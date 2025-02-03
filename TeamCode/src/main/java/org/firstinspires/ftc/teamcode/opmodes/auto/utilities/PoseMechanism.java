package org.firstinspires.ftc.teamcode.opmodes.auto.utilities;

import com.pedropathing.localization.Pose;

public interface PoseMechanism {
    Pose getPose(Enum<?> position);
    boolean isCurve(Enum<?> position);
    boolean isControl(Enum<?> position);
}
