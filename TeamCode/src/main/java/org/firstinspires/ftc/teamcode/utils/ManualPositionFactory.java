package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Functional interface representing a factory for creating ManualPositionMechanism instances.
 */
@FunctionalInterface
public interface ManualPositionFactory {
    /**
     * Creates a new ManualPositionMechanism instance using the provided HardwareMap.
     * @param hardwareMap the hardware map to use for creating the mechanism.
     * @return a new instance of ManualPositionMechanism.
     */
    ManualPositionMechanism manualPositionFactory(HardwareMap hardwareMap);
}
