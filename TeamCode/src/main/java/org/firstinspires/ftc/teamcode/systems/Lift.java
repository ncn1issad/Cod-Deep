package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    DcMotorEx Left;
    DcMotorEx Right;

    public Lift(DcMotorEx Left, DcMotorEx Right){
        this.Left = Left;
        this.Right = Right;
    }

    public void setPower (double power){
        Left.setPower(power);
        Right.setPower(power);
    }

    public double getPower() {
        return (Right.getPower() + Left.getPower()) / 2;
    }

    public void update(@NonNull FtcDashboard dashboard) {
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Lift power", getPower());
        dashboard.sendTelemetryPacket(packet);
    }
}
