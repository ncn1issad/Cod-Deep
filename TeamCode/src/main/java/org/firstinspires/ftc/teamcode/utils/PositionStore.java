package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A store for the robot's last position from autonomous operation.
 */
public class PositionStore {
    public static Pose pose = new Pose(0.0, 0.0, 0.0);
}

/**
 * Autonomous class for resetting the stored position.
 */
@Autonomous(name = "Position Store Reset", group = "B")
class ResetStoredPosition extends LinearOpMode {
    @Override
    public void runOpMode() {
        PositionStore.pose = new Pose(0.0, 0.0, 0.0);
        this.requestOpModeStop();
    }
}
