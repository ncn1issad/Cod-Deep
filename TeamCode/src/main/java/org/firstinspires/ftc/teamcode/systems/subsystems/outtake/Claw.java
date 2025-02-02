package org.firstinspires.ftc.teamcode.systems.subsystems.outtake;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.utilites.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;
import org.firstinspires.ftc.teamcode.systems.utilites.ServoPositionMechanism;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionFactory;
import org.jetbrains.annotations.NotNull;

public class Claw extends ServoPositionMechanism {
    HardwareMap hardwareMap;
    public Claw(HardwareMap hardwareMap) {
        super(Positions.outtakeClawInit);
        this.hardwareMap = hardwareMap;

        if (getServos().length > 0) {
            getServos()[0].setDirection(Servo.Direction.REVERSE);
        }
    }

    @Override
    protected Servo[] getServos() {
        return new Servo[] {
            hardwareMap.get(Servo.class, DeviceNames.ClawServo)
        };
    }

    void setClawPosition(@NotNull CPositions position) {
        setTargetPosition(position.getClaw());
    }

    public void open() {
        setClawPosition(CPositions.Open);
    }

    public void close() {
        setClawPosition(CPositions.Close);
    }

    public enum CPositions {
        Open(Positions.outtakeClawOpen),
        Close(Positions.outtakeClawClosed);
        private final double claw;

        CPositions(double claw) {
            this.claw = claw;
        }
        public double getClaw() {
            return claw;
        }
    }
}

class ClawFactory implements ManualPositionFactory {
    @Override
    public ServoPositionMechanism manualPositionFactory(HardwareMap hardwareMap) {
        return new Claw(hardwareMap);
    }
}

@TeleOp(name = "Outtake Claw Test", group = "C")
class ClawTest extends ManualMechanismTeleOp {
    public ClawTest() {
        super(new ClawFactory());
    }
}
