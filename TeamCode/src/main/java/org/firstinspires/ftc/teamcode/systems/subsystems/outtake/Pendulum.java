package org.firstinspires.ftc.teamcode.systems.subsystems.outtake;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.utilites.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;
import org.firstinspires.ftc.teamcode.systems.utilites.ServoPositionMechanism;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionFactory;

public class Pendulum extends ServoPositionMechanism {
    HardwareMap hardwareMap;
    public Pendulum(HardwareMap hardwareMap) {
        super(Positions.outtakePendulumInit);
        this.hardwareMap = hardwareMap;
    }
    @Override
    protected Servo[] getServos() {
        return new Servo[] {
            hardwareMap.get(Servo.class, DeviceNames.OuttakePendulumLeft),
            hardwareMap.get(Servo.class, DeviceNames.OuttakePendulumRight)
        };
    }
}

class PendulumFactory implements ManualPositionFactory {
    @Override
    public ServoPositionMechanism manualPositionFactory(HardwareMap hardwareMap) {
        return new Pendulum(hardwareMap);
    }
}

@TeleOp(name = "Outtake Pendulum Test", group = "C")
class PendulumTest extends ManualMechanismTeleOp {
    public PendulumTest() {
        super(new PendulumFactory());
    }
}
