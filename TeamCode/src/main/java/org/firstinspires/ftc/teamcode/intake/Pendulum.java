package org.firstinspires.ftc.teamcode.intake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.utils.systems.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utils.systems.ServoPositionMechanism;

import static org.firstinspires.ftc.teamcode.Positions.Intake.Pendulum.pickup;
import static org.firstinspires.ftc.teamcode.Positions.Intake.Pendulum.pickupWait;
import static org.firstinspires.ftc.teamcode.Positions.Intake.Pendulum.transfer;

/**
 * Class representing the vertical rotation of the intake.
 * It extends the ServoPositionMechanism class to control the rotation servo.
 */
public class Pendulum extends ServoPositionMechanism {
    private final HardwareMap hardwareMap;
    /**
     * @param hardwareMap the hardware map to get the servo from.
     */
    public Pendulum(HardwareMap hardwareMap) {
        super(transfer);
        this.hardwareMap = hardwareMap;
    }
    /**
     * Gets the servo associated with the Rotate mechanism.
     * @return an array containing the rotation servo.
     */
    @Override
    protected Servo[] getServos() {
        return new Servo[]{hardwareMap.get(Servo.class, "Intake Pendulum")};
    }
}

/**
 * TeleOp class for testing the Pendulum mechanism manually.
 */
@TeleOp(name = "Intake Pendulum Test", group = "C Intake")
class PendulumTest extends ManualMechanismTeleOp {
    public PendulumTest() {
        super(Pendulum::new);
    }
}

/**
 * TeleOp class for testing the Pendulum mechanism positions.
 * @noinspection DuplicatedCode
 */
@TeleOp(name = "Intake Pendulum Positions Test", group = "D Intake")
@Config
class PendulumPositions extends LinearOpMode {
    public static double current = 0.0;
    @Override
    public void runOpMode() {
        Pendulum pendulum = new Pendulum(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_right) current = pickupWait;
            else if (gamepad1.dpad_down) current = pickup;
            else if (gamepad1.dpad_up) current = transfer;
            else current += pendulum.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            pendulum.setTargetPosition(current);

            TelemetryPacket packet = new TelemetryPacket();
            if(!pendulum.run(packet)) requestOpModeStop();
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
            telemetry.addData("Current Position", current);
            telemetry.update();
        }
    }
}
