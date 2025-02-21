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

import static org.firstinspires.ftc.teamcode.Positions.Intake.Extend.in;
import static org.firstinspires.ftc.teamcode.Positions.Intake.Extend.out;

/**
 * Class representing the Extend mechanism of the robot.
 * It extends the ServoPositionMechanism class to control the extension servos.
 */
public class Extend extends ServoPositionMechanism {
    private final HardwareMap hardwareMap;
    /**
     * @param hardwareMap the hardware map to get the servos from.
     */
    public Extend(HardwareMap hardwareMap) {
        super(in);
        this.hardwareMap = hardwareMap;
    }
    /**
     * Gets the servos associated with the Extend mechanism.
     * @return an array of servos.
     */
    @Override
    protected Servo[] getServos() {
        return new Servo[]{hardwareMap.get(Servo.class, "Extend1"), hardwareMap.get(Servo.class, "Extend2")};
    }
}
/**
 * TeleOp class for testing the Extend mechanism manually.
 */
@TeleOp(name = "Intake Extend Test", group = "C Intake")
class ExtendTest extends ManualMechanismTeleOp {
    public ExtendTest() {
        super(Extend::new);
    }
}
/**
 * TeleOp class for testing the positions of the Extend mechanism.
 * @noinspection DuplicatedCode
 */
@TeleOp(name = "Intake Extend Positions Test", group = "D Intake")
@Config
class ExtendPositions extends LinearOpMode {
    public static double current = 0.0;
    @Override
    public void runOpMode() {
        Extend extend = new Extend(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                current = in;
            } else if (gamepad1.dpad_down) {
                current = out;
            } else {
                current += extend.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            }
            extend.setTargetPosition(current);

            TelemetryPacket packet = new TelemetryPacket();
            if (!extend.run(packet)) requestOpModeStop();
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
            telemetry.addData("Position of Extend", extend.getTargetPosition());
            telemetry.update();
        }
    }
}
