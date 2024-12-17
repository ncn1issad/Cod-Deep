package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.drive.CustomAction;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

import java.util.function.Function;

public class Trajectories {

    private MecanumDrive drive;

    public Trajectories(MecanumDrive drive) {
        this.drive = drive;
    }

    public Function<Pose2d, CustomAction> ClipSpecimen = (startPose) -> new CustomAction(
            drive.actionBuilder(startPose)
                .splineTo(new Vector2d(36, 72), Math.toRadians(270))
                .build(),
            new Vector2d(36, 72),
            Math.toRadians(270)
    );

    public Function<Pose2d, CustomAction> GetSample1Basket = (startPose) -> new CustomAction(
            drive.actionBuilder(startPose)
                    .splineTo(new Vector2d(5, 10), Math.toRadians(270))
                    .build(),
            new Vector2d(5, 10),
            Math.toRadians(270)
    );

    public Function<Pose2d, CustomAction> GetSample2Basket = (startPose) -> new CustomAction(
            drive.actionBuilder(startPose)
                    .splineTo(new Vector2d(5, 5), Math.toRadians(0))
                    .build(),
            new Vector2d(5, 5),
            Math.toRadians(0)
    );

    public Function<Pose2d, CustomAction> GetSample3Basket = (startPose) -> new CustomAction(
            drive.actionBuilder(startPose)
                    .splineTo(new Vector2d(5, 5), Math.toRadians(180))
                    .build(),
            new Vector2d(5, 5),
            Math.toRadians(180)
    );

    public Function<Pose2d, CustomAction> PlaceSampleBasket = (startPose) -> new CustomAction(
            drive.actionBuilder(startPose)
                    .splineTo(new Vector2d(15, 15), Math.toRadians(45))
                    .build(),
            new Vector2d(15, 15),
            Math.toRadians(45)
    );

    public Function<Pose2d, CustomAction> ParkBasket = (startPose) -> new CustomAction(
            drive.actionBuilder(startPose)
                    .splineTo(new Vector2d(0, 10), Math.toRadians(0))
                    .build(),
            new Vector2d(0, 10),
            Math.toRadians(0)
    );
}
