package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    Servo IntakeRotation;
    CRServo IntakeMotor;

    public Intake(Servo IntakeRotation, CRServo IntakeMotor){
        this.IntakeRotation = IntakeRotation;
        this.IntakeMotor = IntakeMotor;
    }
}
