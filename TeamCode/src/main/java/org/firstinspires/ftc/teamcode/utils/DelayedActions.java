package org.firstinspires.ftc.teamcode.utils;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for running actions after a delay.
 */
public class DelayedActions {
    private final List<Pair<Long, Runnable>> actions;
    public DelayedActions() {
        actions = new ArrayList<>();
    }
    /**
     * Adds an action to be run after a delay.
     * @param delay the delay in seconds.
     * @param action the action to run.
     */
    public void addDelayed(double delay, Runnable action) {
        actions.add(new Pair<>(System.currentTimeMillis() + (long) (delay * 1000), action));
    }
    /**
     * Runs all actions that have passed their delay.
     */
    public void run() {
        long currentTime = System.currentTimeMillis();
        actions.removeIf(pair -> {
            if (pair.fst < currentTime) {
                pair.snd.run();
                return true;
            }
            return false;
        });
    }
    /**
     * Clears all actions.
     * @see #addDelayed(double, Runnable)
     * @noinspection UnusedDeclaration
     */
    public void clear() {
        actions.clear();
    }
}
