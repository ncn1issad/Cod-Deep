package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Subsystems.Intake.Extend;
import org.firstinspires.ftc.teamcode.systems.Subsystems.Intake.Pendul;
import org.firstinspires.ftc.teamcode.systems.Subsystems.Intake.Rotation;
import org.firstinspires.ftc.teamcode.systems.Subsystems.Intake.Motor;
import org.firstinspires.ftc.teamcode.utilities.ServoSmoothing;

public class Intake {
    final Servo IntakeRotation;
    final Servo IntakePendul;
    final Servo ExtendMotor;
    final CRServo IntakeMotor;

    public final Rotation rotation;
    public final Motor motor;
    public final Extend extend;
    public final Pendul pendul;

    public final ServoSmoothing smoothing = new ServoSmoothing();

    public enum Positions {
        INTAKE,
        ENTRANCE,
        TRANSFER
    }

    public Positions position = Positions.TRANSFER;

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
        motor.update(dashboard);
    }

    public void runIntake (boolean gamepadButtonIn, boolean gamepadButtonOut){
        if (gamepadButtonIn)
            motor.setPower(0.6);
        else if (gamepadButtonOut)
            motor.setPower(-1.0);
        else
            motor.setPower(0.0);
    }

    public void setPosition(@NonNull Positions value) {
        switch (value) {
            case INTAKE:
                pendul.target = smoothing.Smoothing(pendul.getPosition(), org.firstinspires.ftc.teamcode.systems.Positions.Intake.Pendul.down);
                rotation.target = org.firstinspires.ftc.teamcode.systems.Positions.Intake.Rotation.parallel;
                position = Positions.INTAKE;
                break;
            case ENTRANCE:
                pendul.target = org.firstinspires.ftc.teamcode.systems.Positions.Intake.Pendul.entrance;
                rotation.target = org.firstinspires.ftc.teamcode.systems.Positions.Intake.Rotation.parallel;
                position = Positions.ENTRANCE;
                break;
            case TRANSFER:
                pendul.target = org.firstinspires.ftc.teamcode.systems.Positions.Intake.Pendul.up;
                rotation.target = org.firstinspires.ftc.teamcode.systems.Positions.Intake.Rotation.perpendicular;
                extend.target = org.firstinspires.ftc.teamcode.systems.Positions.Intake.Extend.transfer;
                position = Positions.TRANSFER;
                break;
        }
    }
}
