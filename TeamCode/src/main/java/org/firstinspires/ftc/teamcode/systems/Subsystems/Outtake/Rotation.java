package org.firstinspires.ftc.teamcode.systems.Subsystems.Outtake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Positions;

public class Rotation {
    final Servo Rotation;

    public double target;

    public Rotation(Servo Claw) {
        this.Rotation = Claw;
        target = Positions.outtakeRotationInit;
    }

    public double getPosition() {
        return Rotation.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard){
        Rotation.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Outtake Rotation current position", getPosition());
        packet.put("Outtake Rotation target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
