package org.firstinspires.ftc.teamcode.opmodes.auto.bar;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathCallback;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.opmodes.auto.utilities.PathCreatorFactory;

import java.util.Map;

public class Auto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = new Follower(hardwareMap);

        PathCreatorFactory pathCreatorFactory = new PathCreatorFactory(AutoPoses.values());

        Map<?, PathChain> paths = pathCreatorFactory.createPaths(follower);

        waitForStart();

        PathChain path = paths.get(AutoPoses.startPose);
        if (path != null) {
            follower.followPath(path);
        }
    }
}
