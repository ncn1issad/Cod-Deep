package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.outtake.*;
import org.firstinspires.ftc.teamcode.utils.CancelableAction;
import org.firstinspires.ftc.teamcode.utils.Movement;
import org.jetbrains.annotations.NotNull;

/**
 * Class representing the Outtake mechanism of the robot.
 * Implements the CancelableAction interface to control various outtake components.
 */
public class Outtake implements CancelableAction {
    public Claw claw;
    public Pendulum pendulum;
    public Rotate rotate;
    /**
     * Constructor for the Outtake class.
     * Initializes all the outtake components using the provided hardware map.
     * @param hardwareMap the hardware map to use for initialization.
     */
    public Outtake(HardwareMap hardwareMap) {
        claw = new Claw(hardwareMap);
        pendulum = new Pendulum(hardwareMap);
        rotate = new Rotate(hardwareMap);
    }
    /**
     * Cancels all outtake actions.
     */
    @Override
    public void cancel() {
        claw.cancel();
        pendulum.cancel();
        rotate.cancel();
    }
    /**
     * Runs the outtake mechanism and updates the telemetry packet.
     * @param packet the telemetry packet to update.
     * @return true if all components are running, false otherwise.
     */
    @Override
    public boolean run(@NotNull TelemetryPacket packet) {
        return claw.run(packet) && pendulum.run(packet) && rotate.run(packet);
    }
    private OuttakePositions targetPosition;
    /**
     * Sets the target position for the outtake mechanism.
     * @param targetPosition the target position to set.
     */
    public void setTargetPosition(@NotNull OuttakePositions targetPosition) {
        this.targetPosition = targetPosition;
        pendulum.setTargetPosition(targetPosition.getPendulum());
        rotate.setTargetPosition(targetPosition.getRotate());
    }
    /**
     * Gets the target position for the outtake mechanism.
     * @return the target position.
     */
    public OuttakePositions getTargetPosition() {
        return targetPosition;
    }
    /**
     * Sets the claw position for the outtake mechanism.
     * @param isClosed the position to set.
     */
    public void setClaw(boolean isClosed) {
        claw.set(isClosed);
    }
    /**
     * Checks if the claw is closed.
     * @return true if the claw is closed, false otherwise.
     */
    public boolean isClosed() {
        return claw.isClosed();
    }
}
/**
 * TeleOp mode for testing the Outtake mechanism.
 * This class extends Movement and provides a simple teleop mode
 * to test the functionality of the Outtake mechanism.
 */
@TeleOp(name = "Outtake Test", group = "B")
class OuttakeTest extends Movement {
    private Outtake outtake;
    private FtcDashboard dash;
    @Override
    public void systemInit() {
        outtake = new Outtake(hardwareMap);
        dash = FtcDashboard.getInstance();
    }
    @Override
    public void systemLoop() {
        if (gamepad1.dpad_up) outtake.setTargetPosition(OuttakePositions.PICKUP);
        else if (gamepad1.dpad_down) outtake.setTargetPosition(OuttakePositions.TRANSFER);
        else if (gamepad1.dpad_left) outtake.setTargetPosition(OuttakePositions.BAR);
        else if (gamepad1.dpad_right) outtake.setTargetPosition(OuttakePositions.BASKET);

        if (gamepad1.right_bumper) outtake.setClaw(true);
        else if (gamepad1.left_bumper) outtake.setClaw(false);

        TelemetryPacket packet = new TelemetryPacket();
        if (!outtake.run(packet)) requestOpModeStop();
        dash.sendTelemetryPacket(packet);
    }
}
/**
 * TeleOp mode for manually controlling the Outtake mechanism.
 * This class extends LinearOpMode and provides a manual teleop mode
 * to control the Outtake mechanism using gamepad inputs.
 */
@TeleOp(name = "Manual Outtake Test", group = "C")
class OuttakeManual extends LinearOpMode {
    @Override
    public void runOpMode() {
        Outtake outtake = new Outtake(hardwareMap);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        outtake.setTargetPosition(OuttakePositions.TRANSFER);

        waitForStart();
        double rotate = outtake.rotate.getTargetPosition();
        double pendulum = outtake.pendulum.getTargetPosition();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) outtake.setClaw(true);
            else if (gamepad1.left_bumper) outtake.setClaw(false);

            rotate += outtake.rotate.getMultiplier() * gamepad1.right_stick_y;
            pendulum += outtake.pendulum.getMultiplier() * gamepad1.left_stick_y;

            outtake.rotate.setTargetPosition(rotate);
            outtake.pendulum.setTargetPosition(pendulum);

            TelemetryPacket packet = new TelemetryPacket();
            if (!outtake.run(packet)) requestOpModeStop();
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
