package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.intake.IntakePositions;
import org.firstinspires.ftc.teamcode.outtake.OuttakePositions;
import org.firstinspires.ftc.teamcode.utils.systems.CancelableAction;

import java.util.ArrayList;
import java.util.List;

public abstract class AsyncOpMode extends LinearOpMode {
    /**
     * The robot hardware.
     */
    protected RobotHardware robot;
    /**
     * The Pedro follower.
     */
    protected Follower follower;
    /**
     * The list of systems that need updating.
     */
    protected List<CancelableAction> actions;
    /**
     * The delayed actions.
     */
    private final DelayedActions delayed = new DelayedActions();
    /**
     * Adds an action to be run after a delay.
     * @param delay the delay in seconds.
     * @param action the action to run.
     */
    protected void addDelay(double delay, Runnable action) {
        delayed.addDelayed(delay, action);
    }
    /**
     * Init of the opMode
     */
    public abstract void systemInit();
    /**
     * Loop for the init of the opMode (if needed)
     */
    public void systemInitLoop() {

    }
    /**
     * Start of the opMode (after waitForStart(), if needed)
     */
    public void systemStart() {

    }
    /**
     * Loop for the opMode
     */
    public abstract void systemLoop();
    @Override
    public void runOpMode() {
        robot = new RobotHardware(hardwareMap);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        follower = new Follower(hardwareMap);
        actions = new ArrayList<>();
        systemInit();
        while (!isStarted()) {
            TelemetryPacket packet = new TelemetryPacket();
            actions.removeIf(action -> !action.run(packet));
            dashboard.sendTelemetryPacket(packet);
            systemInitLoop();
        }
        dashboard.clearTelemetry();
        systemStart();
        while (opModeIsActive()) {
            delayed.run();
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Robot Pose", follower.getPose().getAsPedroCoordinates());
            actions.removeIf(action -> !action.run(packet));
            dashboard.sendTelemetryPacket(packet);
            follower.update();
            follower.drawOnDashBoard();
            systemLoop();
        }
    }

    protected void transfer(boolean auto) {
        robot.outtake.setClaw(false);
        if (robot.intake.getTargetPosition() == IntakePositions.PICKUP) {
            robot.intake.setTargetPosition(IntakePositions.TRANSFER);
            addDelay(0.2, () -> robot.outtake.setTargetPosition(OuttakePositions.TRANSFER));
        } else robot.outtake.setTargetPosition(OuttakePositions.TRANSFER);
        if (auto) addDelay(0.5, () -> {
            if (robot.outtake.isClosed()) {
                robot.intake.setClaw(true);
                addDelay(0.1, () -> robot.outtake.setClaw(false));
            } else {
                robot.outtake.setClaw(true);
                addDelay(0.1, () -> robot.intake.setClaw(false));
            }
        });
    }
}
