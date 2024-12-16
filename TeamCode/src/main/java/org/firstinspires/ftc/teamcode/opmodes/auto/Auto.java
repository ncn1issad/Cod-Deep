package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.ActionRunnerAsync;
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
        PLACE_SAMPLE,
        _2_GET_SAMPLE,
        _3_GET_SAMPLE,
        PARK
    }

    // Define ClipSpecimen as a TimeTrajectory
    Action ClipSpecimen = trajectories.ClipSpecimen.apply(startPose);

    // Define subsequent actions
    Action _1GetSample = trajectories.GetSample1Bascket.apply(new Pose2d(new Vector2d(36, 72), Math.toRadians(0.0)));

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
                    runner.runActionAsync(ClipSpecimen);
                    currentState = TrajectoryState._1_GET_SAMPLE;
                    break;
                }
            case _1_GET_SAMPLE:
                if (!runner.isBusy) {
                    runner.runActionAsync(_1GetSample);
                    currentState = TrajectoryState.PLACE_SAMPLE;
                    break;
                }
                // Handle other cases
            default:
                break;
        }
    }
}
