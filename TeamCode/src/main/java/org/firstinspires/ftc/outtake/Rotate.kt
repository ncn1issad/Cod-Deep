package org.firstinspires.ftc.outtake

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.outtake.Rotate.Companion.pickup
import org.firstinspires.ftc.outtake.Rotate.Companion.transfer
import org.firstinspires.ftc.outtake.Rotate.Companion.bar
import org.firstinspires.ftc.outtake.Rotate.Companion.basket
import org.firstinspires.ftc.utils.PressAction
import org.firstinspires.ftc.utils.ManualMechanismTeleOp
import org.firstinspires.ftc.utils.Movement
import org.firstinspires.ftc.utils.ServoPositionMechanism

@Config
class Rotate(hardwareMap: HardwareMap) : ServoPositionMechanism(transfer) {
    companion object {
        /**
         * The position of the outtake rotation when it is inside of the submersible.
         */
        @JvmField
        @Volatile
        var pickup = 0.834
        /**
         * The position of the outtake rotation when it is in the transfer position.
         */
        @JvmField
        @Volatile
        var transfer = 0.19
        /**
         * The position of the outtake rotation when it is in the high chamber position.
         */
        @JvmField
        @Volatile
        var bar = 0.71
        /**
         * The position of the outtake rotation when it is in the basket position.
         */
        @JvmField
        @Volatile
        var basket = 0.58
    }
    /**
     * Gets the servo associated with the Rotate mechanism.
     */
    override val servos: Array<Servo> = arrayOf(hardwareMap.servo["Outtake Rotate"])
}
/**
 * TeleOp class for testing the Rotate mechanism manually.
 */
@TeleOp(name = "Outtake Rotate Test", group = "C")
@Disabled
private class RotateTest : ManualMechanismTeleOp(::Rotate)
/**
 * TeleOp class for testing the positions of the Rotate mechanism.
 */
@TeleOp(name = "Outtake Rotate Position Test", group = "D")
@Disabled
private class RotatePositions : Movement() {
    /**
     * The Rotate mechanism to be tested.
     */
    override lateinit var system: Rotate

    override fun systemInit() {
        system = Rotate(hardwareMap)

        actions.add(PressAction(gamepad1::dpad_down) { system.targetPosition = pickup })
        actions.add(PressAction(gamepad1::dpad_up) { system.targetPosition = transfer })
        actions.add(PressAction(gamepad1::dpad_left) { system.targetPosition = basket })
        actions.add(PressAction(gamepad1::dpad_right) { system.targetPosition = bar })
    }

    override fun systemLoop() {
        // No need for a loop
    }
}