package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.PIDFController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.teamcode.intake.IntakePositions;
import org.firstinspires.ftc.teamcode.outtake.OuttakePositions;
import org.firstinspires.ftc.teamcode.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * TeleOp mode for the robot.
 * <p>
 * This mode allows the driver to control the robot using the gamepads.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "A")
@Config
public class TeleOp extends AsyncOpMode {
    /**
     * Flag to indicate whether the intake system should be used.
     */
    public static boolean useIntake = true;
    /**
     * Flag to indicate whether the outtake system should be used.
     */
    public static boolean useOuttake = true;
    private boolean holdHeading = false;
    private PIDFController headingPID;
    private List<PressAction> pressActions;
    @Override
    public void systemInit() {
        headingPID = new PIDFController(FollowerConstants.headingPIDFCoefficients);
        headingPID.setTargetPosition(0.0);
        if (useIntake) actions.add(robot.intake);
        if (useOuttake) actions.add(robot.outtake);
        pressActions = getPressActions();
    }
    @Override
    public void systemStart() {
        follower.startTeleopDrive();
    }
    @Override
    public void systemLoop() {
        // Pickup precision gear reduction
        double powerMultiplier = robot.intake.getTargetPosition() == IntakePositions.PICKUP ? 0.37 : 1.0;
        // Hold heading near the output bar
        double headingDrive;
        if (holdHeading) {
            double modifiedHeading;
            follower.setHeadingOffset(follower.getHeadingOffset() + gamepad1.left_stick_x * 0.025);
            if (follower.getPose().getHeading() <= Math.PI)
                modifiedHeading = follower.getPose().getHeading();
            else modifiedHeading = follower.getPose().getHeading() - 2 * Math.PI;
            RobotLog.d("Modified PID heading: " + modifiedHeading);

            headingPID.updatePosition(modifiedHeading);
            headingDrive = headingPID.runPIDF();
        } else headingDrive = gamepad1.right_stick_x * powerMultiplier;
        // Update the follower
        follower.setTeleOpMovementVectors(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x * powerMultiplier,
                headingDrive,
                false
        );
        // Run all actions
        for (PressAction action : pressActions) action.run();
        // Reset the robot's pose
        if (gamepad1.left_stick_button) follower.setPose(new Pose(0, 0, 0));
        // Pickup controls
        if (gamepad1.right_bumper) robot.intake.pickup();
        else if (gamepad1.left_bumper) {
            robot.intake.setClaw(false);
            robot.intake.spin.setTargetPosition(Positions.Intake.Spin.middle);
        }
        // Intake controls
        robot.intake.spin.setTargetPosition(
                robot.intake.spin.getTargetPosition() +
                        (gamepad1.right_trigger - gamepad1.left_trigger) *
                                robot.intake.spin.getMultiplier()
        );
        if (gamepad1.right_trigger > 0.8) {
            robot.intake.setClaw(false);
            robot.intake.spin.setTargetPosition(Positions.Intake.Spin.right);
        } else if (gamepad1.left_trigger > 0.8) {
            robot.intake.setClaw(false);
            robot.intake.spin.setTargetPosition(Positions.Intake.Spin.left);
        }
        // Rest of the system controls
        applyPositions(gamepad2);
    }

    /**
     * Apply positions to the robot hardware based on the gamepad input
     * @param gamepad the gamepad to get the input from
     */
    private void applyPositions(@NotNull Gamepad gamepad) {
        PositionPair pair;
        if (gamepad.dpad_right) pair = new PositionPair(OuttakePositions.TRANSFER, IntakePositions.TRANSFER);
        else if (gamepad.dpad_up) pair = new PositionPair(OuttakePositions.BASKET, null);
        else if (gamepad.dpad_left) pair = new PositionPair(OuttakePositions.BAR, null);
        else if (gamepad.dpad_down) {
            pair = new PositionPair(OuttakePositions.PICKUP, IntakePositions.PICKUP);
        } else pair = new PositionPair(null, null);

        if (pair.outtake != null) robot.outtake.setTargetPosition(pair.outtake);
        if (pair.intake != null) robot.intake.setTargetPosition(pair.intake);
    }
    /**
     * @return true if the robot is in the transfer position, false otherwise
     */
    private boolean inTransfer() {
        return robot.intake.getTargetPosition() == IntakePositions.TRANSFER &&
               robot.outtake.getTargetPosition() == OuttakePositions.TRANSFER;
    }
    /**
     * Finish the transfer by closing the claw and opening the intake
     */
    private void finishTransfer() {
        robot.outtake.setClaw(true);
        addDelay(0.25, () -> robot.intake.setClaw(false));
    }
    /**
     * @return a list of press actions for the teleop mode
     */
    @NotNull
    private List<PressAction> getPressActions() {
        List<PressAction> pressActions = new ArrayList<>();
        // Adding the actions
        // Toggle the claw positions
        pressActions.add(new PressAction(() -> gamepad2.right_bumper, () -> {
            // Claw controls in transfer position
            if (inTransfer()) {
                // Transfer from outtake to intake
                if (robot.outtake.isClosed()) {
                    robot.intake.setClaw(true);
                    addDelay(0.25, () -> robot.outtake.setClaw(false));
                // Transfer from intake to outtake
                } else finishTransfer();
            // Claw controls for realising the specimen after clipping it to the bar
            } else if (robot.outtake.isClosed() && robot.outtake.getTargetPosition() == OuttakePositions.BAR) {
                robot.outtake.setClaw(false);
                robot.outtake.pendulum.setTargetPosition((Positions.Outtake.Pendulum.bar + Positions.Outtake.Pendulum.pickup) / 2);
            // Claw controls for the rest of the positions
            } else robot.outtake.setClaw(!robot.outtake.isClosed());
            // Stop holding heading after transfer to the outtake
            if (robot.outtake.isClosed()) holdHeading = false;
        }));
        // Toggle between the intake positions
        pressActions.add(new PressAction(() -> gamepad2.cross, () -> robot.intake.switchTarget()));
        // Toggling the holdHeading variable
        pressActions.add(new PressAction(() -> gamepad1.cross, () -> holdHeading = !holdHeading));
        // Going to the hang position
        pressActions.add(new PressAction(() -> gamepad2.right_stick_button, () -> robot.outtake.setTargetPosition(OuttakePositions.HANG)));
        // Hanging
        pressActions.add(new PressAction(() -> gamepad2.left_stick_button, () -> robot.outtake.lift.setTargetPosition(Positions.Outtake.Lift.hang)));
        // Outtake controls for basket
        pressActions.add(new PressAction(() -> gamepad2.circle, () -> {
            if (inTransfer()) {
                if (!robot.outtake.isClosed()) {
                    finishTransfer();
                    addDelay(0.3, () -> robot.outtake.setTargetPosition(OuttakePositions.BASKET));
                } else addDelay(0.3, () -> robot.outtake.setTargetPosition(OuttakePositions.BASKET));
            } else robot.outtake.lift.setTargetPosition(Positions.Outtake.Lift.up);
        }));
        // Outtake controls for exiting bar
        pressActions.add(new PressAction(() -> gamepad2.left_stick_y > 0.7, () -> {
            robot.outtake.setTargetPosition(OuttakePositions.TRANSFER);
            robot.outtake.lift.setTargetPosition(Positions.Outtake.Lift.half);
            robot.outtake.setClaw(false);
        }));
        // Transfer controls
        pressActions.add(new PressAction(() -> gamepad2.dpad_right, () -> {
            robot.outtake.setClaw(false);
            holdHeading = false;
            if (robot.intake.getTargetPosition() == IntakePositions.TRANSFER)
                robot.outtake.setTargetPosition(OuttakePositions.TRANSFER);
            else {
                robot.intake.setTargetPosition(IntakePositions.TRANSFER);
                addDelay(0.2, () -> robot.outtake.setTargetPosition(OuttakePositions.TRANSFER));
            }
        }));

        return pressActions;
    }
}
