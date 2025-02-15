package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.roadrunner.Action;

public interface CancelableAction extends Action {
    void cancel();
}
