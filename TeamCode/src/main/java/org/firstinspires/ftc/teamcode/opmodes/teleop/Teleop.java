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
import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "TeleOp", group = "A")
public class Teleop extends OpMode {
    final RobotHardware robot = new RobotHardware(this);

    public Intake intake;
    public Outtake outtake;

    FtcDashboard dashboard;

    private final ElapsedTime pendulTimer = new ElapsedTime();
//    private final ElapsedTime liftSmashTimer = new ElapsedTime();

    private static Gamepad Move;
    private static Gamepad Action;

    @Override
    public void init() {
        robot.init();

        intake = robot.intake;
        outtake = robot.outtake;

        Move = gamepad1;
        Action = gamepad2;

        dashboard = FtcDashboard.getInstance();

        robot.update(dashboard);
    }

    private boolean pendulIsActioned = false;
//    private boolean liftIsActioned = false;
//    private boolean liftWasSmashed = false;

    @Override
    public void loop() {
        // Update the position of systems
        robot.update(dashboard);
        // Movement of the robot
        robot.movement(Move);
        // Intake IntakeMotor functions
        robot.intake.runIntake(Move.cross, Move.square);
        // Extend functions
        robot.intake.extend.target += (Action.left_trigger - Action.right_trigger) * SingleServo.ManualMultiplier;
        // Lift functions
        robot.lift.setPower(Move.left_trigger - Move.right_trigger);
        // Claw functions
        if (Move.right_bumper || Action.right_bumper)
            outtake.claw.target = Positions.outtakeClawClosed;
        if (Move.left_bumper || Action.left_bumper)
            outtake.claw.target = Positions.outtakeClawOpen;
        // Intake functions
        if(Action.dpad_down) intake.setPosition(Intake.IntakePositions.ENTRANCE);
        if(intake.position != Intake.IntakePositions.TRANSFER) {
            if (intake.extend.target > 0.35)
                intake.setPosition(Intake.IntakePositions.INTAKE);
            else intake.setPosition(Intake.IntakePositions.ENTRANCE);
        }
        // Transfer timing logic
        if (Action.dpad_right && !pendulIsActioned /*&& !liftIsActioned*/) {
            intake.setPosition(Intake.IntakePositions.TRANSFER);
            outtake.setPosition(Outtake.OuttakePositions.BASKET);
            //robot.lift.target = IntakePositions.Lift.clear;
            pendulIsActioned = true;
            pendulTimer.reset();
        }
        if (pendulIsActioned && pendulTimer.time(TimeUnit.MILLISECONDS) >= 650) {
            outtake.setPosition(Outtake.OuttakePositions.TRANSFER);
            //robot.lift.target = IntakePositions.Lift.down;
            pendulIsActioned = false;
        }
        // Outtake functions
        if(Action.dpad_up) outtake.setPosition(Outtake.OuttakePositions.OUTTAKE);
        else if (Action.dpad_left) outtake.setPosition(Outtake.OuttakePositions.BASKET);
//        // Lift functions
//        if (Action.dpad_left) robot.lift.target = IntakePositions.Lift.basket;
//        else if (Action.dpad_up) {
//            robot.lift.target = IntakePositions.Lift.up;
//            liftIsActioned = true;
//        }
//        // Smash logic
//        else if (Action.dpad_right && liftIsActioned) {
//            robot.lift.target = IntakePositions.Lift.smash;
//            liftSmashTimer.reset();
//        }
//        if (liftIsActioned && liftSmashTimer.time(TimeUnit.MILLISECONDS) >= 350) {
//            outtake.claw.target = IntakePositions.Outtake.Claw.open;
//            liftIsActioned = false;
//            liftWasSmashed = false;
//        }
//        if(liftWasSmashed && liftSmashTimer.time(TimeUnit.MILLISECONDS) >= 500) {
//            intake.setPosition(Intake.IntakePositions.TRANSFER);
//            outtake.setPosition(Outtake.IntakePositions.BASKET);
//            robot.lift.target = IntakePositions.Lift.clear;
//            pendulIsActioned = true;
//            pendulTimer.reset();
//        }
    }
}
