package org.firstinspires.ftc.opmodes

import com.pedropathing.follower.FollowerConstants
import com.pedropathing.localization.Pose
import com.pedropathing.util.PIDFController
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.IntakePositions
import org.firstinspires.ftc.OuttakePositions
import org.firstinspires.ftc.intake.Spin
import org.firstinspires.ftc.outtake.Lift
import org.firstinspires.ftc.utils.AsyncOpMode
import org.firstinspires.ftc.utils.PositionStore
import org.firstinspires.ftc.utils.PressAction

@TeleOp(name = "TeleOp", group = "A")
open class MainTeleOp : AsyncOpMode() {
    companion object {
        /**
         * Flag indicating if the intake mechanism should be used.
         */
        @JvmField
        @Volatile
        var useIntake = true
        /**
         * Flag indicating if the outtake mechanism should be used.
         */
        @JvmField
        @Volatile
        var useOuttake = true
        /**
         * Flag indicating if the robot should take yellow samples.
         */
        @JvmField
        @Volatile
        var isYellowAllowed = false
    }
    /**
     * The color of the alliance.
     */
    protected open val allianceColor = AllianceColor.RED
    /**
     * Checks if the color of the alliance is the same as the detected color.
     */
    private val isAllianceColor get() =
        when (allianceColor) {
            AllianceColor.RED -> robot.intake.sensor.isRed
            AllianceColor.BLUE -> robot.intake.sensor.isBlue
        }
    /**
     * Checks if the color of the alliance is the opposite of the detected color.
     */
    private val isOppositeColor get() =
        when (allianceColor) {
            AllianceColor.RED -> robot.intake.sensor.isBlue
            AllianceColor.BLUE -> robot.intake.sensor.isRed
        }
    /**
     * Checks if the sample is of the right color.
     */
    private val isRightColor get() = isAllianceColor || (isYellowAllowed && robot.intake.sensor.isYellow)
    /**
     * Checks if the sample is of the wrong color.
     */
    private val isWrongColor get() = isOppositeColor || (!isYellowAllowed && robot.intake.sensor.isYellow)
    /**
     * A flag indicating if the robot should hold its heading.
     */
    private var holdHeading = false
    /**
     * The heading PID the robot uses to hold its heading.
     */
    private val headingPID = PIDFController(FollowerConstants.headingPIDFCoefficients)
    /**
     * The actions to run when a button is pressed.
     */
    private lateinit var pressActions: Array<PressAction>
    /**
     * The initialization of the teleop.
     */
    override fun systemInit() {
        follower.setStartingPose(PositionStore.pose)
        headingPID.targetPosition = 0.0
        // Adds the robot components to the actions list
        if (useIntake) actions.add(robot.intake)
        if (useOuttake) actions.add(robot.outtake)
        /**
         * The press actions for the teleop.
         */
        pressActions = arrayOf(
            PressAction(gamepad2::right_bumper) {
                // Finishing the transfer
                if (robot.inTransferPosition) { robot.finishTransfer = true }
                // Realising the specimen
                else if (robot.outtake.claw.isClose && robot.outtake.targetPosition == OuttakePositions.BAR) {
                    robot.outtake.claw.isClose = false
                    robot.outtake.pendulum.targetPosition = robot.outtake.targetPosition.pendulum - 0.1
                } else { robot.outtake.claw.switch() }
                // Cancelling the intake hold heading once transfer is finished
                if (robot.outtake.claw.isClose) { holdHeading = false }
            },
            // Toggle between the intake positions
            PressAction(gamepad2::cross) { robot.intake.switch() },
            // Toggling the holdHeading variable
            PressAction(gamepad1::cross) { holdHeading = !holdHeading },
            // Going to the hang position
            PressAction(gamepad2::right_stick_button) {
                robot.outtake.targetPosition = OuttakePositions.TRANSFER
                robot.outtake.lift.targetPosition = Lift.up
            }, // Hanging
            PressAction(gamepad2::left_stick_button) { robot.outtake.lift.targetPosition = Lift.hang },
            // Outtake controls for basket
            PressAction(gamepad2::circle) {
                if (robot.outtake.claw.isClose) {
                    robot.outtake.targetPosition = OuttakePositions.BAR
                } else if (robot.intake.claw.isClose) {
                    if (robot.inTransferPosition) {
                        robot.finishTransfer = true
                        addCheck({ !robot.finishTransfer }, {
                            robot.outtake.targetPosition = OuttakePositions.BAR
                        })
                    } else {
                        robot.inTransfer = true
                        addCheck({ !robot.inTransfer }, {
                            robot.finishTransfer = true
                            addCheck({ !robot.finishTransfer }, {
                                robot.outtake.targetPosition = OuttakePositions.BAR
                            })
                        })
                    }
                } else {
                    robot.outtake.targetPosition = OuttakePositions.BASKET
                }
            },
            // Prepares the robot for transfer
            // DEPRECATED: No longer needed
            PressAction({ gamepad2.left_stick_y > 0.8 }) {
                robot.outtake.targetPosition = OuttakePositions.TRANSFER
                robot.outtake.lift.targetPosition = Lift.half
                robot.outtake.claw.isClose = false
            },
            // Transfer logic
            PressAction(gamepad2::dpad_right) { robot.inTransfer = true },
            // Resets the robot`s pose to the initial one
            PressAction(gamepad1::left_stick_button) { follower.pose = Pose(0.0, 0.0, 0.0) },
            // Resets the robot`s pose to 180 degrees opposite of the initial one
            PressAction(gamepad1::right_stick_button) { follower.pose = Pose(0.0, 0.0, Math.PI) },
            PressAction(gamepad1::right_bumper) {
                if (robot.intake.targetPosition == IntakePositions.PICKUP) {
                    robot.intake.pickup()
                    delay(0.12) {
                        var colorDetections = 0
                        colorDetections += if (robot.intake.sensor.isRed) 1 else 0
                        colorDetections += if (robot.intake.sensor.isBlue) 1 else 0
                        colorDetections += if (robot.intake.sensor.isYellow) 1 else 0

                        if (colorDetections > 1) {
                            RobotLog.e(
                                """
                                    Multiple Colors Detected:
                                        Red: ${robot.intake.sensor.isRed},
                                        Blue: ${robot.intake.sensor.isBlue},
                                        Yellow: ${robot.intake.sensor.isYellow},
                                """.trimIndent()
                            )
                        }

                    }
                }
            },
            // Resets the intake`s spin mechanism to the middle position
            PressAction(gamepad1::left_bumper) {
                if (robot.intake.targetPosition == IntakePositions.PICKUP) {
                    robot.intake.claw.isClose = false
                    robot.intake.spin.targetPosition = Spin.middle
                }
            },
            // Sets the intake`s spin mechanism to the right position
            PressAction( { gamepad1.right_trigger > 0.8 } ) {
                if (robot.intake.targetPosition == IntakePositions.PICKUP) {
                    robot.intake.claw.isClose = false
                    robot.intake.spin.targetPosition = Spin.right
                }
            },
            // Sets the intake`s spin mechanism to the left position
            PressAction( { gamepad1.left_trigger > 0.8 } ) {
                if (robot.intake.targetPosition == IntakePositions.PICKUP) {
                    robot.intake.claw.isClose = false
                    robot.intake.spin.targetPosition = Spin.left
                }
            },
            // Toggles the robot`s ability to take yellow samples
            PressAction(gamepad1::square) {
                isYellowAllowed = !isYellowAllowed
                gamepad2.rumbleBlips(if (isYellowAllowed) 2 else 1)
            },
            // Sets the outtake`s lift
            PressAction(gamepad2::square) { robot.outtake.lift.targetPosition = Lift.down },
            PressAction(gamepad2::triangle) { robot.outtake.lift.targetPosition = Lift.half },
            // Sets the robot to the pickup position
            PressAction(gamepad2::dpad_down) {
                robot.outtake.targetPosition = OuttakePositions.PICKUP
                robot.intake.targetPosition = IntakePositions.SPECIMEN_PICKUP
            },
            // Sets the outtake to the basket position
            PressAction(gamepad2::dpad_up) { robot.outtake.targetPosition = OuttakePositions.BASKET },
            // Sets the outtake to the bar position
            PressAction(gamepad2::dpad_left) { robot.outtake.targetPosition = OuttakePositions.BAR }
        )
    }
    /**
     * The start of the teleop.
     */
    override fun systemStart() {
        follower.startTeleopDrive()
        robot.intake.targetPosition = IntakePositions.TRANSFER
        robot.intake.claw.isClose = false
    }
    /**
     * The loop of the teleop.
     */
    override fun systemLoop() {
        // A reducing gear for when the robot is searching within the submersible
        val powerMultiply = if (robot.intake.targetPosition == IntakePositions.PICKUP) 0.4 else 1.0
        // The movement of the robot
        follower.setTeleOpMovementVectors(
            gamepad1.left_stick_y.toDouble(),
            gamepad1.left_stick_x.toDouble() * powerMultiply,
            if (holdHeading) {
                follower.headingOffset += gamepad1.right_stick_x.toDouble() * 0.025
                headingPID.updatePosition(follower.pose.heading)
                headingPID.runPIDF()
            } else { gamepad1.right_stick_x.toDouble() * powerMultiply },
            false
        )
        // Runs the press actions
        pressActions.forEach { it.run() }
    }
}