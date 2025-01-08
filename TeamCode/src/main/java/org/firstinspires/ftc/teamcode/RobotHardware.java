package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Outtake;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Lift;

public class RobotHardware {
    public OpMode myOpMode;

    public DcMotorEx FrontLeft;
    public DcMotorEx FrontRight;
    public DcMotorEx BackLeft;
    public DcMotorEx BackRight;

    public DcMotorEx LiftLeft;
    public DcMotorEx LiftRight;

    public Servo Pendul;

    public Servo IntakePendul;
    public Servo IntakeRotation;
    public Servo Extend;
    public CRServo IntakeMotor;

    public Servo Claw;
    public Servo ClawRotation;

    public Lift lift;
    public Intake intake;
    public Outtake outtake;

    public RobotHardware(OpMode opmode) {myOpMode = opmode;}

    public void init(){
        FrontLeft = myOpMode.hardwareMap.get(DcMotorEx.class, DeviceNames.FLMotor);
        FrontRight = myOpMode.hardwareMap.get(DcMotorEx.class, DeviceNames.FRMotor);
        BackLeft = myOpMode.hardwareMap.get(DcMotorEx.class, DeviceNames.BLMotor);
        BackRight = myOpMode.hardwareMap.get(DcMotorEx.class, DeviceNames.BRMotor);

        LiftLeft = myOpMode.hardwareMap.get(DcMotorEx.class, DeviceNames.LLMotor);
        LiftRight = myOpMode. hardwareMap.get(DcMotorEx.class, DeviceNames.LRMotor);

        Extend = myOpMode.hardwareMap.get(Servo.class, DeviceNames.ExtendServo);

        Pendul = myOpMode.hardwareMap.get(Servo.class, DeviceNames.PendulServo);

        IntakePendul = myOpMode.hardwareMap.get(Servo.class, DeviceNames.IPServo);
        IntakeRotation = myOpMode.hardwareMap.get(Servo.class, DeviceNames.IRServo);
        IntakeMotor = myOpMode.hardwareMap.get(CRServo.class, DeviceNames.IMCRServo);

        Claw = myOpMode.hardwareMap.get(Servo.class, DeviceNames.ClawServo);
        ClawRotation = myOpMode.hardwareMap.get(Servo.class, DeviceNames.ClawRotationServo);

        for (DcMotorEx motor : new DcMotorEx[]{FrontLeft, BackLeft, LiftLeft, LiftRight}) {
            motor.setDirection(DcMotorEx.Direction.REVERSE);
        }
        for (CRServo servo : new CRServo[] {IntakeMotor}) {
            servo.setDirection(CRServo.Direction.REVERSE);
        }

        for (Servo servo : new Servo[] {Extend}) {
            servo.setDirection(Servo.Direction.REVERSE);
        }

        lift = new Lift(LiftLeft, LiftRight);
        intake = new Intake(IntakeRotation, IntakeMotor, Extend, IntakePendul);
        outtake = new Outtake(Claw, ClawRotation, Pendul);
    }

    public void update(@NonNull FtcDashboard dashboard) {
        outtake.update(dashboard);
        intake.update(dashboard);
        lift.update(dashboard);
        dashboard.updateConfig();
    }

    public void movement(@NonNull Gamepad gamepad) {
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x * 1.1;
        double rx = gamepad.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        FrontLeft.setPower(frontLeftPower);
        BackLeft.setPower(backLeftPower);
        FrontRight.setPower(frontRightPower);
        BackLeft.setPower(backRightPower);
    }
}
