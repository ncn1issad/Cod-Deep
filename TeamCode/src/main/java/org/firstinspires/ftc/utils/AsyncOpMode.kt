package org.firstinspires.ftc.utils

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.follower.Follower
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.RobotHardware
import org.firstinspires.ftc.utils.systems.CancelableAction

abstract class AsyncOpMode : LinearOpMode() {
    /**
     * The hardware for the robot.
     */
    protected lateinit var robot: RobotHardware
    /**
     * Pedro Pathing`s follower
     */
    protected lateinit var follower: Follower
    /**
     * Creates a list with the system or subsystem
     */
    private val actions: MutableList<CancelableAction> = mutableListOf()
    /**
     * Creates the container for the delayed
     */
    private val delayed = DelayedActions()
    /**
     * Creates the container for the checks
     */
    private val checks = CheckActions()
    /**
     * A wrapper function for addDelay in [DelayedActions]
     * @see DelayedActions.addDelay
     * @param time the delay in seconds.
     * @param action the action to run.
     */
    protected fun delay(time: Long, action: () -> Unit) {
        delayed.addDelay(time, action)
    }
    /**
     * A wrapper function for addCheck in [CheckActions]
     * @see CheckActions.addCheck
     * @param check the lambda check that determines if the action should be run.
     * @param action the action to run.
     */
    protected fun check(check: () -> Boolean, action: () -> Unit) {
        checks.addCheck(check, action)
    }
    /**
     * Init of the opMode
     */
    abstract fun systemInit()
    /**
     * Loop to be run before the start of the opMode
     */
    open fun systemInitLoop() {}
    /**
     * Start of the opMode
     */
    open fun systemStart() {}
    /**
     * Loop to be run after the start of the opMode
     */
    abstract fun systemLoop()

    final override fun runOpMode() {
        // Initializes the robot hardware
        robot = RobotHardware(hardwareMap)
        // Initializes the dashboard
        val dashboard = FtcDashboard.getInstance()
        // Initializes the follower
        follower = Follower(hardwareMap)
        systemInit()
        while (!isStarted) {
            // Runs the systems while in init
            val packet = TelemetryPacket()
            actions.removeAll { !it.run(packet) }
            dashboard.telemetry.update()
            systemInitLoop()
        }
        dashboard.clearTelemetry()
        systemStart()
        while (opModeIsActive()) {
            // Runs the systems while in play
            val packet = TelemetryPacket()
            actions.removeAll { !it.run(packet) }
            delayed.run()
            checks.run()
            // Updates the follower and dashboard
            packet.put("Robot Pose", follower.pose.asPedroCoordinates)
            dashboard.telemetry.update()
            follower.drawOnDashBoard()
            follower.update()
            systemLoop()
        }
    }

    /**
     * Adds timed transfer logic to the opMode.
     * Only use in auto.
     */
    protected fun transfer() {
        //TODO: Do after rest of the systems are done
    }
}