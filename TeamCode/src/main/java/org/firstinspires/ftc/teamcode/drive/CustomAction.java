package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Action;

public class CustomAction {
    private final Action action;    // The Road Runner Action
    private final Vector2d position; // The target position
    private final double heading;    // The target heading in radians

    public CustomAction(Action action, Vector2d position, double heading) {
        this.action = action;
        this.position = position;
        this.heading = heading;
    }

    public Action getAction() {
        return action;
    }

    public Vector2d getPosition() {
        return position;
    }

    public double getHeading() {
        return heading;
    }

    public Pose2d getPose() {
        return new Pose2d(getPosition(), getHeading());
    }
}
