package org.firstinspires.ftc.utils

/**
 * A mechanism that can be manually adjusted to a target position.
 * Implements a custom [CancelableAction] interface.
 */
interface ManualPositionMechanism : CancelableAction {
    /**
     * The current target position of the mechanism.
     */
    var targetPosition: Double
    /**
     * The current multiplier for the manual adjustment of the position of the mechanism.
     */
    var adjustMultiplier: Double
}