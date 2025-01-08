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
                        .lineToLinearHeading(new Pose2d(new Vector2d(7, 36), Math.toRadians(270)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(12,41), Math.toRadians(90)))
                        .splineToLinearHeading(new Pose2d(new Vector2d(36, 28), Math.toRadians(0)), Math.toRadians(270))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(50, 50), Math.toRadians(225)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(46, 28), Math.toRadians(0)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(50, 50), Math.toRadians(225)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(new Vector2d(56, 28), Math.toRadians(0)))
                        .waitSeconds(0.5)
                        .back(14)
                        .lineToLinearHeading(new Pose2d(61.5, 52, Math.toRadians(180)))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}