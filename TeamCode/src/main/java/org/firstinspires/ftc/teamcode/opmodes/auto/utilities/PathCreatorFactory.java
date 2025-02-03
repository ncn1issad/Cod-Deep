package org.firstinspires.ftc.teamcode.opmodes.auto.utilities;

public class PathCreatorFactory implements PathCreator {
    PoseMechanism[] mechanisms;
    public PathCreatorFactory(PoseMechanism[] mechanisms) {
        this.mechanisms = mechanisms;
    }

    @Override
    public PoseMechanism[] poseMechanismFactory() {
        return mechanisms;
    }
}
