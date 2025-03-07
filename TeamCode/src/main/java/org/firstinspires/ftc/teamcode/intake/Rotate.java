package org.firstinspires.ftc.teamcode.intake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.utils.systems.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utils.systems.ServoPositionMechanism;

import static org.firstinspires.ftc.teamcode.Positions.Intake.Rotate.pickup;
import static org.firstinspires.ftc.teamcode.Positions.Intake.Rotate.transfer;

/**
 * Class representing the vertical rotation of the intake claw.
 * It extends the ServoPositionMechanism class to control the rotation servo.
 */
public class Rotate extends ServoPositionMechanism {
    private final HardwareMap hardwareMap;
    /**
     * @param hardwareMap the hardware map to get the servo from.
     */
    public Rotate(HardwareMap hardwareMap) {
        super(transfer);
        this.hardwareMap = hardwareMap;
    }
    /**
     * Gets the servo associated with the Rotate mechanism.
     * @return an array containing the rotation servo.
     */
    @Override
    protected Servo[] getServos() {
        return new Servo[]{hardwareMap.get(Servo.class, "Intake Rotate")};
    }
}

/**
 * TeleOp class for testing the Rotate mechanism manually.
 */
@TeleOp(name = "Intake Rotate Test", group = "C Intake")
@Disabled
class RotateTest extends ManualMechanismTeleOp {
    public RotateTest() {
        super(Rotate::new);
    }
}

/**
 * TeleOp class for testing the Rotate mechanism positions.
 * @noinspection DuplicatedCode
*/
@TeleOp(name = "Intake Rotate Positions Test", group = "D Intake")
@Config
@Disabled
class RotatePositions extends LinearOpMode {
    public static double current = 0.0;
    @Override
    public void runOpMode() {
        Rotate rotate = new Rotate(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) current = pickup;
            else if (gamepad1.dpad_down) current = transfer;
            else current += rotate.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            rotate.setTargetPosition(current);

            TelemetryPacket packet = new TelemetryPacket();
            if (!rotate.run(packet)) requestOpModeStop();
            FtcDashboard.getInstance().sendTelemetryPacket(packet);
            telemetry.addData("Current Position", rotate.getTargetPosition());
            telemetry.update();
        }
    }
}
