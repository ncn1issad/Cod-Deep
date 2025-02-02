package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface SystemFactory {
    SystemMechanism systemFactory(HardwareMap hardwareMap);
}
