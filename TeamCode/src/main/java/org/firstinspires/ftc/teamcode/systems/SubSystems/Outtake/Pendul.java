package org.firstinspires.ftc.teamcode.systems.SubSystems.Outtake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

public class Pendul {
    Servo Pendul;

    public double target;

    public Pendul(Servo Pendul){
        this.Pendul = Pendul;
        target = 0;
    }

    public double getPosition(){
        return Pendul.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard){
        Pendul.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Pendul current position", getPosition());
        packet.put("Pendul target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
