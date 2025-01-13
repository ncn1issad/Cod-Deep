package org.firstinspires.ftc.teamcode.opmodes.teleop.tests.outtake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.Positions;
import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;

@TeleOp(name = "OuttakePendulTest", group = "C")
public class PendulTest extends OpMode {
    Servo servo;
    private SingleServo pendul;
    private FtcDashboard dashboard;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, DeviceNames.OuttakePendul);
        pendul = new SingleServo(servo, Positions.outtakePendulInit, "OuttakeTest pendul");
        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) pendul.target += 0.01;
        if (gamepad1.dpad_down) pendul.target -= 0.01;

        pendul.target += gamepad1.left_stick_x * SingleServo.ManualMultiplier;

        if(gamepad1.cross) pendul.target = Positions.outtakePendulTransfer;
        if(gamepad1.square) pendul.target = Positions.outtakePendulBasket;
        if(gamepad1.triangle) pendul.target = Positions.outtakePendulOuttake;

        pendul.update(dashboard);

    }
}
