package org.firstinspires.ftc

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.outtake.Claw
import org.firstinspires.ftc.outtake.Pendulum
import org.firstinspires.ftc.outtake.Lift
import org.firstinspires.ftc.outtake.Rotate
import org.firstinspires.ftc.utils.CancelableAction
import org.firstinspires.ftc.utils.ManualPositionMechanism
import org.firstinspires.ftc.utils.Movement
import org.firstinspires.ftc.utils.PressAction

class Outtake(
    hardwareMap: HardwareMap,
    val claw: Claw = Claw(hardwareMap),
    val lift: Lift = Lift(hardwareMap),
    val pendulum: Pendulum = Pendulum(hardwareMap),
    val rotate: Rotate = Rotate(hardwareMap)
) : CancelableAction {
    /**
     * The list of outtake components.
     */
    private val components: MutableList<ManualPositionMechanism> = mutableListOf(claw, lift, pendulum, rotate)
    /**
     * Cancels all outtake actions.
     */
    override fun cancel() {
        components.forEach { it.cancel() }
    }
    /**
     * Runs the outtake mechanism and updates the telemetry packet.
     * @param p the telemetry packet to update.
     * @return true if all components are running, false otherwise.
     */
    override fun run(p: TelemetryPacket): Boolean = components.all { it.run(p) }
    /**
     * The target position for the outtake mechanism.
     */
    var targetPosition: OuttakePositions = OuttakePositions.TRANSFER
        set(value) {
            claw.targetPosition = value.rotate
            lift.targetPosition = value.lift
            pendulum.targetPosition = value.pendulum
            rotate.targetPosition = value.rotate
            field = value
        }
}
/**
 * Enum representing the different positions of the outtake mechanism.
 * Each position includes values for lift, pendulum and rotate components.
 */
enum class OuttakePositions (
    val lift: Double,
    val pendulum: Double,
    val rotate: Double
) {
    /**
     * Position for picking up specimens.
     * Includes values for the lift, pendulum and rotate components.
     */
    PICKUP(
        Lift.down,
        Pendulum.pickup,
        Rotate.pickup
    ),
    /**
     * Position for transferring samples to the outtake.
     * Includes values for the lift, pendulum and rotate components.
     */
    TRANSFER(
        Lift.transfer,
        Pendulum.transfer,
        Rotate.transfer
    ),
    /**
     * Position for rotating the outtake to the bar.
     * Includes values for the lift, pendulum and rotate components.
     */
    BAR(
        Lift.half,
        Pendulum.bar,
        Rotate.bar
    ),
    /**
     * Position for rotating the outtake to the basket.
     * Includes values for the lift, pendulum and rotate components.
     */
    BASKET(
        Lift.up,
        Pendulum.basket,
        Rotate.basket
    ),
    /**
     * Position for hanging the robot.
     * Includes values for the lift, pendulum and rotate components.
     */
    HANG(
        Lift.hang,
        Pendulum.transfer,
        Rotate.transfer
    )
}
/**
 * TeleOp mode for manually controlling the Outtake mechanism.
 * This class extends [Movement] and provides a manual teleop mode
 * to control the Outtake mechanism using gamepad inputs.
 */
@TeleOp(name = "Outtake Test", group = "B")
@Disabled
private class OuttakeTest : Movement() {
    /**
     * The Outtake mechanism to be tested.
     */
    override lateinit var system: Outtake
    /**
     * Initializes the Outtake mechanism.
     */
    override fun systemInit() {
        system = Outtake(hardwareMap)

        actions.add(PressAction(gamepad1::right_bumper) { system.claw.switch() })
    }
    /**
     * Updates the Outtake mechanism based on gamepad inputs.
     */
    override fun systemLoop() {
        system.pendulum.targetPosition += gamepad1.right_stick_y.toDouble() * system.pendulum.adjustMultiplier
        system.rotate.targetPosition += gamepad1.left_stick_y.toDouble() * system.rotate.adjustMultiplier
        system.lift.targetPosition += (gamepad1.right_trigger - gamepad1.left_trigger).toDouble() * system.lift.adjustMultiplier
    }
}
