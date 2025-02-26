package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.utils.CancelableAction;
import org.jetbrains.annotations.NotNull;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

/**
 * Class representing the robot hardware.
 * Contains the intake and outtake mechanisms.
 * Implements the CancelableAction interface to control the robot hardware.
 */
public class RobotHardware implements CancelableAction {
    public Intake intake;
    public Outtake outtake;
    /**
     * Constructor for the RobotHardware class.
     * Initializes the intake and outtake mechanisms using the provided hardware map.
     * @param hardwareMap the hardware map to use for initialization.
     */
    public RobotHardware(HardwareMap hardwareMap) {
        Constants.setConstants(FConstants.class, LConstants.class);
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);
    }
    /**
     * Cancels all robot hardware actions.
     */
    @Override
    public void cancel() {
        intake.cancel();
        outtake.cancel();
    }
    /**
     * Runs the robot hardware mechanisms and updates the telemetry packet.
     * @param p the telemetry packet to update.
     * @return true if all components are running, false otherwise.
     */
    @Override
    public boolean run(@NotNull TelemetryPacket p) {
        return intake.run(p) && outtake.run(p);
    }
}
