package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public abstract class ManualMechanismTeleOp extends OpMode {
    ManualPositionFactory factory;
    ManualPositionMechanism mechanism;
    Follower follower;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    ManualMechanismTeleOp(ManualPositionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        mechanism = factory.manualPositionFactory(hardwareMap);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(0, 0, 0));
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        follower.setTeleOpMovementVectors(
            gamepad1.left_stick_x,
            gamepad1.left_stick_y,
            gamepad1.right_stick_x,
            true
        );
        follower.update();

        mechanism.setTargetPosition(
                mechanism.getTargetPosition() + mechanism.getMultiplier() * (gamepad1.right_trigger - gamepad1.left_trigger)
        );

        TelemetryPacket packet = new TelemetryPacket();

        if (!mechanism.run(packet)) requestOpModeStop();

        dashboard.sendTelemetryPacket(packet);
        dashboard.getTelemetry().update();

        telemetry.addData(
                "Position of " + mechanism.getClass().getSimpleName(),
                mechanism.getTargetPosition()
        );
        telemetry.update();
    }
}
