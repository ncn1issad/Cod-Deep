package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.HardwareMap;

@FunctionalInterface
public interface ManualPositionFactory {
    ManualPositionMechanism manualPositionFactory(HardwareMap hardwareMap);
}
