package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.systems.Outtake;

@TeleOp(name = "TeleOp", group = "A")
public class Teleop extends OpMode {
    RobotHardware robot;
    FtcDashboard dashboard;
    private static Gamepad Move;
    private static Gamepad Action;

    @Override
    public void init() {
        robot = new RobotHardware(hardwareMap);

        Move = gamepad1;
        Action = gamepad2;

        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void start() {
        dashboard.clearTelemetry();
        robot.start();
    }

    @Override
    public void loop() {
        TelemetryPacket packet = new TelemetryPacket();
        // Update the position of systems
        robot.run(packet);
        // Movement of the robot
        robot.movement(Move);
        // Intake IntakeMotor functions
        robot.intake.runIntake(Move.cross, Move.square);
        // Claw functions
        if (Action.right_bumper || Move.right_bumper) {
            robot.outtake.claw.close();
        } else if (Action.left_bumper || Move.left_bumper) {
            robot.outtake.claw.open();
        }
        // Outtake functions
        if (Action.dpad_right) {
            robot.outtake.setPosition(Outtake.OPositions.Transfer);
        }
        else if (Action.cross) {
            robot.outtake.setPosition(Outtake.OPositions.Basket);
        }
        else if (Action.dpad_up) {
            robot.outtake.setPosition(Outtake.OPositions.Bar);
        }
        else if (Action.square) {
            robot.outtake.setPosition(Outtake.OPositions.Pickup);
        }
    }
}
