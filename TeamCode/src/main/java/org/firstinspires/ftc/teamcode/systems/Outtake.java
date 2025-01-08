package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.SubSystems.Outtake.Claw;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Outtake.Pendul;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Outtake.Rotation;

public class Outtake {
    Servo Claw;
    Servo Rotation;
    Servo Pendul;

    public Pendul pendul;
    public Claw claw;
    public Rotation rotation;

    public enum OuttakePositions {
        TRANSFER,
        BASKET,
        OUTTAKE
    }

    public Outtake(Servo Claw, Servo Rotation, Servo Pendul) {
        this.Pendul = Pendul;
        this.Claw = Claw;
        this.Rotation = Rotation;

        pendul = new Pendul(Pendul);
        claw = new Claw(Claw);
        rotation = new Rotation(Rotation);
    }

    public void update(@NonNull FtcDashboard dashboard) {
        pendul.update(dashboard);
        claw.update(dashboard);
        rotation.update(dashboard);
    }

    public void setPosition(@NonNull OuttakePositions value) {
        switch (value) {
            case TRANSFER:
                pendul.target = Positions.Outtake.Pendul.transfer;
                rotation.target = Positions.Outtake.Rotation.transfer;
                break;
            case BASKET:
                pendul.target = Positions.Outtake.Pendul.basket;
                rotation.target = Positions.Outtake.Rotation.basket;
                break;
            case OUTTAKE:
                pendul.target = Positions.Outtake.Pendul.outtake;
                rotation.target = Positions.Outtake.Rotation.outtake;
                break;
        }
    }
}
