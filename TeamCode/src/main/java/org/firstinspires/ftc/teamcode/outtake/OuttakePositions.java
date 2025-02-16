package org.firstinspires.ftc.teamcode.outtake;

import static org.firstinspires.ftc.teamcode.Positions.Outtake;

/**
 * Enum representing the different positions of the outtake mechanism.
 * Each position includes values for the pendulum and rotate components.
 */
public enum OuttakePositions {
    /**
     * Position for picking up samples.
     * Includes values for the pendulum and rotate components.
     */
    PICKUP(Outtake.Pendulum.pickup, Outtake.Rotate.pickup),
    /**
     * Position for transferring samples to the outtake.
     * Includes values for the pendulum and rotate components.
     */
    TRANSFER(Outtake.Pendulum.transfer, Outtake.Rotate.transfer),
    /**
     * Position for rotating the outtake to the bar.
     * Includes values for the pendulum and rotate components.
     */
    BAR(Outtake.Pendulum.bar, Outtake.Rotate.bar),
    /**
     * Position for rotating the outtake to the basket.
     * Includes values for the pendulum and rotate components.
     */
    BASKET(Outtake.Pendulum.basket, Outtake.Rotate.basket);

    private final double pendulum;
    private final double rotate;
    /**
     * Constructor for the OuttakePositions enum.
     * @param pendulum the pendulum value.
     * @param rotate the rotate value.
     */
    OuttakePositions(double pendulum, double rotate) {
        this.pendulum = pendulum;
        this.rotate = rotate;
    }
    /**
     * Gets the pendulum value for the position.
     * @return the pendulum value.
     */
    public double getPendulum() {
        return pendulum;
    }
    /**
     * Gets the rotate value for the position.
     * @return the rotate value.
     */
    public double getRotate() {
        return rotate;
    }
}
