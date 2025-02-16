package org.firstinspires.ftc.teamcode.utils;

/**
 * Interface representing a manual position mechanism.
 * Extends the CancelableAction interface to include methods for setting and getting target positions,
 * adjusting and getting multipliers.
 */
public interface ManualPositionMechanism extends CancelableAction {
    /**
     * Sets the target position of the mechanism.
     * @param position the target position to set.
     */
    void setTargetPosition(double position);
    /**
     * Gets the current target position of the mechanism.
     * @return the current target position.
     */
    double getTargetPosition();
    /**
     * Adjusts the multiplier used for position adjustments.
     * @param multiplier the multiplier to set.
     */
    void adjustMultiplier(double multiplier);
    /**
     * Gets the current multiplier used for position adjustments.
     * @return the current multiplier.
     */
    double getMultiplier();
}
