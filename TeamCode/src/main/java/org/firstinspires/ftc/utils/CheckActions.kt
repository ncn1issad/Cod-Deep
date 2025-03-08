package org.firstinspires.ftc.utils

/**
 * A class for running actions after a condition is met.
 */
class CheckActions {
    private val checks: MutableList<Pair<() -> Boolean, () -> Unit>> = mutableListOf()
    /**
     * Adds an action to be run after a delay.
     * @param check the lambda check that determines if the action should be run.
     * @param action the action to run.
     */
    fun addCheck(check: () -> Boolean, action: () -> Unit) {
        checks.add(Pair(check, action))
    }
    /**
     * Runs all actions that have passed their check.
     */
    fun run() {
        checks.forEach {
            if (it.first()) {
                it.second
            }
        }
    }/**
     * Clears all actions.
     * @see addCheck
     * @noinspection UnusedDeclaration
     */
    fun clear() {
        checks.clear()
    }
}