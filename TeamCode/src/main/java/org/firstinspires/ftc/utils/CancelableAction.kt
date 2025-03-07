package org.firstinspires.ftc.utils

import com.acmerobotics.roadrunner.Action

interface CancelableAction : Action {
    /**
     * Cancels the action.
     * Implementations should stop any ongoing actions and reset any state.
     * Implements RoadRunner's [Action] interface.
     * @see Action.run()
     */
    fun cancel()
}