package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Outtake;
import org.firstinspires.ftc.teamcode.systems.Positions;

@TeleOp(name = "TeleOp", group = "A")
public class Teleop extends OpMode {
    RobotHardware robot = new RobotHardware(this);

    Intake intake = robot.intake;
    Outtake outtake = robot.outtake;

    FtcDashboard dashboard;

    private final ElapsedTime timer = new ElapsedTime();

    private static Gamepad Move;
    private static Gamepad Action;

    private boolean inTransfer() {
        return robot.intake.pendul.getPosition() == Positions.Intake.Pendul.up;
    }

    @Override
    public void init() {
        robot.init();

        Move = gamepad1;
        Action = gamepad2;

        dashboard = FtcDashboard.getInstance();
    }

    private boolean pendulIsActioned = false;
    private boolean intakeIsUp = false;

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
        if (Move.right_bumper || Action.right_bumper) outtake.claw.target = Positions.Outtake.Claw.closed;
        else if (Move.left_bumper || Action.left_bumper) outtake.claw.target = Positions.Outtake.Claw.open;

    }
}
