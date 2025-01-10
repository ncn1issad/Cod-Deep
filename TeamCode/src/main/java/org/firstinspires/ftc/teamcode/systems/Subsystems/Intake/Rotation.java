package org.firstinspires.ftc.teamcode.systems.Subsystems.Intake;

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
        target = Positions.intakeRotationInit;
    }

    public double getPosition() {
        return Rotation.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard){
        Rotation.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Intake Rotation current position", getPosition());
        packet.put("Intake Rotation target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
