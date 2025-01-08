package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Extend;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Pendul;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Rotation;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Motor;
import org.firstinspires.ftc.teamcode.utilities.ServoSmoothing;

public class Intake {
    Servo IntakeRotation;
    Servo IntakePendul;
    Servo ExtendMotor;
    CRServo IntakeMotor;

    public Rotation rotation;
    public Motor motor;
    public Extend extend;
    public Pendul pendul;

    public ServoSmoothing smoothing = new ServoSmoothing();

    public enum IntakePosition {
        INTAKE,
        ENTRANCE,
        TRANSFER
    }

    public Intake(Servo IntakeRotation, CRServo IntakeMotor, Servo ExtendMotor, Servo IntakePendul){
        this.IntakeRotation = IntakeRotation;
        this.IntakeMotor = IntakeMotor;
        this.ExtendMotor = ExtendMotor;
        this.IntakePendul = IntakePendul;

        rotation = new Rotation(IntakeRotation);
        motor = new Motor(IntakeMotor);
        extend = new Extend(ExtendMotor);
        pendul = new Pendul(IntakePendul);
    }

    public void update(@NonNull FtcDashboard dashboard) {
        rotation.update(dashboard);
        extend.update(dashboard);
        pendul.update(dashboard);
    }

    public void runIntake (boolean gamepadButtonIn, boolean gamepadButtonOut){
        if (gamepadButtonIn)
            motor.setPower(0.6);
        else if (gamepadButtonOut)
            motor.setPower(-1.0);
        else
            motor.setPower(0.0);
    }

    public void setPosition(@NonNull IntakePosition value) {
        switch (value) {
            case INTAKE:
                pendul.target = smoothing.Smoothing(pendul.getPosition(), Positions.Intake.Pendul.down);
                rotation.target = Positions.Intake.Rotation.parallel;
                break;
            case ENTRANCE:
                pendul.target = Positions.Intake.Pendul.entrance;
                rotation.target = Positions.Intake.Rotation.parallel;
                break;
            case TRANSFER:
                pendul.target = Positions.Intake.Pendul.up;
                rotation.target = Positions.Intake.Rotation.perpendicular;
                extend.target = Positions.Intake.extend;
                break;
        }
    }
}
