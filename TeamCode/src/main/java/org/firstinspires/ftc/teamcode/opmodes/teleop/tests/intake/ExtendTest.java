package org.firstinspires.ftc.teamcode.opmodes.teleop.tests.intake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.Positions;
import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;

@TeleOp(name = "IntakeExtendTest", group = "C")
public class ExtendTest extends OpMode {
    Servo servo;
    private SingleServo extend;
    private FtcDashboard dashboard;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, DeviceNames.IntakeExtend);
        extend = new SingleServo(servo, Positions.intakeExtendInit, "Intake extend");
        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) extend.target += 0.01;
        if (gamepad1.dpad_down) extend.target -= 0.01;

        extend.target += gamepad1.left_stick_x * SingleServo.ManualMultiplier;

        extend.update(dashboard);
    }
}
