package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.roadrunner.Action;
/**
 * Extends the Action interface to include a cancel method.
 */
public interface CancelableAction extends Action {
    /**
     * Cancels the action.
     */
    void cancel();
}
