package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.ProfileParams;
import com.acmerobotics.roadrunner.TrajectoryBuilderParams;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class Auto extends OpMode {
    RobotHardware robot = new RobotHardware(this);
    Pose2d startPose = new Pose2d(new Vector2d(0.0, 0.0), Math.toRadians(0.0));
    MecanumDrive drive = new MecanumDrive(robot.myOpMode.hardwareMap, startPose);

    TrajectoryBuilderParams buildParams = new TrajectoryBuilderParams(1e-6, new ProfileParams(0.25,0.1,1e-2));

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

    // Define ClipSpecimen as a TimeTrajectory
    Action ClipSpecimen = drive.actionBuilder(startPose)
            .splineTo(new Vector2d(36, 72), Math.toRadians(0.0))
            .build();

    // Define subsequent actions
    Action _1GetSample = drive.actionBuilder(new Pose2d(new Vector2d(36, 72), Math.toRadians(0.0)))
            .splineTo(new Vector2d(5, 10), Math.toRadians(270))
            .build();

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
                    Actions.runBlocking(ClipSpecimen);
                    currentState = TrajectoryState._1_GET_SAMPLE;
                    break;
            case _1_GET_SAMPLE:
                Actions.runBlocking(_1GetSample);
                currentState = TrajectoryState._1_PLACE_SAMPLE;
                break;
            // Handle other cases
            default:
                break;
        }
    }
}
