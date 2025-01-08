package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Subsystems.Outtake.Claw;
import org.firstinspires.ftc.teamcode.systems.Subsystems.Outtake.Pendul;
import org.firstinspires.ftc.teamcode.systems.Subsystems.Outtake.Rotation;

public class Outtake {
    final Servo Claw;
    final Servo Rotation;
    final Servo Pendul;

    public final Pendul pendul;
    public final Claw claw;
    public final Rotation rotation;

    public enum Positions {
        TRANSFER,
        BASKET,
        OUTTAKE
    }
    /** @noinspection unused*/
    public Positions position = Positions.TRANSFER;

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

    public void setPosition(@NonNull Positions value) {
        switch (value) {
            case TRANSFER:
                pendul.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Pendul.transfer;
                rotation.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Rotation.transfer;
                break;
            case BASKET:
                pendul.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Pendul.basket;
                rotation.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Rotation.basket;
                break;
            case OUTTAKE:
                pendul.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Pendul.outtake;
                rotation.target = org.firstinspires.ftc.teamcode.systems.Positions.Outtake.Rotation.outtake;
                break;
        }
    }
}
