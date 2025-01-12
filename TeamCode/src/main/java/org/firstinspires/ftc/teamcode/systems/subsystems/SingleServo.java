package org.firstinspires.ftc.teamcode.systems.subsystems;

import static com.qualcomm.robotcore.util.Range.clip;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class SingleServo {
    Servo servo;

    public static double ManualMultiplier = 0.005;

    public double target;
    public String name;

    public SingleServo(Servo servo, double InitPos, String name) {
        this.servo = servo;
        this.name = name;
        target = InitPos;
    }

    public double getPosition() {
        return servo.getPosition();
    }

    public void update(@NonNull FtcDashboard dashboard){
        target = clip(target, 0.005, 0.995);
        servo.setPosition(target);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put(name + "current position", getPosition());
        packet.put(name + " target position", target);
        dashboard.sendTelemetryPacket(packet);
    }
}
