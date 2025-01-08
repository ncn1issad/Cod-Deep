package org.firstinspires.ftc.teamcode.systems.SubSystems.Intake;

import com.qualcomm.robotcore.hardware.CRServo;

public class Motor {
    CRServo Intake;

    public Motor(CRServo Intake){
        this.Intake = Intake;
    }

    public void setPower (double power) {
        Intake.setPower(power);
    }

    /** @noinspection unused*/
    public double getPower () {
        return Intake.getPower();
    }
}
