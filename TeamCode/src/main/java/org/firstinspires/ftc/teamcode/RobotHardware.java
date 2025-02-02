package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.teamcode.systems.Outtake;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.subsystems.Lift;
import org.jetbrains.annotations.NotNull;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class RobotHardware implements Action {
    public Lift lift;
    public Intake intake;
    public Outtake outtake;
    Follower follower;
    public RobotHardware(HardwareMap hardwareMap) {
        lift = new Lift(hardwareMap);
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(0, 0, 0));
    }
    @Override
    public boolean run(@NotNull TelemetryPacket packet) {
        return lift.run(packet) && intake.run(packet) && outtake.run(packet);
    }

    public void start() {
        follower.startTeleopDrive();
    }

    public void movement(@NonNull Gamepad gamepad) {
        follower.setTeleOpMovementVectors(
                -gamepad.left_stick_y,
                -gamepad.left_stick_x,
                -gamepad.right_stick_x,
                true
        );
        follower.update();
    }

    public void actions(@NonNull Gamepad actionGamepad, @NonNull Gamepad movementGamepad) {
        if (actionGamepad.right_bumper || movementGamepad.right_bumper) {
            outtake.claw.close();
        } else if (actionGamepad.left_bumper || movementGamepad.left_bumper) {
            outtake.claw.open();
        }
        outtake.update(actionGamepad);
        intake.runIntake(actionGamepad.cross, actionGamepad.square);
    }
}
