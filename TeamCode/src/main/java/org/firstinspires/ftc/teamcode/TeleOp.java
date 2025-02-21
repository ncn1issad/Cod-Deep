package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.intake.IntakePositions;
import org.firstinspires.ftc.teamcode.outtake.OuttakePositions;
import org.firstinspires.ftc.teamcode.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "A")
@Config
public class TeleOp extends LinearOpMode {
    public static boolean useIntake = true;
    public static boolean useOuttake = true;
    private RobotHardware robot;
    private boolean outtakeIsOpen = false;
    @Override
    public void runOpMode() {
        robot = new RobotHardware(hardwareMap);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Follower follower = new Follower(hardwareMap);
        follower.setStartingPose(PositionStore.pose);

        TogglePress clawToggle = new TogglePress(() -> gamepad1.right_bumper);
        SinglePress intakePositionSet = new SinglePress(() -> gamepad1.cross);

        List<CancelableAction> actions = new ArrayList<>();
        if (useIntake) actions.add(robot.intake);
        if (useOuttake) actions.add(robot.outtake);

        while (!isStarted()) {
            TelemetryPacket packet = new TelemetryPacket();
            actions.removeIf(action -> !action.run(packet));
            dashboard.sendTelemetryPacket(packet);
        }
        waitForStart();

        dashboard.clearTelemetry();
        follower.startTeleopDrive();

        while (opModeIsActive()) {
            double powerMultiplier = robot.intake.getTargetPosition() == IntakePositions.PICKUP ? 0.37 : 1.0;
            boolean clawCache = clawToggle.isToggled();

            follower.setTeleOpMovementVectors(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x * powerMultiplier,
                    -gamepad1.right_stick_x * powerMultiplier,
                    false
            );
            follower.update();
            follower.drawOnDashBoard();

            applyPositions(gamepad2);

            // Set the pendulum position to clear the bar if the outtake is set to the bar position
            if (!clawCache && robot.outtake.getTargetPosition() == OuttakePositions.BAR)
                robot.outtake.pendulum.setTargetPosition(Positions.Outtake.Pendulum.clearBar);
            // Make sure the claw position matches the outtake position
            if (!clawCache && outtakeIsOpen) {
                clawCache = true;
                outtakeIsOpen = false;
            }

            // Update systems and telemetry
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Robot Pose", follower.getPose().getAsPedroCoordinates());
            actions.removeIf(action -> !action.run(packet));
            dashboard.sendTelemetryPacket(packet);
            // Reset the robot's pose
            if (gamepad1.left_stick_button) follower.setPose(new Pose(0, 0, 0));
            // Toggle the claw position
            robot.outtake.setClaw(clawCache);
            if (robot.intake.getTargetPosition() == IntakePositions.TRANSFER &&
                robot.outtake.getTargetPosition() == OuttakePositions.TRANSFER)
                robot.intake.setClaw(!clawCache);
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
            // Intake position set
            if (intakePositionSet.isPressed()) {
                if (robot.intake.getTargetPosition() == IntakePositions.PICKUP)
                    robot.intake.setTargetPosition(IntakePositions.TRANSFER);
                else robot.intake.setTargetPosition(IntakePositions.PICKUP);
            }
        }
    }

    /**
     * Apply positions to the robot hardware based on the gamepad input
     * @param gamepad the gamepad to get the input from
     */
    private void applyPositions(@NotNull Gamepad gamepad) {
        PositionPair pair;
        if (gamepad.dpad_down) pair = new PositionPair(OuttakePositions.TRANSFER, IntakePositions.TRANSFER);
        else if (gamepad.dpad_up) pair = new PositionPair(OuttakePositions.BASKET, null);
        else if (gamepad.dpad_left) pair = new PositionPair(OuttakePositions.BAR, null);
        else if (gamepad.dpad_right) {
            outtakeIsOpen = true;
            pair = new PositionPair(OuttakePositions.PICKUP, IntakePositions.PICKUP);
        } else pair = new PositionPair(null, null);

        if (pair.outtake != null) robot.outtake.setTargetPosition(pair.outtake);
        if (pair.intake != null) robot.intake.setTargetPosition(pair.intake);
    }
}
