package org.firstinspires.ftc.teamcode.opmodes.teleop.tests.outtake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.Positions;
import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;

@TeleOp(name = "OuttakeClawTest", group = "C")
public class ClawTest extends OpMode {
    Servo servo;
    private SingleServo claw;
    private FtcDashboard dashboard;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, DeviceNames.ClawServo);
        claw = new SingleServo(servo, Positions.outtakeClawInit, "OuttakeTest claw");
        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) claw.target += 0.01;
        if (gamepad1.dpad_down) claw.target -= 0.01;

        claw.target += gamepad1.left_stick_x * SingleServo.ManualMultiplier;

        claw.update(dashboard);

    }
}
