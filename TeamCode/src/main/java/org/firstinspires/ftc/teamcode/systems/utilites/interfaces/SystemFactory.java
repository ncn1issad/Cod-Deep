package org.firstinspires.ftc.teamcode.systems.utilites.interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface SystemFactory {
    SystemMechanism systemFactory(HardwareMap hardwareMap);
}
