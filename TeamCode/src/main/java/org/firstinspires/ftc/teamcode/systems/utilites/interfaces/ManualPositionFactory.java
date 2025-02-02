package org.firstinspires.ftc.teamcode.systems.utilites.interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface ManualPositionFactory {
    ManualPositionMechanism manualPositionFactory(HardwareMap hardwareMap);
}
