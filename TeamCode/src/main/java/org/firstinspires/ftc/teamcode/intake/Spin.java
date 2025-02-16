package org.firstinspires.ftc.teamcode.intake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.utils.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utils.ServoPositionMechanism;

import static org.firstinspires.ftc.teamcode.Positions.Intake.Spin.middle;

/**
 * Class representing the horizontal rotation of the intake claw.
 * It extends the ServoPositionMechanism class to control the rotation servo.
 */
public class Spin extends ServoPositionMechanism {
    private final HardwareMap hardwareMap;
    /**
     * @param hardwareMap the hardware map to get the servo from.
     */
    public Spin(HardwareMap hardwareMap) {
        super(middle);
        this.hardwareMap = hardwareMap;
        adjustMultiplier(0.005);
    }
    /**
     * Gets the servo associated with the Spin mechanism.
     * @return an array containing the rotation servo.
     */
    @Override
    protected Servo[] getServos() {
        return new Servo[]{hardwareMap.get(Servo.class, "Spin")};
    }
}

/**
 * TeleOp class for testing the Spin mechanism manually.
 */
@TeleOp(name = "Spin Test", group = "C Intake")
class SpinTest extends ManualMechanismTeleOp {
    public SpinTest() {
        super(Spin::new);
    }
}

/**
 * TeleOp class for testing the Spin mechanism positions.
 * @noinspection DuplicatedCode
 */
@TeleOp(name = "Spin Positions Test", group = "D Intake")
@Config
class SpinPositions extends LinearOpMode {
    public static double current = 0.0;
    @Override
    public void runOpMode() {
        Spin spin = new Spin(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_left) current = middle;
            else current += spin.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            spin.setTargetPosition(current);

            TelemetryPacket packet = new TelemetryPacket();
            if(!spin.run(packet)) requestOpModeStop();
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
            telemetry.addData("Current Position", current);
            telemetry.update();
        }
    }
}