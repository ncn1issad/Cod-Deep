package org.firstinspires.ftc.utils.systems

import com.pedropathing.follower.Follower
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

abstract class Movement : LinearOpMode() {
    /**
     * Initializes the system or subsystem along with
     * any hardware devices not related to the movement of the robot.
     */
    abstract fun systemInit()
    /**
     * Actions to be done after the start button is pressed but only once.
     * This function is empty by default.
     */
    open fun systemStart() {}
    /**
     * Actions to be done after the stop button is pressed.
     */
    abstract fun systemLoop()
    /**
     * The main function that runs the system or subsystem.
     */
    override fun runOpMode() {
        /**
         * Pedro Pathing`s follower
         */
        val follower = Follower(hardwareMap)
        // Initialize the system or subsystem
        systemInit()
        // Wait for the start button to be pressed
        waitForStart()
        // Initialize the follower for teleop drive
        follower.startTeleopDrive()
        // Does all of the actions specified in the start button
        systemStart()
        // Run the op mode
        while (opModeIsActive()) {
            // Sets the movement vectors for the follower
            follower.setTeleOpMovementVectors(
                gamepad1.left_stick_x.toDouble(),
                gamepad1.left_stick_y.toDouble(),
                gamepad1.right_stick_x.toDouble(),
                false)
            // Updates the follower
            follower.update()
            // Run the system or subsystem
            systemLoop()
        }
    }
}