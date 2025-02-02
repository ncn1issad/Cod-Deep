package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.utilites.SingleServo;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;

public class Outtake {
    final Servo Claw;
    final Servo Rotation;
    final Servo Pendulum;

    public SingleServo pendulum;
    public SingleServo claw;
    public SingleServo rotation;

    public enum OuttakePositions {
        TRANSFER,
        BASKET,
        OUTTAKE,
        INIT
    }
    /** @noinspection unused*/
    public OuttakePositions position = OuttakePositions.INIT;

    public Outtake(Servo Claw, Servo Rotation, Servo Pendulum) {
        this.Pendulum = Pendulum;
        this.Claw = Claw;
        this.Rotation = Rotation;

        pendulum = new SingleServo(Pendulum, Positions.outtakePendulInit, "OuttakeTest pendulum");
        claw = new SingleServo(Claw, Positions.outtakeClawInit, "OuttakeTest claw");
        rotation = new SingleServo(Rotation, Positions.outtakeRotationInit, "OuttakeTest rotation");
    }

    public void update(@NonNull FtcDashboard dashboard) {
        pendulum.update(dashboard);
        claw.update(dashboard);
        rotation.update(dashboard);
    }

    public void setPosition(@NonNull OuttakePositions value) {
        switch (value) {
            case TRANSFER:
                pendulum.target = Positions.outtakePendulTransfer;
                rotation.target = Positions.outtakeRotationTransfer;
                position = OuttakePositions.TRANSFER;
                break;
            case BASKET:
                pendulum.target = Positions.outtakeRotationBasket;
                rotation.target = Positions.outtakeRotationBasket;
                position = OuttakePositions.BASKET;
                break;
            case OUTTAKE:
                pendulum.target = Positions.outtakePendulOuttake;
                rotation.target = Positions.outtakeRotationOuttake;
                position = OuttakePositions.OUTTAKE;
                break;
            case INIT:
                pendulum.target = Positions.outtakePendulInit;
                rotation.target = Positions.outtakeRotationInit;
                claw.target = Positions.outtakeClawInit;
                position = OuttakePositions.INIT;
                break;
        }
    }
}
