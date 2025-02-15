package org.firstinspires.ftc.teamcode.utils;

public interface ManualPositionMechanism extends CancelableAction {
    void setTargetPosition(double position);
    double getTargetPosition();
    void adjustMultiplier(double multiplier);
    double getMultiplier();
}
