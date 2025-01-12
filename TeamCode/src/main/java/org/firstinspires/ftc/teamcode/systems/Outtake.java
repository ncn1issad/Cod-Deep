package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.subsystems.SingleServo;

public class Outtake {
    final Servo Claw;
    final Servo Rotation;
    final Servo Pendul;

    public SingleServo pendul;
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

    public Outtake(Servo Claw, Servo Rotation, Servo Pendul) {
        this.Pendul = Pendul;
        this.Claw = Claw;
        this.Rotation = Rotation;

        pendul = new SingleServo(Pendul, Positions.outtakePendulInit, "Outtake pendul");
        claw = new SingleServo(Claw, Positions.outtakeClawInit, "Outtake claw");
        rotation = new SingleServo(Rotation, Positions.outtakeRotationInit, "Outtake rotation");
    }

    public void update(@NonNull FtcDashboard dashboard) {
        pendul.update(dashboard);
        claw.update(dashboard);
        rotation.update(dashboard);
    }

    public void setPosition(@NonNull OuttakePositions value) {
        switch (value) {
            case TRANSFER:
                pendul.target = Positions.outtakePendulTransfer;
                rotation.target = Positions.outtakeRotationTransfer;
                position = OuttakePositions.TRANSFER;
                break;
            case BASKET:
                pendul.target = Positions.outtakeRotationBasket;
                rotation.target = Positions.outtakeRotationBasket;
                position = OuttakePositions.BASKET;
                break;
            case OUTTAKE:
                pendul.target = Positions.outtakePendulOuttake;
                rotation.target = Positions.outtakeRotationOuttake;
                position = OuttakePositions.OUTTAKE;
                break;
            case INIT:
                pendul.target = Positions.outtakePendulInit;
                rotation.target = Positions.outtakeRotationInit;
                claw.target = Positions.outtakeClawInit;
                position = OuttakePositions.INIT;
                break;
        }
    }
}
