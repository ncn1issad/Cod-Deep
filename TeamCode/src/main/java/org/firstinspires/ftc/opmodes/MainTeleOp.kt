package org.firstinspires.ftc.opmodes

import com.pedropathing.follower.FollowerConstants
import com.pedropathing.util.PIDFController
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.OuttakePositions
import org.firstinspires.ftc.utils.AsyncOpMode
import org.firstinspires.ftc.utils.PositionStore
import org.firstinspires.ftc.utils.PressAction

@TeleOp(name = "TeleOp", group = "A")
class MainTeleOp : AsyncOpMode() {
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
    }
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
        if (useIntake) actions.add(robot.intake)
        if (useOuttake) actions.add(robot.outtake)
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
            PressAction(gamepad2::cross) { robot.intake.switch() }
            // TODO: Add more press actions
        )
    }
    /**
     * The loop of the teleop.
     */
    override fun systemLoop() {
        // TODO: Implement the teleop loop
    }
}