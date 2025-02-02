package org.firstinspires.ftc.teamcode.systems.utilites;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionMechanism;
import org.jetbrains.annotations.NotNull;

public abstract class ServoPositionMechanism implements ManualPositionMechanism {
    private boolean isCanceled = false;
    private double targetPosition;
    protected abstract Servo[] getServos();
    public ServoPositionMechanism(double initialPosition) {
        this.targetPosition = Range.clip(initialPosition, 0.0, 1.0);
    }
    @Override
    public double getTargetPosition() {
        return targetPosition;
    }
    @Override
    public void setTargetPosition(double position) {
        targetPosition = Range.clip(position, 0.0, 1.0);
    }
    @Override
    public void cancel() {
        isCanceled = true;
    }

    @Override
    public boolean run(@NotNull TelemetryPacket packet) {
        if (isCanceled) {
            isCanceled = false;

            return false;
        }
        for (Servo servo : getServos()) {
            servo.setPosition(targetPosition);
        }

        packet.put("Position for " + this.getClass().getSimpleName(), targetPosition);

        return true;
    }
}
