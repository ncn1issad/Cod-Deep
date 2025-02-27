package org.firstinspires.ftc.teamcode.utils;

import java.util.function.Supplier;

/**
 * A delegate for single press buttons.
 * <p>
 * When the button is pressed, the action will run once.
 */
public class PressAction {
    private boolean lastState = false;
    private final Supplier<Boolean> getState;
    private final Runnable action;
    /**
     * Creates a new PressAction delegate.
     * @param getState a supplier for the button state.
     * @param action the action to run when the button is pressed.
     */
    public PressAction(Supplier<Boolean> getState, Runnable action) {
        this.getState = getState;
        this.action = action;
    }
    /**
     * Runs the action if the button is pressed.
     */
    public void run() {
        boolean state = getState.get();
        if (state && !lastState) action.run();
        lastState = state;
    }
}
