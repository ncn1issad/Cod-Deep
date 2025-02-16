package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.utils.CancelableAction;
import org.jetbrains.annotations.NotNull;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class RobotHardware implements CancelableAction {
    public Intake intake;
    RobotHardware(HardwareMap hardwareMap) {
        Constants.setConstants(FConstants.class, LConstants.class);
        intake = new Intake(hardwareMap);
    }
    @Override
    public void cancel() {
        intake.cancel();
    }

    @Override
    public boolean run(@NotNull TelemetryPacket p) {
        return intake.run(p);
    }
}
