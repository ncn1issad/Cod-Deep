package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Positions.IntakePositions;

public class Intake {
    Servo IntakeRotation;
    CRServo IntakeMotor;

    public double target;

    public Intake(Servo IntakeRotation, CRServo IntakeMotor){
        this.IntakeRotation = IntakeRotation;
        this.IntakeMotor = IntakeMotor;
        target = IntakePositions.PARALLEL;
    }

    public void update() {
        IntakeRotation.setPosition(target);
    }

    public void runIntake (boolean gamepadButtonIn, boolean gamepadButtonOut){
        if (gamepadButtonIn)
            IntakeMotor.setPower(0.6);
        else if (gamepadButtonOut)
            IntakeMotor.setPower(-1.0);
        else
            IntakeMotor.setPower(0.0);
    }
}
