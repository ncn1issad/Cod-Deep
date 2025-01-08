package org.firstinspires.ftc.teamcode.systems.SubSystems.Intake;

import com.qualcomm.robotcore.hardware.CRServo;

public class Extend {
    CRServo Extend;

    public Extend(CRServo Extend){
        this.Extend = Extend;
    }

    public void setPower (double power) {
        Extend.setPower(power);
    }

    public double getPower () {
        return Extend.getPower();
    }
}
