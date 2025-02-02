package org.firstinspires.ftc.teamcode.systems;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.systems.subsystems.intake.Motor;
import org.jetbrains.annotations.NotNull;

public class Intake implements Action {
    public final Motor motor;

    public Intake(HardwareMap hardwareMap) {
        motor = new Motor(hardwareMap);
    }

    public boolean run(@NotNull TelemetryPacket packet) {
        return motor.run(packet);
    }

    public void runIntake (boolean gamepadButtonIn, boolean gamepadButtonOut){
        if (gamepadButtonIn)
            motor.setPower(0.6);
        else if (gamepadButtonOut)
            motor.setPower(-1.0);
        else
            motor.setPower(0.0);
    }
}
