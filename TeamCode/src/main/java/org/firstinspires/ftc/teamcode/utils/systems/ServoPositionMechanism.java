package org.firstinspires.ftc.teamcode.utils.systems;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class representing a servo position mechanism.
 * Implements the ManualPositionMechanism interface to control servo positions.
 */
public abstract class ServoPositionMechanism implements ManualPositionMechanism {
    /**
     * Gets the servos associated with the mechanism.
     * @return an array of servos.
     */
    protected abstract Servo[] getServos();
    private double multiplier;

    /**
     * Adjusts the multiplier used for position adjustments.
     * @param multiplier the multiplier to set.
     */
    @Override
    public void adjustMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
    /**
     * Gets the current multiplier used for position adjustments.
     * @return the current multiplier.
     */
    @Override
    public double getMultiplier() {
        return multiplier;
    }
    private double targetPosition;

    /**
     * Sets the target position of the mechanism.
     * @param position the target position to set.
     */
    @Override
    public void setTargetPosition(double position) {
        targetPosition = Range.clip(position, 0, 1);
    }
    /**
     * Gets the current target position of the mechanism.
     * @return the current target position.
     */
    @Override
    public double getTargetPosition() {
        return targetPosition;
    }
    private double lasrPosition = Double.NaN;
    private boolean isCancelled = false;

    /**
     * @param initialPosition the initial position to set for the mechanism.
     */
    public ServoPositionMechanism(double initialPosition) {
        setTargetPosition(initialPosition);
        this.multiplier = 0.003;
    }
    /**
     * Cancels the action.
     */
    @Override
    public void cancel() {
        isCancelled = true;
    }
    /**
     * Runs the mechanism and updates the telemetry packet.
     * @param p the telemetry packet to update.
     * @return true if the mechanism is running, false otherwise.
     */
    @Override
    public boolean run(@NotNull TelemetryPacket p) {
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
