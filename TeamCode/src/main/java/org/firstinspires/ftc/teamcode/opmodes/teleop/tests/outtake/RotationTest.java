package org.firstinspires.ftc.teamcode.opmodes.teleop.tests.outtake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;
import org.firstinspires.ftc.teamcode.systems.utilites.SingleServo;

@TeleOp(name = "OuttakeRotationTest", group = "C")
public class RotationTest extends OpMode {
    Servo servo;
    private SingleServo rotation;
    private FtcDashboard dashboard;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, DeviceNames.OuttakeRotation);
        rotation = new SingleServo(servo, Positions.outtakeRotationInit, "OuttakeTest rotation");
        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) rotation.target += 0.01;
        if (gamepad1.dpad_down) rotation.target -= 0.01;

        rotation.target += gamepad1.left_stick_x * SingleServo.ManualMultiplier;

        if (gamepad1.cross) rotation.target = Positions.outtakeRotationTransfer;
        if (gamepad1.square) rotation.target = Positions.outtakeRotationBasket;
        if (gamepad1.triangle) rotation.target = Positions.outtakeRotationOuttake;

        rotation.update(dashboard);

    }
}
