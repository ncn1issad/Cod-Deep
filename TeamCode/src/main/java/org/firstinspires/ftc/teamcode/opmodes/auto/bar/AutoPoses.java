package org.firstinspires.ftc.teamcode.opmodes.auto.bar;

import com.pedropathing.localization.Pose;
import org.firstinspires.ftc.teamcode.opmodes.auto.utilities.PoseMechanism;

public enum AutoPoses implements PoseMechanism {
    startPose(0, 0, 0, false, false),
    parkPose(16, 0, 0, false, false);
    private final Pose pose;
    private final boolean curve;
    private final boolean control;
    AutoPoses(double x, double y, double heading, boolean curve, boolean control) {
        this.pose = new Pose(x, y, Math.toRadians(heading));
        this.curve = curve;
        this.control = control;
    }

    @Override
    public Pose getPose() {
        return pose;
    }
    @Override
    public boolean isCurve() {
        return curve;
    }
    @Override
    public boolean isControl() {
        return control;
    }
}
