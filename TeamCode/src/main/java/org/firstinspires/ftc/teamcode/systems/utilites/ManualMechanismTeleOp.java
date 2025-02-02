package org.firstinspires.ftc.teamcode.systems.utilites;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionFactory;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionMechanism;
import org.jetbrains.annotations.NotNull;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public abstract class ManualMechanismTeleOp extends OpMode {
    ManualPositionMechanism manualPositionMechanism;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Follower follower;

    protected double multiplier = 0.003;
    private final ManualPositionFactory factory;

    public ManualMechanismTeleOp(@NotNull ManualPositionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(0, 0, 0));

        manualPositionMechanism = factory.manualPositionFactory(hardwareMap);
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
        // Update the manual position mechanism
        manualPositionMechanism.setTargetPosition(
                manualPositionMechanism.getTargetPosition() + gamepad1.left_stick_y * multiplier
        );
        TelemetryPacket packet = new TelemetryPacket();
        // Checks if the action is cancelled
        if (!manualPositionMechanism.run(packet)) { requestOpModeStop(); }
        // Sends telemetry data to the dashboard
        dashboard.sendTelemetryPacket(packet);
        dashboard.getTelemetry().update();
        // Sends telemetry data to the driver station
        telemetry.addData(
                "Positions for " + manualPositionMechanism.getClass().getSimpleName(),
                manualPositionMechanism.getTargetPosition()
        );
        telemetry.update();
    }
}
