package org.firstinspires.ftc.teamcode.systems.subsystems.outtake;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.DeviceNames;
import org.firstinspires.ftc.teamcode.systems.utilites.ManualMechanismTeleOp;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;
import org.firstinspires.ftc.teamcode.systems.utilites.ServoPositionMechanism;
import org.firstinspires.ftc.teamcode.systems.utilites.interfaces.ManualPositionFactory;

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
