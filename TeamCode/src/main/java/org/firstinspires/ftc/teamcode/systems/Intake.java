package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    Servo IntakeRotation;
    CRServo IntakeMotor;

    public enum RotationPosition{
        PARALLEL(0.57),
        PERPENDICULAR(0.87),
        REVERSE(0.27);

        private final double value;

        RotationPosition(double v) {
            this.value = v;
        }

        public double getValue() {
            return value;
        }
    }

    public RotationPosition target = RotationPosition.PARALLEL;

    public Intake(Servo IntakeRotation, CRServo IntakeMotor){
        this.IntakeRotation = IntakeRotation;
        this.IntakeMotor = IntakeMotor;
    }

    public void update() {
        IntakeRotation.setPosition(target.getValue());
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
