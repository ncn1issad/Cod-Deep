package org.firstinspires.ftc.teamcode.opmodes.teleop.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.Outtake;
import org.firstinspires.ftc.teamcode.systems.Positions;
import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;

@TeleOp(name = "OuttakeTest", group = "B")
public class OuttakeTest extends OpMode {
    Servo Claw;
    Servo Pendul;
    Servo Rotation;

    FtcDashboard dashboard;

    Outtake outtake;

    @Override
    public void init() {
        Claw = hardwareMap.get(Servo.class, DeviceNames.ClawServo);
        Pendul = hardwareMap.get(Servo.class, DeviceNames.OuttakePendul);
        Rotation = hardwareMap.get(Servo.class, DeviceNames.IntakeRotation);

        outtake = new Outtake(Claw, Rotation, Pendul);

        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {
        outtake.claw.target += gamepad2.left_stick_x * SingleServo.ManualMultiplier;
        outtake.pendul.target += gamepad1.right_stick_x * SingleServo.ManualMultiplier;
        outtake.rotation.target += gamepad1.left_stick_x * SingleServo.ManualMultiplier;

        outtake.update(dashboard);
    }
}
