package org.firstinspires.ftc.intake

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.intake.Pendulum.Companion.pickup
import org.firstinspires.ftc.intake.Pendulum.Companion.pickupWait
import org.firstinspires.ftc.intake.Pendulum.Companion.transfer
import org.firstinspires.ftc.utils.PressAction
import org.firstinspires.ftc.utils.systems.ManualMechanismTeleOp
import org.firstinspires.ftc.utils.systems.Movement
import org.firstinspires.ftc.utils.systems.ServoPositionMechanism

@Config
class Pendulum(hardwareMap: HardwareMap) : ServoPositionMechanism(transfer) {
    companion object {
        /**
         * The position of the intake pendulum when it is outside of the submersible.
         */
        @JvmField
        @Volatile
        var pickupWait = 0.1
        /**
         * The position of the intake pendulum when it is inside of the submersible.
         */
        @JvmField
        @Volatile
        var pickup = 0.05
        /**
         * The position of the intake pendulum when it is in the transfer position.
         */
        @JvmField
        @Volatile
        var transfer = 0.75
    }
    /**
     * Gets the servo associated with the Pendulum mechanism.
     */
    override val servos: Array<Servo> = arrayOf(hardwareMap.servo["Intake Pendulum"])
}
/**
 * TeleOp class for testing the Pendulum mechanism manually.
 */
@TeleOp(name = "Intake Pendulum Test", group = "C")
@Disabled
private class PendulumTest : ManualMechanismTeleOp(::Pendulum)
/**
 * TeleOp class for testing the positions of the Pendulum mechanism.
 */
private class PendulumPositions : Movement() {
    /**
     * The Pendulum mechanism to be tested.
     */
    override lateinit var system: Pendulum

    override fun systemInit() {
        system = Pendulum(hardwareMap)

        actions.add(PressAction(gamepad1::dpad_right) { system.targetPosition = pickupWait })
        actions.add(PressAction(gamepad1::dpad_down) { system.targetPosition = pickup })
        actions.add(PressAction(gamepad1::dpad_up) { system.targetPosition = transfer })
    }

    override fun systemLoop() {
        // No need for a loop
    }
}
