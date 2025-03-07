package org.firstinspires.ftc

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.pedropathing.util.Timer
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.intake.*
import org.firstinspires.ftc.utils.CancelableAction
import org.firstinspires.ftc.utils.ManualPositionMechanism
import org.firstinspires.ftc.utils.Movement
import org.firstinspires.ftc.utils.PressAction

/**
 * Constructor for the Intake class.
 * Initializes all intake components using the provided hardware map.
 * @param hardwareMap the hardware map to get the components from.
 */
class Intake (
    hardwareMap: HardwareMap,
    val claw: Claw = Claw(hardwareMap),
    val extend: Extend = Extend(hardwareMap),
    val pendulum: Pendulum = Pendulum(hardwareMap),
    val rotate: Rotate = Rotate(hardwareMap),
    val spin: Spin = Spin(hardwareMap)
) : CancelableAction {
    /**
     * The list of intake components.
     */
    private val components: MutableList<ManualPositionMechanism> = mutableListOf(claw, extend, pendulum, rotate, spin)
    /**
     * Cancels all intake actions.
     */
    override fun cancel() {
        components.forEach { it.cancel() }
    }
    /**
     * If the intake needs to pick up a sample.
     */
    private var needsPickup = false
    /**
     * The timer for picking up a sample.
     */
    private val pickupTimer = Timer()
    /**
     * Runs the intake mechanism and updates the telemetry packet.
     * @param p the telemetry packet to update.
     * @return true if all components are running, false otherwise.
     */
    override fun run(p: TelemetryPacket): Boolean {
        if (needsPickup) {
            when(pickupTimer.elapsedTimeSeconds) {
                in 0.0..0.075 -> {
                    claw.isClose = false
                    pendulum.targetPosition = Pendulum.pickup
                    rotate.targetPosition = Rotate.pickup
                }
                in 0.07..0.12 -> {
                    claw.isClose = true
                }
                else -> {
                    needsPickup = false
                    pendulum.targetPosition = Pendulum.pickupWait
                    rotate.targetPosition = Rotate.pickupWait
                }
            }
        }
        return claw.run(p) && extend.run(p) && pendulum.run(p) && rotate.run(p) && spin.run(p)
    }
    /**
     * The target position for the intake mechanism.
     */
    var targetPosition: IntakePositions = IntakePositions.TRANSFER
        set(value) {
            extend.targetPosition = value.extend
            pendulum.targetPosition = value.pendulum
            rotate.targetPosition = value.rotate
            spin.targetPosition = value.spin
            field = value
        }
    /**
     * Initiates the pickup action for the intake mechanism.
     */
    fun pickup() {
        if (targetPosition != IntakePositions.PICKUP) return
        needsPickup = true
        pickupTimer.resetTimer()
    }
    /**
     * Switches the state of the intake mechanism.
     */
    fun switch() {
        targetPosition = if (targetPosition == IntakePositions.TRANSFER) IntakePositions.PICKUP else IntakePositions.TRANSFER
    }
}
/**
 * Enum representing the different positions of the intake mechanism.
 * Each position includes values for extend, pendulum, rotate and spin components.
 */
enum class IntakePositions (
    val extend: Double,
    val pendulum: Double,
    val rotate: Double,
    val spin: Double
) {
    /**
     * Position for picking up samples.
     * Includes values for extend, rotate, spin, and pendulum components.
     */
    PICKUP(
        Extend.out,
        Pendulum.pickupWait,
        Rotate.pickupWait,
        Spin.middle
    ),
    /**
     * Position for transferring samples to outtake.
     * Includes values for extend, rotate, spin, and pendulum components.
     */
    TRANSFER(
        Extend.`in`,
        Pendulum.transfer,
        Rotate.transfer,
        Spin.middle
    )
}
/**
 * TeleOp mode for manually controlling the Intake mechanism.
 * This class extends [Movement] and provides a manual teleop mode
 * to control the Intake mechanism using gamepad inputs.
 */
@TeleOp(name = "Intake Test", group = "B")
@Disabled
private class IntakeTest : Movement() {
    /**
     * The Intake mechanism to be tested.
     */
    override lateinit var system: Intake
    /**
     * Initializes the Intake mechanism.
     */
    override fun systemInit() {
        system = Intake(hardwareMap)

        actions.add(PressAction(gamepad1::right_bumper) { system.claw.switch() })
        actions.add(PressAction(gamepad1::left_bumper) { system.switch() })
    }
    /**
     * Updates the Intake mechanism based on gamepad inputs.
     */
    override fun systemLoop() {
        system.pendulum.targetPosition += gamepad1.right_stick_y.toDouble() * system.pendulum.adjustMultiplier
        system.rotate.targetPosition += gamepad1.left_stick_y.toDouble() * system.rotate.adjustMultiplier
        system.spin.targetPosition += (gamepad1.right_trigger - gamepad1.left_trigger).toDouble() * system.spin.adjustMultiplier
    }
}