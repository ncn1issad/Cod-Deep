package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.intake.*;
import org.firstinspires.ftc.teamcode.utils.CancelableAction;
import org.firstinspires.ftc.teamcode.utils.Movement;
import org.jetbrains.annotations.NotNull;

/**
 * Class representing the Intake mechanism of the robot.
 * Implements the CancelableAction interface to control various intake components.
 */
public class Intake implements CancelableAction {
    public Claw claw;
    public Extend extend;
    public Pendulum pendulum;
    public Rotate rotate;
    public Spin spin;
    /**
     * Constructor for the Intake class.
     * Initializes all intake components using the provided hardware map.
     * @param hardwareMap the hardware map to get the components from.
     */
    public Intake(HardwareMap hardwareMap) {
        claw = new Claw(hardwareMap);
        extend = new Extend(hardwareMap);
        pendulum = new Pendulum(hardwareMap);
        rotate = new Rotate(hardwareMap);
        spin = new Spin(hardwareMap);
    }
    /**
     * Cancels all intake actions.
     */
    @Override
    public void cancel() {
        claw.cancel();
        extend.cancel();
        pendulum.cancel();
        rotate.cancel();
        spin.cancel();
    }
    private boolean needsPickup = false;
    private final Timer pickupTimer = new Timer();
    /**
     * Runs the intake mechanism and updates the telemetry packet.
     * @param packet the telemetry packet to update.
     * @return true if all components are running, false otherwise.
     */
    @Override
    public boolean run(@NotNull TelemetryPacket packet) {
        if (needsPickup) pendulum.setTargetPosition(Positions.Intake.Pendulum.pickup);
        if (needsPickup && pickupTimer.getElapsedTime() >= 0.1) claw.set(true);
        if (needsPickup && pickupTimer.getElapsedTime() >= 0.2) {
            needsPickup = false;
            pendulum.setTargetPosition(Positions.Intake.Pendulum.pickupWait);
        }
        return claw.run(packet) && extend.run(packet) && pendulum.run(packet) && rotate.run(packet) && spin.run(packet);
    }
    private IntakePositions targetPosition;
    /**
     * Sets the target position for the intake mechanism.
     * @param targetPosition the target position to set.
     */
    public void setTargetPosition(@NotNull IntakePositions targetPosition) {
        this.targetPosition = targetPosition;
        extend.setTargetPosition(targetPosition.getExtend());
        pendulum.setTargetPosition(targetPosition.getPendulum());
        rotate.setTargetPosition(targetPosition.getRotate());
        spin.setTargetPosition(targetPosition.getSpin());
    }
    /**
     * Gets the current target position of the intake mechanism.
     * @return the current target position.
     */
    public IntakePositions getTargetPosition() {
        return targetPosition;
    }
    /**
     * Initiates the pickup action for the intake mechanism.
     */
    public void pickup() {
        if (getTargetPosition() != IntakePositions.PICKUP) return;
        needsPickup = true;
        pickupTimer.resetTimer();
    }
    /**
     * Sets the state of the claw.
     * @param isClosed true to close the claw, false to open it.
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
 * TeleOp mode for testing the Intake mechanism.
 * This class extends Movement and provides a simple teleop mode
 * to test the functionality of the Intake mechanism.
 */
@TeleOp(name = "Intake Test", group = "B")
class IntakeTest extends Movement {
    private Intake intake;
    private FtcDashboard dash;
    @Override
    public void systemInit() {
        intake = new Intake(hardwareMap);
        dash = FtcDashboard.getInstance();
    }

    @Override
    public void systemLoop() {
        if (gamepad1.dpad_down) intake.setTargetPosition(IntakePositions.PICKUP);
        else if (gamepad1.dpad_up) intake.setTargetPosition(IntakePositions.TRANSFER);

        if (gamepad1.right_bumper) intake.pickup();
        if (gamepad1.left_bumper) intake.setClaw(false);

        intake.spin.setTargetPosition(
                intake.spin.getTargetPosition() + intake.spin.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger)
        );

        if (gamepad1.options) intake.cancel();

        TelemetryPacket packet = new TelemetryPacket();
        if(!intake.run(packet)) requestOpModeStop();
        dash.sendTelemetryPacket(packet);
    }
}
/**
 * TeleOp mode for manually controlling the Intake mechanism.
 * This class extends LinearOpMode and provides a manual teleop mode
 * to control the Intake mechanism using gamepad inputs.
 */
@TeleOp(name = "Manual Intake Test", group = "C")
class IntakeManual extends LinearOpMode {
    @Override
    public void runOpMode() {
        Intake intake = new Intake(hardwareMap);
        FtcDashboard dash = FtcDashboard.getInstance();
        intake.setTargetPosition(IntakePositions.PICKUP);

        waitForStart();
        double rotate = intake.rotate.getTargetPosition();
        double spin = intake.spin.getTargetPosition();
        double pendulum = intake.pendulum.getTargetPosition();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) intake.setClaw(true);
            else if (gamepad1.left_bumper) intake.setClaw(false);

            rotate += intake.rotate.getMultiplier() * gamepad1.right_stick_y;
            spin += intake.spin.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger);
            pendulum += intake.pendulum.getMultiplier() * gamepad1.left_stick_y;

            intake.rotate.setTargetPosition(rotate);
            intake.spin.setTargetPosition(spin);
            intake.pendulum.setTargetPosition(pendulum);

            TelemetryPacket packet = new TelemetryPacket();
            if(!intake.run(packet)) requestOpModeStop();
            dash.sendTelemetryPacket(packet);
        }
    }
}
