package org.firstinspires.ftc.teamcode.systems.utilites.interfaces;

public interface ManualPositionMechanism extends CancelableAction {
    double getTargetPosition();
    void setTargetPosition(double position);
}
