package org.firstinspires.ftc.teamcode.opmodes.teleop.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;

@TeleOp(name = "IntakeTest", group = "B")
public class IntakeTest extends OpMode {
    Servo Extend;
    Servo Pendul;
    Servo Rotation;
    CRServo Motor;

    FtcDashboard dashboard;

    Intake intake;

    @Override
    public void init() {
        Extend = hardwareMap.get(Servo.class, DeviceNames.IntakeExtend);
        Pendul = hardwareMap.get(Servo.class, DeviceNames.IntakePendul);
        Rotation = hardwareMap.get(Servo.class, DeviceNames.IntakeRotation);
        Motor = hardwareMap.get(CRServo.class, DeviceNames.IntakeMotor);

        for (CRServo servo : new CRServo[] {Motor}) {
            servo.setDirection(CRServo.Direction.REVERSE);
        }

        for (Servo servo : new Servo[] {Extend}) {
            servo.setDirection(Servo.Direction.REVERSE);
        }

        intake = new Intake(Rotation, Motor, Extend, Pendul);

        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {
        intake.extend.target += gamepad1.left_stick_x * SingleServo.ManualMultiplier;
        intake.pendul.target += gamepad1.right_stick_x * SingleServo.ManualMultiplier;
        intake.rotation.target += gamepad2.left_stick_x * SingleServo.ManualMultiplier;

        intake.update(dashboard);
    }
}
