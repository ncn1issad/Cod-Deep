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
        // Actions of the robot
        robot.actions(Action, Move);
    }
}
