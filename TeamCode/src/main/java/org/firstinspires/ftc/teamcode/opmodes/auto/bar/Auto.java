package org.firstinspires.ftc.teamcode.opmodes.auto.bar;

import org.firstinspires.ftc.teamcode.opmodes.auto.utilities.AutoMechanism;
import org.firstinspires.ftc.teamcode.systems.Outtake;

public class Auto extends AutoMechanism {
    public Auto() {
        super(AutoPoses.values());
    }

    @Override
    protected void update() {
         switch (pathState) {
             case 0:
                 robot.follower.followPath(pathChains.get(AutoPoses.startPose));
                 robot.outtake.setPosition(Outtake.State.Pickup);
                 setPathState(1);
                 break;
             case 1:
                 if (!robot.follower.isBusy()) {
                     setPathState(-1);
                 }
                 break;
         }
    }
}
