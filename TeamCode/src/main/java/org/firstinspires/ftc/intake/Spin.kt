package org.firstinspires.ftc.intake

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.intake.Spin.Companion.left
import org.firstinspires.ftc.intake.Spin.Companion.middle
import org.firstinspires.ftc.intake.Spin.Companion.right
import org.firstinspires.ftc.utils.ManualMechanismTeleOp
import org.firstinspires.ftc.utils.Movement
import org.firstinspires.ftc.utils.PressAction
import org.firstinspires.ftc.utils.ServoPositionMechanism

@Config
class Spin(hardwareMap: HardwareMap) : ServoPositionMechanism(middle) {
    companion object {
        /**
         * The position of the intake spin when it is in the middle position.
         */
        @JvmField
        @Volatile
        var middle= 0.525
        /**
         * The position of the intake spin when it is in the left position.
         */
        @JvmField
        @Volatile
        var left = 0.265
        /**
         * The position of the intake spin when it is in the right position.
         */
        @JvmField
        @Volatile
        var right = 0.785
    }
    /**
     * Gets the servo associated with the Spin mechanism.
     */
    override val servos: Array<Servo> = arrayOf(hardwareMap.servo["Intake Spin"])
}
/**
 * TeleOp class for testing the Spin mechanism manually.
 */
@TeleOp(name = "Intake Spin Test", group = "C")
@Disabled
private class SpinTest : ManualMechanismTeleOp(::Spin)
/**
 * TeleOp class for testing the positions of the Spin mechanism.
 */
@TeleOp(name = "Intake Spin Position Test", group = "D")
@Disabled
private class SpinPositions : Movement() {
    /**
     * The Spin mechanism to be tested.
     */
    override lateinit var system: Spin
    override fun systemInit() {
        system = Spin(hardwareMap)

        actions.add(PressAction(gamepad1::dpad_down) { system.targetPosition = middle })
        actions.add(PressAction(gamepad1::dpad_left) { system.targetPosition = left })
        actions.add(PressAction(gamepad1::dpad_right) { system.targetPosition = right })
    }

    override fun systemLoop() {
        // No need for a loop
    }
}