package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

import java.util.function.Function;

public class Trajectories {

    private MecanumDrive drive;

    public Trajectories(MecanumDrive drive) {
        this.drive = drive;
    }

    public Function<Pose2d, Action> ClipSpecimen = (startPose) -> drive.actionBuilder(startPose)
            .splineTo(new Vector2d(36, 72), Math.toRadians(270))
            .build();

    public Function<Pose2d, Action> GetSample1Bascket = (startPose) -> drive.actionBuilder(startPose)
            .splineTo(new Vector2d(5, 10), Math.toRadians(270))
            .build();
}
