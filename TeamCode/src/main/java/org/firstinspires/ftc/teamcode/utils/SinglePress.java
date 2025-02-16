package org.firstinspires.ftc.teamcode.utils;

import java.util.function.Supplier;

/**
 * A delegate for buttons that should trigger only on a single press.
 * <p>
 * When the button is pressed, the next read will return true, then
 * false until the button is released and pressed again.
 */
public class SinglePress {
    private final Supplier<Boolean> getState;
    private boolean lastState = false;

    /**
     * Creates a new SinglePress delegate.
     * @param getState a supplier for the button state.
     */
    public SinglePress(Supplier<Boolean> getState) {
        this.getState = getState;
    }

    /**
     * Gets the state of the button.
     * @return true if the button is pressed, false otherwise.
     */
    public boolean isPressed() {
        boolean state = getState.get();
        boolean pressed = state && !lastState;
        lastState = state;
        return pressed;
    }
}
