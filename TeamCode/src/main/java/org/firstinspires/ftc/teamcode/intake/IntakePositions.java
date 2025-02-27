package org.firstinspires.ftc.teamcode.intake;

import static org.firstinspires.ftc.teamcode.Positions.Intake;

/**
 * Enum representing the different positions of the intake mechanism.
 * Each position includes values for extend, rotate, spin, and pendulum components.
 */
public enum IntakePositions {
    /**
    * Position for picking up samples.
    * Includes values for extend, rotate, spin, and pendulum components.
    */
    PICKUP(Intake.Extend.in, Intake.Rotate.pickupWait, Intake.Spin.middle, Intake.Pendulum.pickupWait),
    /**
     * Position for transferring samples to outtake.
     * Includes values for extend, rotate, spin, and pendulum components.
     */
    TRANSFER(Intake.Extend.in, Intake.Rotate.transfer, Intake.Spin.middle, Intake.Pendulum.transfer),;

    private final double extend;
    private final double rotate;
    private final double spin;
    private final double pendulum;
    /**
     * Constructor for the IntakePositions enum.
     * @param extend the extend value.
     * @param rotate the rotate value.
     * @param spin the spin value.
     * @param pendulum the pendulum value.
     */
    IntakePositions(double extend, double rotate, double spin, double pendulum) {
        this.extend = extend;
        this.rotate = rotate;
        this.spin = spin;
        this.pendulum = pendulum;
    }
    /**
     * Gets the extend value for the position.
     * @return the extend value.
     */
    public double getExtend() {
        return extend;
    }
    /**
     * Gets the rotate value for the position.
     * @return the rotate value.
     */
    public double getRotate() {
        return rotate;
    }
    /**
     * Gets the spin value for the position.
     * @return the spin value.
     */
    public double getSpin() {
        return spin;
    }
    /**
     * Gets the pendulum value for the position.
     * @return the pendulum value.
     */
    public double getPendulum() {
        return pendulum;
    }
}
