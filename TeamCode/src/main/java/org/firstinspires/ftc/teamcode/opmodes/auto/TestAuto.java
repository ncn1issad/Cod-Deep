package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.pathgen.Path;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;

@Autonomous(name = "Simple Pedro Pathing Autonomous", group = "Autonomous")
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Initialize PedroFollower and hardware
        Follower follower = new Follower(hardwareMap);

        BezierCurve bezier = new BezierCurve(new Point(new Pose(0,0,0)), new Point(new Pose(24, 24, Math.toRadians(90))));

        // Define the path
        Path simplePath = new Path(bezier);// Move to (24, 24) facing 90 degrees

        // Wait for the start signal
        waitForStart();

        if (opModeIsActive()) {
            // Follow the path
            follower.followPath(simplePath);

            // Wait for the path to finish
            while (opModeIsActive() && follower.isBusy()) {
                follower.update();
                sleep(10); // Prevent overloading the CPU
            }
        }
    }
}
