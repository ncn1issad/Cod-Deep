package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.ActionRunnerAsync;
import org.firstinspires.ftc.teamcode.drive.CustomAction;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class Auto extends OpMode {
    RobotHardware robot = new RobotHardware(this);
    Pose2d startPose = new Pose2d(new Vector2d(0.0, 0.0), Math.toRadians(0.0));
    MecanumDrive drive = new MecanumDrive(robot.myOpMode.hardwareMap, startPose);
    ActionRunnerAsync runner = new ActionRunnerAsync();
    Trajectories trajectories = new Trajectories(drive);

    enum TrajectoryState {
        CLIP_SPECIMEN,
        _1_GET_SAMPLE,
        _1_PLACE_SAMPLE,
        _2_GET_SAMPLE,
        _2_PLACE_SAMPLE,
        _3_GET_SAMPLE,
        _3_PLACE_SAMPLE,
        PARK
    }

    CustomAction ClipSpecimen = trajectories.ClipSpecimen.apply(startPose);
    CustomAction _1GetSample = trajectories.GetSample1Basket.apply(ClipSpecimen.getPose());
    CustomAction _1PlaceSample = trajectories.PlaceSampleBasket.apply(_1GetSample.getPose());
    CustomAction _2GetSample = trajectories.GetSample2Basket.apply(_1PlaceSample.getPose());
    CustomAction _2PlaceSample = trajectories.PlaceSampleBasket.apply(_2GetSample.getPose());
    CustomAction _3GetSample = trajectories.GetSample3Basket.apply(_2PlaceSample.getPose());
    CustomAction _3PlaceSample = trajectories.PlaceSampleBasket.apply(_3GetSample.getPose());
    CustomAction Park = trajectories.ParkBasket.apply(_3PlaceSample.getPose());

    private TrajectoryState currentState = TrajectoryState.CLIP_SPECIMEN;

    @Override
    public void init() {
        robot.init();
        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
    }

    @Override
    public void loop() {
        switch (currentState) {
            case CLIP_SPECIMEN:
                if (!runner.isBusy) {
                    runner.runActionAsync(ClipSpecimen.getAction());
                    currentState = TrajectoryState._1_GET_SAMPLE;
                    break;
                }
            case _1_GET_SAMPLE:
                if (!runner.isBusy) {
                    runner.runActionAsync(_1GetSample.getAction());
                    currentState = TrajectoryState._1_PLACE_SAMPLE;
                    break;
                }
            case _1_PLACE_SAMPLE:
                if (!runner.isBusy) {
                    runner.runActionAsync(_1PlaceSample.getAction());
                    currentState = TrajectoryState._2_GET_SAMPLE;
                    break;
                }
            case _2_GET_SAMPLE:
                if (!runner.isBusy) {
                    runner.runActionAsync(_2GetSample.getAction());
                    currentState = TrajectoryState._2_PLACE_SAMPLE;
                    break;
                }
            case _2_PLACE_SAMPLE:
                if (!runner.isBusy) {
                    runner.runActionAsync(_2PlaceSample.getAction());
                    currentState = TrajectoryState._3_GET_SAMPLE;
                    break;
                }
            case _3_GET_SAMPLE:
                if (!runner.isBusy) {
                    runner.runActionAsync(_3GetSample.getAction());
                    currentState = TrajectoryState._3_PLACE_SAMPLE;
                    break;
                }
            case _3_PLACE_SAMPLE:
                if (!runner.isBusy) {
                    runner.runActionAsync(_3PlaceSample.getAction());
                    currentState = TrajectoryState.PARK;
                    break;
                }
            case PARK:
                if (!runner.isBusy) {
                    runner.runActionAsync(Park.getAction());
                    break;
                }
        }
    }
}
