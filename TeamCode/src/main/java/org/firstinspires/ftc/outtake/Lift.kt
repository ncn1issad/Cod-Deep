package org.firstinspires.ftc.outtake

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.utils.DelayedActions
import org.firstinspires.ftc.utils.ManualMechanismTeleOp
import org.firstinspires.ftc.utils.ManualPositionMechanism
import org.firstinspires.ftc.utils.Movement
import org.firstinspires.ftc.utils.PressAction

@Config
class Lift(
    hardwareMap: HardwareMap,
    val motors: Array<DcMotorEx> = arrayOf(
        hardwareMap.get(DcMotorEx::class.java, "Lift Left"),
        hardwareMap.get(DcMotorEx::class.java, "Lift Right")
    )
) : ManualPositionMechanism {
    companion object {
        var lastKnown = 0
        /**
         * The up position of the lift.
         */
        @JvmField
        @Volatile
        var up = 3200.0
        /**
         * The position of the lift the level 2 hang is finished.
         */
        @JvmField
        @Volatile
        var hang = 1700.0
        /**
         * The position of the lift when placing specimens in the high chamber.
         */
        @JvmField
        @Volatile
        var half = 650.0
        /**
         * The position of the lift when it transfer.
         */
        @JvmField
        @Volatile
        var transfer = 100.0
        /**
         * The down position of the lift.
         */
        @JvmField
        @Volatile
        var down = 0.0
    }
    /**
     * Flag for whether the lift is cancelled.
     */
    private var isCancelled = false
    /**
     * Initializes the mechanism`s components.
     */
    init {
        motors.forEach {
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.mode = DcMotor.RunMode.RUN_TO_POSITION
            it.power = 1.0
        }
        motors[0].direction = DcMotorSimple.Direction.REVERSE
    }
    /**
     * The current target position of the lift.
     */
    override var targetPosition: Double = 0.0
        set(value) {
            field = value.coerceIn(0.0..3200.0)
        }
    /**
     * The multiplier that the mechanism gets manually adjusted by.
     */
    override var adjustMultiplier: Double = 15.0
    /**
     * The current position of the lift.
     */
    val measuredPosition: Double
        get() = motors.map { it.currentPosition }.average()
    /**
     * The cancel function of the mechanism.
     * Also sets the power of the motors to 0 and stores the last known position of the lift for future use.
     */
    override fun cancel() {
        motors.forEach { it.power = 0.0 }
        lastKnown = measuredPosition.toInt()
        isCancelled = true
    }
    /**
     * The update function of the mechanism.
     */
    override fun run(p: TelemetryPacket): Boolean {
        if (isCancelled) {
            isCancelled = false
            return false
        }
        for ((ind, motor) in motors.withIndex()) {
            motor.targetPosition = targetPosition.toInt()
            p.put("Lift $ind position", motor.currentPosition)
        }
        return true
    }
}
/**
 * Autonomous class for resetting the lift.
 */
@Autonomous(name = "Reset Lift", group = "C")
@Config
private class ResetLift : LinearOpMode() {
    companion object {
        /**
         * The power at which the lift is reset.
         */
        private var resetPower = -0.2
    }
    override fun runOpMode() {
        val lift = Lift(hardwareMap)
        val delays = DelayedActions()
        lift.motors.forEach {
            it.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
            it.power = resetPower
        }
        delays.addDelay(3) {
            lift.motors.forEach {
                it.power = 0.0
                it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                it.mode = DcMotor.RunMode.RUN_TO_POSITION
            }
            lift.cancel()
            requestOpModeStop()
        }
        while (opModeIsActive()) {
            delays.run()
        }
    }
}
/**
 * TeleOp class for testing the Lift mechanism manually.
 */
@TeleOp(name = "Lift Test", group = "C")
@Disabled
private class LiftTest : ManualMechanismTeleOp(::Lift)
/**
 * TeleOp class for testing the positions of the Lift mechanism.
 */
@TeleOp(name = "Lift Position Test", group = "D")
@Disabled
private class LiftPositions : Movement() {
    /**
     * The Lift mechanism to be tested.
     */
    override lateinit var system: Lift

    override fun systemInit() {
        system = Lift(hardwareMap)

        actions.add(PressAction(gamepad1::dpad_up) { system.targetPosition = Lift.up })
        actions.add(PressAction(gamepad1::dpad_left) { system.targetPosition = Lift.hang })
        actions.add(PressAction(gamepad1::dpad_right) { system.targetPosition = Lift.half })
        actions.add(PressAction(gamepad1::dpad_down) { system.targetPosition = Lift.down })
        actions.add(PressAction(gamepad1::cross) { system.targetPosition = Lift.transfer })
    }

    override fun systemLoop() {
        // No need for a loop
    }
}
