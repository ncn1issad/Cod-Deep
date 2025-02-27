package org.firstinspires.ftc.teamcode.outtake;

import static org.firstinspires.ftc.teamcode.Positions.Outtake;

/**
 * Enum representing the different positions of the outtake mechanism.
 * Each position includes values for the lift, pendulum and rotate components.
 */
public enum OuttakePositions {
    /**
     * Position for picking up samples.
     * Includes values for the lift, pendulum and rotate components.
     */
    PICKUP(Outtake.Pendulum.pickup, Outtake.Rotate.pickup, Outtake.Lift.down),
    /**
     * Position for transferring samples to the outtake.
     * Includes values for the lift, pendulum and rotate components.
     */
    TRANSFER(Outtake.Pendulum.transfer, Outtake.Rotate.transfer, Outtake.Lift.transfer),
    /**
     * Position for rotating the outtake to the bar.
     * Includes values for the lift, pendulum and rotate components.
     */
    BAR(Outtake.Pendulum.bar, Outtake.Rotate.bar, Outtake.Lift.half),
    /**
     * Position for rotating the outtake to the basket.
     * Includes values for the lift, pendulum and rotate components.
     */
    BASKET(Outtake.Pendulum.basket, Outtake.Rotate.basket, Outtake.Lift.up),
    /**
     * Position for hanging the robot.
     * Includes values for the lift, pendulum and rotate components.
     */
    HANG(Outtake.Pendulum.transfer, Outtake.Rotate.transfer, Outtake.Lift.up);

    private final double pendulum;
    private final double rotate;
    private final double lift;
    /**
     * Constructor for the OuttakePositions enum.
     * @param pendulum the pendulum value.
     * @param rotate the rotate value.
     * @param lift the lift value.
     */
    OuttakePositions(double pendulum, double rotate, double lift) {
        this.pendulum = pendulum;
        this.rotate = rotate;
        this.lift = lift;
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
    /**
     * Gets the lift value for the position.
     * @return the lift value.
     */
    public double getLift() {
        return lift;
    }
}
