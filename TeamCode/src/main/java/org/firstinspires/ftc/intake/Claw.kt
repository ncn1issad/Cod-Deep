package org.firstinspires.ftc.intake

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.utils.PressAction
import org.firstinspires.ftc.utils.systems.CancelableAction
import org.firstinspires.ftc.utils.systems.ManualMechanismTeleOp
import org.firstinspires.ftc.utils.systems.Movement
import org.firstinspires.ftc.utils.systems.ServoPositionMechanism

/**
 * The Claw mechanism of the intake system.
 *
 * @param hardwareMap The hardware map for the robot.
 */
@Config
class Claw(hardwareMap: HardwareMap) : ServoPositionMechanism(close) {
    companion object {
        /**
         * The open position of the claw.
         */
        @JvmField
        @Volatile
        var open = 0.46
        /**
         * The close position of the claw.
         */
        @JvmField
        @Volatile
        var close = 0.79
    }
    /**
     * Gets the servo associated with the Claw mechanism.
     */
    override val servos : Array<Servo> = arrayOf(hardwareMap.servo["Intake Claw"])
    /**
     * The current state of the claw.
     */
    var isClose: Boolean
        get() = targetPosition <= close
        set(value) {
            targetPosition = if (value) close else open
        }
}
/**
 * TeleOp class for testing the Claw mechanism manually.
 */
@TeleOp(name = "Intake Claw Test", group = "C")
@Disabled
private class ClawTest : ManualMechanismTeleOp(::Claw)
/**
 * TeleOp class for testing the positions of the Claw mechanism.
 */
@TeleOp(name = "Intake Claw Position Test", group = "D")
@Disabled
private class ClawPositions : Movement() {
    /**
     * The Claw mechanism to be tested.
     */
    override lateinit var system: Claw

    override fun systemInit() {
        system = Claw(hardwareMap)

        actions.add(PressAction(gamepad1::dpad_up) { system.isClose = false })
        actions.add(PressAction(gamepad1::dpad_down) { system.isClose = true })
    }

    override fun systemLoop() {
        // No need for a loop
    }
}