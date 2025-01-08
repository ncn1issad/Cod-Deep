package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Extend;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Rotation;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Motor;

public class Intake {
    Servo IntakeRotation;
    CRServo IntakeMotor;
    CRServo ExtendMotor;

    public Rotation rotation;
    public Motor motor;
    public Extend extend;

    public Intake(Servo IntakeRotation, CRServo IntakeMotor, CRServo ExtendMotor){
        this.IntakeRotation = IntakeRotation;
        this.IntakeMotor = IntakeMotor;
        this.ExtendMotor = ExtendMotor;

        rotation = new Rotation(IntakeRotation);
        motor = new Motor(IntakeMotor);
        extend = new Extend(ExtendMotor);
    }

    public void update(@NonNull FtcDashboard dashboard) {
        rotation.update(dashboard);
    }

    public void runIntake (boolean gamepadButtonIn, boolean gamepadButtonOut){
        if (gamepadButtonIn)
            motor.setPower(0.6);
        else if (gamepadButtonOut)
            motor.setPower(-1.0);
        else
            motor.setPower(0.0);
    }
}
