package org.firstinspires.ftc.teamcode.outtake;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.utils.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utils.ServoPositionMechanism;

import static org.firstinspires.ftc.teamcode.Positions.Outtake.Rotate.*;

/**
 * Class representing the vertical rotation of the outtake claw.
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
        return new Servo[]{hardwareMap.get(Servo.class, "Outtake Rotate")};
    }
}

/**
 * TeleOp class for testing the Rotate mechanism manually.
 */
@TeleOp(name = "Outtake Rotate Test", group = "C Outtake")
class RotateTest extends ManualMechanismTeleOp {
    public RotateTest() {
        super(Rotate::new);
    }
}

/**
 * TeleOp class for testing the Rotate mechanism positions.
 * @noinspection DuplicatedCode
 */
@TeleOp(name = "Outtake Rotate Positions Test", group = "D Outtake")
class RotatePositions extends LinearOpMode {
    public static double current = 0.0;
    @Override
    public void runOpMode() {
        Rotate rotate = new Rotate(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_down) current = transfer;
            else if (gamepad1.dpad_up) current = basket;
            else if (gamepad1.dpad_left) current = bar;
            else if (gamepad1.dpad_right) current = pickup;
            else current += rotate.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            rotate.setTargetPosition(current);

            TelemetryPacket packet = new TelemetryPacket();
            if(!rotate.run(packet)) requestOpModeStop();
            telemetry.addData("Position", current);
            telemetry.update();
        }
    }
}
