package org.firstinspires.ftc.utils.systems

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.hardware.Servo

abstract class ServoPositionMechanism(private val initialPosition: Double) : ManualPositionMechanism {
    private var isCancelled = false
    /**
     * The servos that make up this mechanism
     */
    protected abstract val servos: Array<Servo>
    /**
     * The multiplier to adjust the position by
     */
    override var adjustMultiplier = 0.003
    /**
     * The current target position of the mechanism
     */
    override var targetPosition: Double = initialPosition
        set(value) {
             field = value.coerceIn(0.0, 1.0)
        }
    /**
     * The last target position of the mechanism
     */
    private var lastPosition = initialPosition
    /**
     * Cancels the actions of the mechanism
     */
    override fun cancel() {
        isCancelled = true
    }
    /**
     * Runs the mechanism
     */
    override fun run(p: TelemetryPacket): Boolean {
        if (isCancelled) {
            isCancelled = false
            return false
        }
        if (targetPosition != lastPosition) {
            lastPosition = targetPosition
            servos.forEach { it.position = targetPosition }
        }
        p.put("Position of ${this::class.simpleName}", targetPosition)
        return true
    }
}