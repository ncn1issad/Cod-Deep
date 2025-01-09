package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utilities.PIDController;

public class Lift {
    final DcMotorEx Left;
    final DcMotorEx Right;

    public double target;

    PIDController controller;

    @Config
    public static class PIDCoefficients {
        public static double Kp = 0.001;
        public static double Ki = 0.0;
        public static double Kd = 0.0;
    }

    public Lift(DcMotorEx Left, DcMotorEx Right){
        this.Left = Left;
        this.Right = Right;

        target = Positions.Lift.init;

        controller = new PIDController(PIDCoefficients.Kp, PIDCoefficients.Ki, PIDCoefficients.Kd);
    }

    public void setPower (double power){
        Left.setPower(power);
        Right.setPower(power);
    }

    public double getPower() {
        return (Right.getPower() + Left.getPower()) / 2;
    }

    public int getPosition() {
        return Left.getCurrentPosition();
    }

    public void update(@NonNull FtcDashboard dashboard) {
        // setPower(controller.update(target, getPosition()));

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Lift power", getPower());
        dashboard.sendTelemetryPacket(packet);
    }
}
