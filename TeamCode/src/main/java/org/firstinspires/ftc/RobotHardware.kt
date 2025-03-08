package org.firstinspires.ftc

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.utils.CancelableAction

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
    override fun run(p: TelemetryPacket): Boolean = components.all { it.run(p) }
    fun transfer() {
        // TODO: Implement transfer method
    }
}