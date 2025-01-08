package org.firstinspires.ftc.teamcode.systems.SubSystems.Outtake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

public class Rotation {
    Servo Rotation;

    public double target;

    public Rotation(Servo Claw) {
        this.Rotation = Claw;
        target = 0;
    }

    public double getPosition() {
        return Rotation.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard){
        Rotation.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Pendul current position", getPosition());
        packet.put("Pendul target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
