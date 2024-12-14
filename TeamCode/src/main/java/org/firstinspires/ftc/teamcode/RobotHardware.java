package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Extend;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.Pendul;

public class RobotHardware {
    public OpMode myOpMode;

    public DcMotorEx FrontLeft;
    public DcMotorEx FrontRight;
    public DcMotorEx BackLeft;
    public DcMotorEx BackRight;

    public DcMotorEx LiftLeft;
    public DcMotorEx LiftRight;

    public CRServo ExtendLeft;
    public CRServo ExtendRight;

    public Servo PendulLeft;
    public Servo PendulRight;

    public Servo IntakeRotation;
    public CRServo IntakeMotor;

    public Lift lift;
    public Extend extend;
    public Pendul pendul;
    public Intake intake;

    public RobotHardware(OpMode opmode) {myOpMode = opmode;}

    public void init(){
        FrontLeft = myOpMode.hardwareMap.get(DcMotorEx.class, "FrontLeftMotor");
        FrontRight = myOpMode.hardwareMap.get(DcMotorEx.class, "FrontRightMotor");
        BackLeft = myOpMode.hardwareMap.get(DcMotorEx.class, "BackLeftMotor");
        BackRight = myOpMode.hardwareMap.get(DcMotorEx.class, "BackRightMotor");

        LiftLeft = myOpMode.hardwareMap.get(DcMotorEx.class, "LiftLeftMotor");
        LiftRight = myOpMode. hardwareMap.get(DcMotorEx.class, "LiftRightMotor");

        ExtendLeft = myOpMode.hardwareMap.get(CRServo.class, "ExtendLeftServo");
        ExtendRight = myOpMode. hardwareMap.get(CRServo.class, "ExtendRightServo");

        PendulLeft = myOpMode.hardwareMap.get(Servo.class, "PendulLeftServo");
        PendulRight = myOpMode.hardwareMap.get(Servo.class, "PendulRightServo");

        IntakeRotation = myOpMode.hardwareMap.get(Servo.class, "IntakeServo");
        IntakeMotor =myOpMode.hardwareMap.get(CRServo.class, "IntakeMotor");

        for (DcMotorEx motor : new DcMotorEx[]{FrontLeft, BackLeft}) {
            motor.setDirection(DcMotorEx.Direction.REVERSE);
        }
        for (CRServo servo : new CRServo[]{ExtendLeft}) {
            servo.setDirection(CRServo.Direction.REVERSE);
        }
        for (Servo servo : new Servo[] {PendulLeft}) {
            servo.setDirection(Servo.Direction.REVERSE);
        }

        lift = new Lift(LiftLeft, LiftRight);
        extend = new Extend(ExtendLeft, ExtendRight);
        pendul = new Pendul(PendulLeft, PendulRight);
        intake = new Intake(IntakeRotation, IntakeMotor);
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
