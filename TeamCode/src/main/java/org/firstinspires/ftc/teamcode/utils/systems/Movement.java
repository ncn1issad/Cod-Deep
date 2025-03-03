package org.firstinspires.ftc.teamcode.utils.systems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public abstract class Movement extends LinearOpMode {
    public abstract void systemInit();
    public void systemStart() {

    }
    public abstract void systemLoop();

    @Override
    public void runOpMode() {
        systemInit();
        Follower follower = new Follower(hardwareMap);

        waitForStart();
        follower.startTeleopDrive();
        systemStart();

        while (opModeIsActive()) {
            follower.setTeleOpMovementVectors(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, false);
            follower.update();

            systemLoop();
        }
    }
}

@TeleOp(name = "Movement Test", group = "E")
@Disabled
class MovementTest extends Movement {
    @Override
    public void systemInit() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void systemLoop() {
        telemetry.addData("Status", "Running");
        telemetry.update();
    }
}
