package org.firstinspires.ftc.teamcode.outtake;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.utils.systems.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utils.systems.ServoPositionMechanism;

import static org.firstinspires.ftc.teamcode.Positions.Outtake.Pendulum.*;

/**
 * Class representing the vertical rotation of the outtake.
 * It extends the ServoPositionMechanism class to control the rotation servo.
 */
public class Pendulum extends ServoPositionMechanism {
    private final HardwareMap hardwareMap;
    /**
     * @param hardwareMap the hardware map to get the servo from.
     */
    public Pendulum(HardwareMap hardwareMap) {
        super(0.0);
        this.hardwareMap = hardwareMap;
    }
    /**
     * Gets the servo associated with the Rotate mechanism.
     * @return an array containing the rotation servo.
     */
    @Override
    protected Servo[] getServos() {
        return new Servo[]{hardwareMap.get(Servo.class, "Outtake Pendulum 1"), hardwareMap.get(Servo.class, "Outtake Pendulum 2")};
    }
}

/**
 * TeleOp class for testing the Pendulum mechanism manually.
 */
@TeleOp(name = "Outtake Pendulum Test", group = "C Outtake")
@Disabled
class PendulumTest extends ManualMechanismTeleOp {
    public PendulumTest() {
        super(Pendulum::new);
    }
}

/**
 * TeleOp class for testing the Pendulum mechanism positions.
 * @noinspection DuplicatedCode
 */
@TeleOp(name = "Outtake Pendulum Positions Test", group = "D Outtake")
@Config
@Disabled
class PendulumPositions extends LinearOpMode {
    public static double current = 0.0;
    @Override
    public void runOpMode() {
        Pendulum pendulum = new Pendulum(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_down) current = transfer;
            else if (gamepad1.dpad_up) current = basket;
            else if (gamepad1.dpad_left) current = bar;
            else if (gamepad1.dpad_right) current = pickup;
            else current += pendulum.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            pendulum.setTargetPosition(current);

            TelemetryPacket packet = new TelemetryPacket();
            if(!pendulum.run(packet)) requestOpModeStop();
            telemetry.addData("Position", current);
            telemetry.update();
        }
    }
}
