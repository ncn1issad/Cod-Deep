package org.firstinspires.ftc.teamcode.outtake;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.utils.systems.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utils.systems.ServoPositionMechanism;

import static org.firstinspires.ftc.teamcode.Positions.Outtake.Claw.close;
import static org.firstinspires.ftc.teamcode.Positions.Outtake.Claw.open;

/**
 * Class representing the Claw mechanism of the outtake.
 * It extends the ServoPositionMechanism class to control the claw servo.
 */
public class Claw extends ServoPositionMechanism {
    private final HardwareMap hardwareMap;
    /**
     * @param hardwareMap the hardware map to get the servo from.
     */
    public Claw (HardwareMap hardwareMap) {
        super(open);
        this.hardwareMap = hardwareMap;
        getServos()[0].setDirection(Servo.Direction.REVERSE);
    }
    /**
     * Gets the servo associated with the Claw mechanism.
     * @return an array containing the claw servo.
     */
    @Override
    protected Servo[] getServos() {
        return new Servo[]{hardwareMap.get(Servo.class, "Outtake Claw")};
    }
    /**
     * Checks if the claw is closed.
     * @return true if the claw is closed, false otherwise.
     */
    public boolean isClosed() {
        return getTargetPosition() >= close;
    }
    /**
     * Sets the claw position.
     * @param closed true to close the claw, false to open it.
     */
    public void set(boolean closed) {
        setTargetPosition(closed ? close : open);
    }
}
/**
 * TeleOp class for testing the Claw mechanism manually.
 */
@TeleOp(name = "Outtake Claw Test", group = "C Outtake")
@Disabled
class ClawTest extends ManualMechanismTeleOp {
    public ClawTest() {
        super(Claw::new);
    }
}
/**
 * TeleOp class for testing the positions of the Claw mechanism.
 * @noinspection DuplicatedCode
 */
@TeleOp(name = "Outtake Claw Positions Test", group = "D Outtake")
@Disabled
class ClawPositions extends LinearOpMode {
    @Override
    public void runOpMode() {
        Claw claw = new Claw(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                claw.set(true);
            } else if (gamepad1.dpad_down) {
                claw.set(false);
            } else {
                claw.setTargetPosition(claw.getTargetPosition() + claw.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger));
            }
            TelemetryPacket packet = new TelemetryPacket();
            if (!claw.run(packet)) requestOpModeStop();
            telemetry.addData("Position of Claw", claw.getTargetPosition());
            telemetry.update();
        }
    }
}
