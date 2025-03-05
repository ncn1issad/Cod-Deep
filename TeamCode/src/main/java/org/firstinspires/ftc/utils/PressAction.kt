package org.firstinspires.ftc.utils

class PressAction(
    /**
     * The button to check.
     */
    private val button: () -> Boolean,
    /**
     * The action to run when the button is pressed.
     */
    private val action: () -> Unit
) {
    // The last state of the button
    private var lastState = false
    /**
     * Runs the action if the button is pressed.
     */
    fun run() {
        val state = button()
        if (state && !lastState) {
            action()
        }
        lastState = state
    }
}