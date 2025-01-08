package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Outtake;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "TeleOp", group = "A")
public class Teleop extends OpMode {
    final RobotHardware robot = new RobotHardware(this);

    final Intake intake = robot.intake;
    final Outtake outtake = robot.outtake;

    FtcDashboard dashboard;

    private final ElapsedTime timer = new ElapsedTime();

    private static Gamepad Move;
    private static Gamepad Action;

    @Override
    public void init() {
        robot.init();

        Move = gamepad1;
        Action = gamepad2;

        dashboard = FtcDashboard.getInstance();

        robot.update(dashboard);
    }

    private boolean pendulIsActioned = false;

    @Override
    public void loop() {
        // Update the position of systems
        robot.update(dashboard);
        // Movement of the robot
        robot.movement(Move);
        // Intake Motor functions
        robot.intake.runIntake(Move.cross, Move.square);
        // Extend functions
        robot.intake.extend.target += (Action.left_trigger - Action.right_trigger);
        // Lift functions
        robot.lift.setPower(Move.left_trigger - Move.right_trigger);
        // Claw functions
        if (Move.right_bumper || Action.right_bumper)
            outtake.claw.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Claw.closed;
        if (Move.left_bumper || Action.left_bumper)
            outtake.claw.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Claw.open;
        // Intake functions
        if (intake.extend.target > 0.3)
            intake.setPosition(Intake.Positions.INTAKE);
        else intake.setPosition(Intake.Positions.ENTRANCE);
        // Transfer timing logic
        if (Action.dpad_right && !pendulIsActioned) {
            intake.setPosition(Intake.Positions.TRANSFER);
            outtake.setPosition(Outtake.Positions.BASKET);
            pendulIsActioned = true;
            timer.reset();
        }
        if (pendulIsActioned && timer.time(TimeUnit.MILLISECONDS) >= 500) {
            outtake.setPosition(Outtake.Positions.TRANSFER);
            pendulIsActioned = false;
        }
        // Outtake functions
        if(Action.dpad_up) outtake.setPosition(Outtake.Positions.OUTTAKE);
        else if (Action.dpad_left) outtake.setPosition(Outtake.Positions.BASKET);
    }
}
