package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(61.5, 52, Math.toRadians(180)))
                        .forward(15)
                        .splineToConstantHeading(new Vector2d(25, 15), Math.toRadians(180))
                        .waitSeconds(0.5)
                        .lineToSplineHeading(new Pose2d(new Vector2d(58, 10), Math.toRadians(90)))
                        .splineToConstantHeading(new Vector2d(58, 24), Math.toRadians(90))
                        .lineToLinearHeading(new Pose2d(new Vector2d(50, 50), Math.toRadians(225)))
                        .waitSeconds(0.5)
                        .lineToSplineHeading(new Pose2d(new Vector2d(48, 40), Math.toRadians(270)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(50, 50), Math.toRadians(225)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(57, 24), Math.toRadians(0)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(50, 50), Math.toRadians(225)))
                        .waitSeconds(0.5)
                        .lineToSplineHeading(new Pose2d(new Vector2d(61.5, 52), Math.toRadians(180)))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}