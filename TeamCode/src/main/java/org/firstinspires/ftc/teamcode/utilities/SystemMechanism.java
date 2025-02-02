package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;

public interface SystemMechanism extends Action {
    void update(Gamepad gamepad);
    void setPosition(Enum<?> position);
    Enum<?> getTargetPosition();
}
