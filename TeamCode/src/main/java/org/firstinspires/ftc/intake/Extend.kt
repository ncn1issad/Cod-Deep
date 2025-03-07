package org.firstinspires.ftc.intake

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.intake.Extend.Companion.`in`
import org.firstinspires.ftc.intake.Extend.Companion.out
import org.firstinspires.ftc.utils.PressAction
import org.firstinspires.ftc.utils.ManualMechanismTeleOp
import org.firstinspires.ftc.utils.Movement
import org.firstinspires.ftc.utils.ServoPositionMechanism

/**
 * The Extend mechanism of the intake system.
 *
 * @param hardwareMap The hardware map for the robot.
 */
@Config
class Extend(hardwareMap: HardwareMap) : ServoPositionMechanism(`in`) {
    companion object {
        /**
         * The in position of the extend.
         */
        @JvmField
        @Volatile
        var `in` = 0.635
        /**
         * The out position of the extend.
         */
        @JvmField
        @Volatile
        var out = 0.9
    }
    /**
     * Gets the servo associated with the Extend mechanism.
     */
    override val servos: Array<Servo> = arrayOf(hardwareMap.servo["Intake Extend"])
}
/**
 * TeleOp class for testing the Extend mechanism manually.
 */
@TeleOp(name = "Extend Test", group = "C")
@Disabled
private class ExtendTest : ManualMechanismTeleOp(::Extend)

/**
 * TeleOp class for testing the positions of the Extend mechanism.
 */
@TeleOp(name = "Extend Position Test", group = "D")
@Disabled
private class ExtendPositions : Movement() {
    /**
     * The Extend mechanism to be tested.
     */
    override lateinit var system: Extend

    override fun systemInit() {
        system = Extend(hardwareMap)

        actions.add(PressAction(gamepad1::dpad_up) { (system as Extend).targetPosition = `in` })
        actions.add(PressAction(gamepad1::dpad_down) { (system as Extend).targetPosition = out })
    }

    override fun systemLoop() {
        // No need for a loop
    }
}