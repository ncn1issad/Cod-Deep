package org.firstinspires.ftc.utils.systems

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.follower.Follower
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.utils.PressAction
import pedroPathing.constants.FConstants
import pedroPathing.constants.LConstants

abstract class ManualMechanismTeleOp(private val factory: ManualPositionFactory) : OpMode() {
    /**
     * Initializes the mechanism ot be tested.
     */
    private lateinit var mechanism: ManualPositionMechanism
    /**
     * Initializes the follower for the robots movement.
     */
    lateinit var follower: Follower
    /**
     * Initializes the dashboard for telemetry data.
     */
    val dashboard: FtcDashboard = FtcDashboard.getInstance()
    /**
     * Creates the stop button
     */
    private lateinit var stopRequest: PressAction
    /**
     * Initializes the robot hardware.
     */
    override fun init() {
        // Sets the constants for the follower
        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        // Initializes the mechanism and follower
        mechanism = factory.manualPositionFactory(hardwareMap)
        follower = Follower(hardwareMap)
        follower.setStartingPose(Pose(0.0, 0.0, 0.0))
        // Assigns a stop button
        stopRequest = PressAction(gamepad1::options) { mechanism.cancel() }
    }
    /**
     * Starts the teleop drive for the robot.
     */
    override fun start() {
        follower.startTeleopDrive()
    }
    /**
     * Updates the follower and the mechanism.
     */
    override fun loop() {
        // Sets the movement vectors for the follower
        follower.setTeleOpMovementVectors(
            gamepad1.left_stick_x.toDouble(),
            gamepad1.left_stick_y.toDouble(),
            gamepad1.right_stick_x.toDouble(),
            false
        )
        follower.update()
        // Updates the mechanism`s position
        mechanism.targetPosition += (gamepad1.right_trigger - gamepad1.left_trigger) * mechanism.adjustMultiplier
        // Runs the mechanism
        val packet = TelemetryPacket()
        if(!mechanism.run(packet)) {
            // Stops the op mode if cancel is requested
            this.requestOpModeStop()
        }
        // Checks if the stop button is pressed
        stopRequest.run()
        // Sends telemetry data to the dashboard and driver station
        dashboard.sendTelemetryPacket(packet)
        dashboard.telemetry.update()
        telemetry.addData("Position of ${mechanism::class.simpleName}", mechanism.targetPosition)
        telemetry.update()
    }
}