package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public abstract class ServoPositionMechanism implements ManualPositionMechanism {
    protected abstract Servo[] getServos();
    private double multiplier;
    @Override
    public void adjustMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
    @Override
    public double getMultiplier() {
        return multiplier;
    }
    private double targetPosition;
    @Override
    public void setTargetPosition(double position) {
        targetPosition = Range.clip(position, 0, 1);
    }
    @Override
    public double getTargetPosition() {
        return targetPosition;
    }
    private double lasrPosition = Double.NaN;
    private boolean isCancelled = false;
    public ServoPositionMechanism(double initialPosition) {
        setTargetPosition(initialPosition);
        this.multiplier = 0.003;
    }
    @Override
    public void cancel() {
        isCancelled = true;
    }
    @Override
    public boolean run(TelemetryPacket p) {
        if (isCancelled) {
            isCancelled = false;
            return false;
        }
        if (lasrPosition != targetPosition) {
            for (Servo servo : getServos()) {
                servo.setPosition(targetPosition);
            }
            lasrPosition = targetPosition;
        }
        p.put("Position of " + getClass().getSimpleName(), targetPosition);
        return true;
    }
}
