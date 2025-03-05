package org.firstinspires.ftc.utils

import com.pedropathing.localization.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

/**
 * A store for the robot's last position from autonomous operation.
 */
class PositionStore {
    companion object {
        var pose = Pose(0.0, 0.0, 0.0)
    }
}

/**
 * Autonomous class for resetting the stored position.
 */
@Autonomous(name = "Position Store Reset", group = "B")
private class ResetPositionStore : LinearOpMode() {
    override fun runOpMode() {
        PositionStore.pose = Pose(0.0, 0.0, 0.0)
        this.requestOpModeStop()
    }
}