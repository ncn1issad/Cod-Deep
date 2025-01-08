package org.firstinspires.ftc.teamcode.systems.Subsystems.Intake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.CRServo;

public class Motor {
    final CRServo Intake;

    public Motor(CRServo Intake){
        this.Intake = Intake;
    }

    public void setPower (double power) {
        Intake.setPower(power);
    }

    public double getPower () {
        return Intake.getPower();
    }

    public void update(@NonNull FtcDashboard dashboard) {
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Intake power", getPower());
        dashboard.sendTelemetryPacket(packet);
    }
}
