package org.firstinspires.ftc.teamcode.opmodes.teleop.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.Subsystems.Outtake.Pendul;

@TeleOp(name = "SingleServoControl", group = "Custom")
public class SingleServoTest extends OpMode {
    private Pendul singleServo;
    private FtcDashboard dashboard;
    private Gamepad control;

    @Override
    public void init() {
        Servo servo = hardwareMap.get(Servo.class, DeviceNames.PendulServo);
        singleServo = new Pendul(servo);
        dashboard = FtcDashboard.getInstance();
        control = gamepad1; // Use gamepad1 for input
    }

    @Override
    public void loop() {
        if (control.dpad_up) singleServo.target = (singleServo.getPosition() + 0.01);
        if (control.dpad_down) singleServo.target = (singleServo.getPosition() - 0.01);

        singleServo.target += control.left_stick_x;

        singleServo.update(dashboard);
    }
}
