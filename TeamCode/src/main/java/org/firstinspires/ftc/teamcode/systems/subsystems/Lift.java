package org.firstinspires.ftc.teamcode.systems.subsystems;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.utilites.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.utilities.Positions;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionFactory;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionMechanism;
import org.jetbrains.annotations.NotNull;

public class Lift implements ManualPositionMechanism {
    DcMotorEx[] motors;
    private int targetPosition = Positions.liftInit;
    private boolean isCancelled = false;

    public Lift(@NotNull HardwareMap hardwareMap) {
        motors = new DcMotorEx[] {
            hardwareMap.get(DcMotorEx.class, DeviceNames.LLMotor),
            hardwareMap.get(DcMotorEx.class, DeviceNames.LRMotor),
        };
        motors[0].setDirection(DcMotorEx.Direction.REVERSE);
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(Positions.liftInit);
            motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
        motors[0].setDirection(DcMotorEx.Direction.REVERSE);
    }

    @Override
    public double getTargetPosition() {
        return targetPosition;
    }

    @Override
    public void setTargetPosition(double position) {
        targetPosition = Range.clip((int) position, 0, 2185);
    }

    @Override
    public void cancel() {
        isCancelled = true;
        for (DcMotorEx motor : motors) {
            motor.setPower(0);
        }
    }

    @Override
    public boolean run(@NotNull TelemetryPacket telemetryPacket) {
        if (isCancelled) {
            isCancelled = false;
            return false;
        }
        for (DcMotorEx motor : motors) {
            motor.setTargetPosition(targetPosition);
        }
        telemetryPacket.put("Lift Target Position", targetPosition);
        return true;
    }
}

class LiftFactory implements ManualPositionFactory {
    @Override
    public ManualPositionMechanism manualPositionFactory(HardwareMap hardwareMap) {
        return new Lift(hardwareMap);
    }
}

@TeleOp(name = "Lift Test", group = "C")
class LiftTest extends ManualMechanismTeleOp {
    public LiftTest() {
        super(new LiftFactory());
    }
}
