package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.pedropathing.util.Constants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class RobotHardware implements Action {
    RobotHardware() {
        Constants.setConstants(FConstants.class, LConstants.class);
    }

    @Override
    public boolean run(TelemetryPacket p) {
        // TODO: Implement robot hardware control
        return false;
    }
}
