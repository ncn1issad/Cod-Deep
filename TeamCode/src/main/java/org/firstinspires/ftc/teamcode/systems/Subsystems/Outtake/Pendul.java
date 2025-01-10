package org.firstinspires.ftc.teamcode.systems.Subsystems.Outtake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Positions;

public class Pendul {
    final Servo Pendul;

    public double target;

    public Pendul(Servo Pendul){
        this.Pendul = Pendul;
        target = Positions.outtakePendulInit;
    }

    public double getPosition(){
        return Pendul.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard){
        Pendul.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Outtake Pendul current position", getPosition());
        packet.put("Outtake Pendul target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
