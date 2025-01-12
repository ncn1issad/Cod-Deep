package org.firstinspires.ftc.teamcode.systems.Subsystems.Intake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Positions;

@Config
public class Extend {
    final Servo Extend;

    public static double ManualMultiplier = 0.005;

    public double target;

    public Extend(Servo Extend){
        this.Extend = Extend;
        target = Positions.intakeExtendInit;
    }

    public double getPosition() {
        return Extend.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard) {
        Extend.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Extend current position", getPosition());
        packet.put("Extend target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
