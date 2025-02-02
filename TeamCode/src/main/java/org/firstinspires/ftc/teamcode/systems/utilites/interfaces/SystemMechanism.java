package org.firstinspires.ftc.teamcode.systems.utilites.interfaces;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;

public interface SystemMechanism extends Action {
    void updateTeleOp(Gamepad gamepad);
    void setPosition(Enum<?> position);
    Enum<?> getTargetPosition();
}
