package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Positions.PendulPositions;

public class Pendul {
    Servo PendulLeft;
    Servo PendulRight;

    public static final double PENDUL_MULTIPLIER = 0.001;

    public double target;

    public Pendul(Servo PendulLeft, Servo PendulRight){
        this.PendulLeft = PendulLeft;
        this.PendulRight = PendulRight;
        target = PendulPositions.DOWN;
    }

    public double getPosition(){
        return (PendulLeft.getPosition() + PendulRight.getPosition()) / 2;
    }

    public void update(@NonNull FtcDashboard dashboard){
        PendulLeft.setPosition(target);
        PendulRight.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Pendul current position", getPosition());
        packet.put("Pendul target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
