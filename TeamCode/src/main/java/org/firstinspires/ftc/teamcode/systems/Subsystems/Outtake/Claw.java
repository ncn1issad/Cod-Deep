package org.firstinspires.ftc.teamcode.systems.Subsystems.Outtake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Positions;

public class Claw {
    final Servo Claw;

    public double target;

    public Claw(Servo Claw) {
        this.Claw = Claw;
        target = Positions.outtakeClawInit;
    }

    public double getPosition() {
        return Claw.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard){
        Claw.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Claw current position", getPosition());
        packet.put("Claw target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
