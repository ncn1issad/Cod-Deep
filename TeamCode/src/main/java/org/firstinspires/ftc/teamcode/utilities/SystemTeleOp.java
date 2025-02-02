package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public abstract class SystemTeleOp extends OpMode {
    SystemMechanism systemMechanism;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Follower follower;
    private final SystemFactory factory;

    public SystemTeleOp(SystemFactory factory) {
        this.factory = factory;
    }

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(0, 0, 0));

        systemMechanism = factory.systemFactory(hardwareMap);
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        // Update Pedro to move the robot based on:
        follower.setTeleOpMovementVectors(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                true
        );
        follower.update();
        // Update the system mechanism
        systemMechanism.update(gamepad2);
        TelemetryPacket packet = new TelemetryPacket();
        // Checks if the action is cancelled
        if (!systemMechanism.run(packet)) { requestOpModeStop(); }
        // Sends telemetry data to the dashboard
        dashboard.sendTelemetryPacket(packet);
        dashboard.getTelemetry().update();
        // Sends telemetry data to the driver station
        telemetry.addData(
                "Positions for " + systemMechanism.getClass().getSimpleName(),
                systemMechanism.getTargetPosition().name()
        );
        telemetry.update();
    }
}
