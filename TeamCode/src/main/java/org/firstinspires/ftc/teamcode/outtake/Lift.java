package org.firstinspires.ftc.teamcode.outtake;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.utils.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utils.ManualPositionMechanism;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.Positions.Outtake.Lift.*;

/**
 * Class representing the vertical movement of the outtake.
 * It implements the ManualPositionMechanism interface to control the lift motors.
 */
public class Lift implements ManualPositionMechanism {
    private boolean isCancelled = false;
    private final DcMotorEx[] motors;
    /**
     * Constructor for the Lift class.
     * Initializes the lift motors using the provided hardware map.
     * @param hardwareMap the hardware map to use for initialization.
     */
    public Lift(@NotNull HardwareMap hardwareMap) {
        motors = new DcMotorEx[]{
            hardwareMap.get(DcMotorEx.class, "LiftLeft"),
            hardwareMap.get(DcMotorEx.class, "LiftRight")
        };
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(0);
            motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
        motors[1].setDirection(DcMotorEx.Direction.REVERSE);
    }
    private double power = 1.0;
    private int targetPosition = 0;
    /**
     * Sets the target position for the lift motors.
     * @param position the target position to set.
     */
    @Override
    public void setTargetPosition(double position) {
        targetPosition = (int) Range.clip(position, 0, 2185);
    }
    /**
     * Gets the target position for the lift motors.
     * @return the target position.
     */
    @Override
    public double getTargetPosition() {
        return targetPosition;
    }
    private double multiplier = 15.0;
    /**
     * Adjusts the multiplier for the lift motors.
     * @param multiplier the multiplier to adjust by.
     */
    @Override
    public void adjustMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
    /**
     * Gets the multiplier for the lift motors.
     * @return the multiplier.
     */
    @Override
    public double getMultiplier() {
        return multiplier;
    }
    /**
     * Cancels the lift motors.
     */
    @Override
    public void cancel() {
        power = 0.0;
        isCancelled = true;
    }
    /**
     * Runs the lift motors and updates the telemetry packet.
     * @param packet the telemetry packet to update.
     * @return true if the motors are running, false otherwise.
     */
    @Override
    public boolean run(@NotNull TelemetryPacket packet) {
        if (isCancelled) {
            for (DcMotorEx motor : motors) motor.setPower(power);
            return false;
        }
        for(int ind = 0; ind < motors.length; ind++) {
            motors[ind].setPower(power);
            motors[ind].setTargetPosition(targetPosition);

            packet.put("Lift " + ind + " position", motors[ind].getCurrentPosition());
        }
        return true;
    }
}

/**
 * TeleOp class for testing the Lift mechanism manually.
 */
@TeleOp(name = "Lift Test", group = "C Outtake")
class LiftTest extends ManualMechanismTeleOp {
    public LiftTest() {
        super(Lift::new);
    }
}

/**
 * TeleOp class for testing the Lift mechanism positions.
 * @noinspection DuplicatedCode
 */
@TeleOp(name = "Lift Positions Test", group = "D Outtake")
@Config
class LiftPositions extends LinearOpMode {
    public static double current = 0.0;
    @Override
    public void runOpMode() {
        Lift lift = new Lift(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_down) current = down;
            else if (gamepad1.dpad_right) current = half;
            else if (gamepad1.dpad_up) current = up;
            else current += lift.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            lift.setTargetPosition(current);

            TelemetryPacket packet = new TelemetryPacket();
            if(!lift.run(packet)) requestOpModeStop();
            telemetry.addData("Position", current);
            telemetry.update();
        }
    }
}
