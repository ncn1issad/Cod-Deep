package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;

public class ActionRunnerAsync {

    public volatile boolean isBusy = false;

    public void runActionAsync (Action action) {
        new Thread(() -> {
            isBusy = true;
            try {
                Actions.runBlocking(action);
            } finally {
                isBusy = false;
            }
        }).start();
    }
}
