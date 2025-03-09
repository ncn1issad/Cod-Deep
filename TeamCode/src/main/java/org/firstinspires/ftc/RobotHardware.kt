package org.firstinspires.ftc

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.util.Timer
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.utils.CancelableAction
import org.firstinspires.ftc.utils.DelayedActions

class RobotHardware(
    hardwareMap: HardwareMap,
    val intake: Intake = Intake(hardwareMap),
    val outtake: Outtake = Outtake(hardwareMap)
) : CancelableAction {
    /**
     * The list of robot components
     */
    private val components: MutableList<CancelableAction> = mutableListOf(intake, outtake)
    /**
     * The delayed actions for the transfer logic.
     */
    private val delays = DelayedActions()
    /**
     * Cancels all robot actions.
     */
    override fun cancel() {
        components.forEach { it.cancel() }
    }
    /**
     * Runs the robot mechanism and updates the telemetry packet.
     * @param p the telemetry packet to update.
     * @return true if all components are running, false otherwise.
     */
    override fun run(p: TelemetryPacket): Boolean {
        if (finishTransfer && inTransferPosition) {
            if (toOuttake) {
                when (transferTimer.elapsedTimeSeconds) {
                    in 0.0..0.075 -> { outtake.claw.isClose = true }
                    in 0.50..1.25 -> { intake.claw.isClose = false }
                    else -> { finishTransfer = false }
                }
            } else {
                when (transferTimer.elapsedTimeSeconds) {
                    in 0.0..0.075 -> { intake.claw.isClose = true }
                    in 0.50..1.25 -> { outtake.claw.isClose = false }
                    else -> { finishTransfer = false }
                }
            }
        }
        delays.run()
        return components.all { it.run(p) }
    }
    /**
     * Flag indicating if the robot is in transfer mode.
     * Make sure to NEVER set this value to false directly.
     */
    var inTransfer = false
        set(value) {
            if (value) {
                field = true
                when (outtake.targetPosition) {
                    OuttakePositions.TRANSFER -> {
                        if (intake.targetPosition == IntakePositions.TRANSFER) { field = false }
                        else {
                            intake.switch()
                            delays.addDelay(0.2) { field = false }
                        }
                    }
                    OuttakePositions.BAR -> {
                        outtake.targetPosition = OuttakePositions.TRANSFER
                        intake.targetPosition = IntakePositions.TRANSFER
                        delays.addDelay(0.2) { field = false }
                    }
                    OuttakePositions.BASKET -> {
                        outtake.targetPosition = OuttakePositions.TRANSFER
                        delays.addDelay(0.2) { intake.targetPosition = IntakePositions.TRANSFER }
                        delays.addDelay(0.4) { field = false }
                    }
                    OuttakePositions.PICKUP -> {
                        outtake.targetPosition = OuttakePositions.TRANSFER
                        delays.addDelay(0.1) { intake.targetPosition = IntakePositions.TRANSFER }
                        delays.addDelay(0.3) { field = false }
                    }
                }
            }
        }
    val inTransferPosition: Boolean
        get() = intake.targetPosition == IntakePositions.TRANSFER && outtake.targetPosition == OuttakePositions.TRANSFER && !inTransfer
    /**
     * Flag indicating if the sample is in the robots intake.
     */
    private val toOuttake: Boolean
        get() = intake.claw.isClose
    /**
     * Flag indicating if the robot is to transfer the sample.
     */
    var finishTransfer = false
        set(value) {
            if (value && !field) {
                toOuttake
                transferTimer.resetTimer()
                field = true
            }
        }
    /**
     * Timer for the transfer mode.
     */
    private val transferTimer = Timer()
}