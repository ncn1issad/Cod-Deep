package org.firstinspires.ftc.teamcode.systems.subsystems.intake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.CancelableAction;
import org.jetbrains.annotations.NotNull;

public class Motor implements CancelableAction {
    final CRServo[] Intake;
    private boolean isCancelled = false;
    private double power = 0;

    public Motor(@NotNull HardwareMap hardwareMap) {
        Intake = new CRServo[] {
            hardwareMap.get(CRServo.class, DeviceNames.IntakeMotorLeft),
            hardwareMap.get(CRServo.class, DeviceNames.IntakeMotorRight)
        };
    }
    public double getPower() {
        return power;
    }
    public void setPower(double power) {
        this.power = Range.clip(power, -1.0, 1.0);
    }
    @Override
    public void cancel() {
        isCancelled = true;
        for (CRServo motor : Intake) {
            motor.setPower(0);
        }
    }
    @Override
    public boolean run(@NotNull TelemetryPacket telemetryPacket) {
        if (isCancelled) {
            isCancelled = false;
            return false;
        }
        for (CRServo motor : Intake) {
            motor.setPower(power);
        }
        return true;
    }
}
@TeleOp(name = "Intake Motor Test", group = "C")
class MotorTest extends OpMode {
    private Motor motor;
    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void init() {
        motor = new Motor(hardwareMap);
    }

    @Override
    public void loop() {
        TelemetryPacket packet = new TelemetryPacket();
        motor.setPower(gamepad1.left_stick_y);
        motor.run(packet);
        dashboard.getTelemetry().update();
    }
}
