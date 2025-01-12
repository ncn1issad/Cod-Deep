package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.ServoSmoothing;
import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;
import org.firstinspires.ftc.teamcode.systems.subsystems.IntakeMotor;

public class Intake {
    final Servo IntakeRotation;
    final Servo IntakePendul;
    final Servo ExtendMotor;
    final CRServo IntakeMotor;

    public final SingleServo rotation;
    public final IntakeMotor intakeMotor;
    public final SingleServo extend;
    public final SingleServo pendul;

    public final ServoSmoothing smoothing = new ServoSmoothing();

    public enum IntakePositions {
        INTAKE,
        ENTRANCE,
        TRANSFER,
        INIT
    }

    public IntakePositions position = IntakePositions.INIT;

    public Intake(Servo IntakeRotation, CRServo IntakeMotor, Servo ExtendMotor, Servo IntakePendul){
        this.IntakeRotation = IntakeRotation;
        this.IntakeMotor = IntakeMotor;
        this.ExtendMotor = ExtendMotor;
        this.IntakePendul = IntakePendul;

        rotation = new SingleServo(IntakeRotation, Positions.intakeRotationInit, "Intake rotation");
        intakeMotor = new IntakeMotor(IntakeMotor);
        extend = new SingleServo(ExtendMotor, Positions.intakeExtendInit, "Intake extend");
        pendul = new SingleServo(IntakePendul, Positions.intakePendulInit, "Intake pendul");
    }

    public void update(@NonNull FtcDashboard dashboard) {
        rotation.update(dashboard);
        extend.update(dashboard);
        pendul.update(dashboard);
        intakeMotor.update(dashboard);
    }

    public void runIntake (boolean gamepadButtonIn, boolean gamepadButtonOut){
        if (gamepadButtonIn)
            intakeMotor.setPower(0.6);
        else if (gamepadButtonOut)
            intakeMotor.setPower(-1.0);
        else
            intakeMotor.setPower(0.0);
    }

    public void setPosition(@NonNull IntakePositions value) {
        switch (value) {
            case INTAKE:
                pendul.target = smoothing.Smoothing(pendul.getPosition(), Positions.intakePendulDown);
                rotation.target = Positions.intakeRotationParallel;
                position = IntakePositions.INTAKE;
                break;
            case ENTRANCE:
                pendul.target = Positions.intakePendulEntrance;
                rotation.target = Positions.intakeRotationParallel;
                position = IntakePositions.ENTRANCE;
                break;
            case TRANSFER:
                pendul.target = Positions.intakePendulUp;
                rotation.target = Positions.intakeRotationPerpendicular;
                extend.target = Positions.intakeExtendTransfer;
                position = IntakePositions.TRANSFER;
                break;
            case INIT:
                pendul.target = Positions.intakePendulInit;
                rotation.target = Positions.intakeRotationInit;
                extend.target = Positions.intakeExtendInit;
                position = IntakePositions.INIT;
                break;
        }
    }
}
