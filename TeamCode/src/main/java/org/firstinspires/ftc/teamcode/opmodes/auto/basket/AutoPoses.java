package org.firstinspires.ftc.teamcode.opmodes.auto.basket;

import com.pedropathing.localization.Pose;
import org.firstinspires.ftc.teamcode.opmodes.auto.utilities.PoseMechanism;

public enum AutoPoses implements PoseMechanism {
    startPose(0, 0, 0, false, false);
    private final Pose pose;
    private final boolean curve;
    private final boolean control;
    AutoPoses(double x, double y, double heading, boolean curve, boolean control) {
        this.pose = new Pose(x, y, Math.toRadians(heading));
        this.curve = curve;
        this.control = control;
    }

    @Override
    public Pose getPose(Enum<?> position) {
        if (position instanceof AutoPoses) {
            return ((AutoPoses) position).pose;
        }
        return null;
    }
    @Override
    public boolean isCurve(Enum<?> position) {
        return position instanceof AutoPoses && ((AutoPoses) position).curve;
    }
    @Override
    public boolean isControl(Enum<?> position) {
        return position instanceof AutoPoses && ((AutoPoses) position).control;
    }
}
