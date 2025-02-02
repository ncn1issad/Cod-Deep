package org.firstinspires.ftc.teamcode.systems.subsystems.outtake;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.utilites.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;
import org.firstinspires.ftc.teamcode.systems.utilites.ServoPositionMechanism;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionFactory;

public class Rotation extends ServoPositionMechanism {
    HardwareMap hardwareMap;
    public Rotation(HardwareMap hardwareMap) {
        super(Positions.outtakeRotationInit);
        this.hardwareMap = hardwareMap;
    }
    @Override
    protected Servo[] getServos() {
        return new Servo[] {
            hardwareMap.get(Servo.class, DeviceNames.OuttakeRotation)
        };
    }
}

class RotationFactory implements ManualPositionFactory {
    @Override
    public ServoPositionMechanism manualPositionFactory(HardwareMap hardwareMap) {
        return new Rotation(hardwareMap);
    }
}

@TeleOp(name = "Outtake Rotation Test", group = "C")
class RotationTest extends ManualMechanismTeleOp {
    public RotationTest() {
        super(new RotationFactory());
    }
}
