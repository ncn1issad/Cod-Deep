package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;
import kotlin.Pair;
import org.firstinspires.ftc.teamcode.Positions;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.intake.IntakePositions;
import org.firstinspires.ftc.teamcode.outtake.OuttakePositions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Clips", group = "A")
public class Clips extends LinearOpMode {
    Pose startPose = new Pose(9.0, 48.0, -Math.toRadians(90.0));
    Point[] samplePoints = new Point[] {
            new Point(29.0, 41.2),
            new Point(29.0, 31.0),
            new Point(29.0, 21.0)
    };
    double scoreAngle = Math.toRadians(180.0);
    Point scoreControl = new Point(16.0, 70.0);
    Pose pickupSpecimen = new Pose(17.0, 28.0, 0.0);
    Point[] specimenControl = new Point[]{
            new Point(24.0, 70.0),
            new Point(36.0, 36.0)
    };
    double sampleAngle = Math.toRadians(47.0);
    Point lastDropPoint = new Point(24.0, 28.0);
    double dropAngle = Math.toRadians(130.0);
    private final Timer pathTimer = new Timer();
    private int state = 0;
    private void setState(int newState) {
        state = newState;
        pathTimer.resetTimer();
    }

    /**
     * Returns an array of points that the robot will score at.
     * @param size the number of points to score at
     * @return an array of points that the robot will score at
     */
    @NotNull
    private Point[] getScorePoints(int size) {
        size = Range.clip(size, 1, 18);
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(41.75, 68.0 + 0.5 * i);
        }
        return points;
    }

    /** @noinspection SameParameterValue*/
    @NotNull
    private List<Pair<PathChain, PathChain>> getScorePaths(RobotHardware robot, int size) {
        Point[] score = getScorePoints(size);
        List<Pair<PathChain, PathChain>> paths = new ArrayList<>();
        for (Point point : score) {
            paths.add(new Pair<>(new PathBuilder().addBezierCurve(new Point(pickupSpecimen), specimenControl[0], point)
                            .setLinearHeadingInterpolation(0.0, scoreAngle, 0.8)
                            .addParametricCallback(0.4, () -> robot.outtake.setTargetPosition(OuttakePositions.BAR))
                            .build(),
                    new PathBuilder().addBezierCurve(point, specimenControl[1], new Point(pickupSpecimen))
                            .setLinearHeadingInterpolation(scoreAngle, 0.0, 0.8)
                            .addParametricCallback(0.5, () -> robot.outtake.setTargetPosition(OuttakePositions.PICKUP))
                            .build()));
        }
        return paths;
    }

    @Override
    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        RobotHardware robot = new RobotHardware(hardwareMap);
        Follower follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        PathChain[] pickupSamples = new PathChain[]{
                new PathBuilder().addBezierLine(new Point(startPose), samplePoints[0])
                        .setLinearHeadingInterpolation(startPose.getHeading(), sampleAngle)
                        .build(),
                new PathBuilder().addBezierLine(samplePoints[0], samplePoints[1])
                        .setLinearHeadingInterpolation(dropAngle, sampleAngle, 0.6)
                        .build(),
                new PathBuilder().addBezierLine(samplePoints[1], samplePoints[2])
                        .setLinearHeadingInterpolation(dropAngle, sampleAngle, 0.4)
                        .build()
        };
        PathChain lastDrop = new PathBuilder().addBezierLine(samplePoints[2], lastDropPoint)
                .setLinearHeadingInterpolation(sampleAngle, dropAngle)
                .build();
        PathChain firstScore = new PathBuilder().addBezierCurve(lastDropPoint, scoreControl, getScorePoints(1)[0])
                .setLinearHeadingInterpolation(dropAngle, scoreAngle, 0.8)
                .build();

        List<Pair<PathChain, PathChain>> scorePaths = getScorePaths(robot, 5);
        robot.intake.setTargetPosition(IntakePositions.TRANSFER);

        while (opModeInInit()) {
            TelemetryPacket packet = new TelemetryPacket();
            robot.run(packet);
            dashboard.sendTelemetryPacket(packet);
        }

        Timer opModeTimer = new Timer();

        while (!isStopRequested()) {
            if (opModeTimer.getElapsedTimeSeconds() >= 29.9) robot.cancel();

            switch (state) {
                case 0:
                    follower.followPath(pickupSamples[0]);
                    setState(1);
                case 1:
                    robot.outtake.setClaw(true);
                    robot.intake.setTargetPosition(IntakePositions.PICKUP);
                    robot.outtake.setTargetPosition(OuttakePositions.PICKUP);
                    robot.intake.spin.setTargetPosition(Positions.Intake.Spin.left);
                    if (!follower.isBusy()) {
                        robot.intake.pickup();
                        setState(2);
                    }
                case 2:
                    if (pathTimer.getElapsedTimeSeconds() > 0.2) {
                        follower.turnDegrees(85.0, false);
                        setState(3);
                    }
                case 3:
                    if (pathTimer.getElapsedTimeSeconds() > 1.0) {
                        robot.intake.setClaw(false);
                        follower.followPath(pickupSamples[1]);
                        setState(4);
                    }
                case 4:
                    if (!follower.isBusy()) {
                        robot.intake.pickup();
                        setState(5);
                    }
                case 5:
                    if (pathTimer.getElapsedTimeSeconds() > 0.2) {
                        follower.turnDegrees(85.0, false);
                        setState(6);
                    }
                case 6:
                    if (pathTimer.getElapsedTimeSeconds() > 1.0) {
                        robot.intake.setClaw(false);
                        follower.followPath(pickupSamples[2]);
                        setState(7);
                    }
                case 7:
                    if (!follower.isBusy()) {
                        robot.intake.pickup();
                        setState(8);
                    }
                case 8:
                    if (pathTimer.getElapsedTimeSeconds() > 0.2) {
                        follower.followPath(lastDrop);
                        setState(9);
                    }
                case 9:
                    if (!follower.isBusy()) {
                        robot.intake.setClaw(false);
                        robot.outtake.setTargetPosition(OuttakePositions.BAR);
                        robot.intake.setTargetPosition(IntakePositions.TRANSFER);
                        follower.followPath(firstScore);
                        setState(10);
                    }
                case 10:
                    if (!follower.isBusy()) {
                        robot.outtake.setClaw(false);
                        robot.outtake.lift.setTargetPosition(Positions.Outtake.Lift.down);
                        robot.outtake.pendulum.setTargetPosition(Positions.Outtake.Pendulum.clearBar);
                        follower.followPath(scorePaths.get(0).getSecond());
                        setState(11);
                    }
                case 11:
                    if (!follower.isBusy()) {
                        robot.intake.setClaw(true);
                        robot.outtake.setTargetPosition(OuttakePositions.BAR);
                        follower.followPath(scorePaths.get(1).getFirst());
                        scorePaths.get(0).getSecond().resetCallbacks();
                        setState(12);
                    }
                case 12:
                    if (!follower.isBusy()) {
                        robot.outtake.setClaw(false);
                        robot.outtake.lift.setTargetPosition(Positions.Outtake.Lift.down);
                        robot.outtake.pendulum.setTargetPosition(Positions.Outtake.Pendulum.clearBar);
                        follower.followPath(scorePaths.get(1).getSecond());
                        scorePaths.get(1).getFirst().resetCallbacks();
                        setState(13);
                    }
                case 13:
                    if (!follower.isBusy()) {
                        robot.intake.setClaw(true);
                        robot.outtake.setTargetPosition(OuttakePositions.BAR);
                        follower.followPath(scorePaths.get(2).getFirst());
                        scorePaths.get(1).getSecond().resetCallbacks();
                        setState(14);
                    }
                case 14:
                    if (!follower.isBusy()) {
                        robot.outtake.setClaw(false);
                        robot.outtake.lift.setTargetPosition(Positions.Outtake.Lift.down);
                        robot.outtake.pendulum.setTargetPosition(Positions.Outtake.Pendulum.clearBar);
                        follower.followPath(scorePaths.get(2).getSecond());
                        scorePaths.get(2).getFirst().resetCallbacks();
                        setState(15);
                    }
                case 15:
                    if (!follower.isBusy()) {
                        robot.intake.setClaw(true);
                        robot.outtake.setTargetPosition(OuttakePositions.BAR);
                        follower.followPath(scorePaths.get(3).getFirst());
                        scorePaths.get(2).getSecond().resetCallbacks();
                        setState(16);
                    }
                case 16:
                    if (!follower.isBusy()) {
                        robot.outtake.setClaw(false);
                        robot.outtake.lift.setTargetPosition(Positions.Outtake.Lift.down);
                        robot.outtake.pendulum.setTargetPosition(Positions.Outtake.Pendulum.clearBar);
                        follower.followPath(scorePaths.get(3).getSecond());
                        scorePaths.get(3).getFirst().resetCallbacks();
                        setState(17);
                    }
                case 17:
                    if (!follower.isBusy()) {
                        robot.intake.setClaw(true);
                        robot.outtake.setTargetPosition(OuttakePositions.BAR);
                        follower.followPath(scorePaths.get(4).getFirst());
                        setState(18);
                    }
                case 18:
                    if (!follower.isBusy()) {
                        robot.outtake.setClaw(false);
                        robot.outtake.setTargetPosition(OuttakePositions.TRANSFER);
                        setState(19);
                    }
            }

            TelemetryPacket packet = new TelemetryPacket();
            if (!robot.run(packet)) requestOpModeStop();
            follower.update();
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
