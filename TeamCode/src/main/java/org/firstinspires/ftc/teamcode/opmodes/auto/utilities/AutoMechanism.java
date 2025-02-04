package org.firstinspires.ftc.teamcode.opmodes.auto.utilities;

import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.opmodes.auto.bar.AutoPoses;

import java.util.Map;

public abstract class AutoMechanism extends OpMode {
    protected abstract void update();
    protected RobotHardware robot;
    PathCreatorFactory pathCreatorFactory;
    protected Map<?, PathChain> pathChains;
    Timer pathTimer, actionTimer, opModeTimer;
    protected int pathState;

    public void setPathState(int pathState) {
        this.pathState = pathState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        actionTimer = new Timer();
        opModeTimer = new Timer();

        robot = new RobotHardware(hardwareMap);
        pathCreatorFactory = new PathCreatorFactory(AutoPoses.values());
        pathChains = pathCreatorFactory.createPaths(robot.follower);
        robot.follower.setStartingPose(AutoPoses.startPose.getPose());
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        pathState = 0;
    }

    @Override
    public void loop() {
        robot.follower.update();
        update();
    }
}
