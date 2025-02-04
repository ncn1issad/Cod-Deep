package org.firstinspires.ftc.teamcode.opmodes.auto.utilities;

import com.pedropathing.localization.Pose;

public interface PoseMechanism {
    Pose getPose();
    boolean isCurve();
    boolean isControl();
}
