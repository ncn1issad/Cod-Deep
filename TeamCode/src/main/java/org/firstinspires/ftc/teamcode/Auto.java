package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import kotlin.Pair;
import org.firstinspires.ftc.teamcode.intake.IntakePositions;
import org.firstinspires.ftc.teamcode.outtake.OuttakePositions;
import org.firstinspires.ftc.teamcode.utils.AsyncOpMode;
import org.firstinspires.ftc.teamcode.utils.PositionStore;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Clips", group = "A")
class Clips extends AsyncOpMode {
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
    PathChain[] pickupSamples;
    PathChain lastDrop;
    PathChain firstScore;
    List<Pair<PathChain, PathChain>> scorePaths;
    private final Timer pathTimer = new Timer();
    private final Timer opModeTimer = new Timer();
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
    public void systemInit() {
        actions.add(robot);
        pickupSamples = new PathChain[]{
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
        lastDrop = new PathBuilder().addBezierLine(samplePoints[2], lastDropPoint)
                .setLinearHeadingInterpolation(sampleAngle, dropAngle)
                .build();
        firstScore = new PathBuilder().addBezierCurve(lastDropPoint, scoreControl, getScorePoints(1)[0])
                .setLinearHeadingInterpolation(dropAngle, scoreAngle, 0.8)
                .build();

        scorePaths = getScorePaths(robot, 5);
        robot.intake.setTargetPosition(IntakePositions.TRANSFER);
    }
    @Override
    public void systemStart() {
        opModeTimer.resetTimer();
    }
    @Override
    public void systemLoop() {
        if (opModeTimer.getElapsedTimeSeconds() >= 29.7) setState(-1);

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
            case 19:
                if (!follower.isBusy()) {
                    setState(-1);
                }
            case -1:
                robot.outtake.setClaw(false);
                robot.intake.setClaw(false);
                robot.intake.setTargetPosition(IntakePositions.TRANSFER);
                robot.outtake.setTargetPosition(OuttakePositions.TRANSFER);
                PositionStore.pose = follower.getPose();
                robot.cancel();
        }
    }
}

@Autonomous(name = "Basket", group = "A")
@Config
class Basket extends AsyncOpMode {
    Pose beginPose = new Pose(11.6, 118.0, Math.toRadians(45.0));
    Point[] samplePoints = new Point[] {
            new Point(21.5, 126.3),
            new Point(21.0, 128.6),
            new Point(23.2, 131.6)
    };
    Pose scorePose = new Pose(16.0, 128.0, Math.toRadians(-45.0));
    Pose parkPose = new Pose(64.2, 92.3, Math.toRadians(90.0));
    Point parkControl = new Point(65.5, 130.1);
    PathChain firstScore;
    List<Pair<PathChain, PathChain>> scores;
    PathChain park;
    public static double pickupDelay = 0.12;
    private final Timer pathTimer = new Timer();
    private final Timer opModeTimer = new Timer();
    private int state = 0;
    private void setState(int newState) {
        state = newState;
        pathTimer.resetTimer();
    }
    @NotNull
    private List<Pair<PathChain, PathChain>> getScores() {
        List<Pair<PathChain, PathChain>> scores = new ArrayList<>();
        for (Point point : samplePoints) {
            scores.add(new Pair<>(new PathBuilder().addBezierLine(new Point(scorePose), point)
                            .setTangentHeadingInterpolation()
                            .build(),
                    new PathBuilder().addBezierLine(point, new Point(scorePose))
                            .setLinearHeadingInterpolation(follower.getTotalHeading(), scorePose.getHeading(), 1.1)
                            .addParametricCallback(pickupDelay, () -> transfer(true))
                            .addParametricCallback(1.0, () -> robot.outtake.setTargetPosition(OuttakePositions.BASKET))
                            .build()));
        }
        return scores;
    }
    @Override
    public void systemInit() {
        actions.add(robot);
        firstScore = new PathBuilder().addBezierLine(new Point(beginPose), new Point(scorePose))
                .setLinearHeadingInterpolation(beginPose.getHeading(), scorePose.getHeading())
                .build();
        scores = getScores();
        park = new PathBuilder().addBezierCurve(new Point(scorePose), parkControl, parkControl)
                .setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading())
                .build();
        robot.intake.setTargetPosition(IntakePositions.TRANSFER);
    }
    @Override
    public void systemStart() {
        opModeTimer.resetTimer();
    }
    @Override
    public void systemLoop() {
        if (opModeTimer.getElapsedTimeSeconds() >= 29.7) setState(-1);

        switch (state) {
            case 0:
                follower.followPath(firstScore);
                robot.outtake.setTargetPosition(OuttakePositions.BASKET);
                setState(1);
            case 1:
                if (!follower.isBusy()) {
                    robot.outtake.setClaw(false);
                    addDelay(0.1, () -> {
                        follower.followPath(scores.get(0).getFirst());
                        robot.intake.setTargetPosition(IntakePositions.PICKUP);
                        setState(2);
                    });
                }
            case 2:
                if (!follower.isBusy()) {
                    robot.intake.spin.setTargetPosition(Positions.Intake.Spin.left);
                    robot.intake.pickup();
                    addDelay(pickupDelay, () -> {
                        follower.followPath(scores.get(0).getSecond());
                        setState(3);
                    });
                }
            case 3:
                if (!follower.isBusy()) {
                    robot.outtake.setClaw(false);
                    addDelay(0.1, () -> {
                        follower.followPath(scores.get(1).getFirst());
                        robot.intake.setTargetPosition(IntakePositions.PICKUP);
                        setState(4);
                    });
                }
            case 4:
                if (!follower.isBusy()) {
                    robot.intake.spin.setTargetPosition(Positions.Intake.Spin.middle);
                    robot.intake.pickup();
                    addDelay(pickupDelay, () -> {
                        follower.followPath(scores.get(1).getSecond());
                        setState(5);
                    });
                }
            case 5:
                if (!follower.isBusy()) {
                    robot.outtake.setClaw(false);
                    addDelay(0.1, () -> {
                        follower.followPath(scores.get(2).getFirst());
                        robot.intake.setTargetPosition(IntakePositions.PICKUP);
                        setState(6);
                    });
                }
            case 6:
                if (!follower.isBusy()) {
                    robot.intake.spin.setTargetPosition(Positions.Intake.Spin.right);
                    robot.intake.pickup();
                    addDelay(pickupDelay, () -> {
                        follower.followPath(scores.get(2).getSecond());
                        setState(7);
                    });
                }
            case 7:
                if (!follower.isBusy()) {
                    robot.outtake.setClaw(false);
                    addDelay(0.1, () -> {
                        follower.followPath(park);
                        robot.outtake.setTargetPosition(OuttakePositions.BAR);
                        setState(8);
                    });
                }
            case 8:
                if (!follower.isBusy()) {
                    setState(-1);
                }
            case -1:
                robot.outtake.setClaw(false);
                robot.intake.setClaw(false);
                robot.intake.setTargetPosition(IntakePositions.TRANSFER);
                robot.outtake.setTargetPosition(OuttakePositions.TRANSFER);
                PositionStore.pose = follower.getPose();
                robot.cancel();
        }
    }
}
