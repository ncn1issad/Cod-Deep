package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.CRServo;

public class Extend {
    CRServo ExtendLeft;
    CRServo ExtendRight;

    public Extend(CRServo ExtendLeft, CRServo ExtendRight){
        this.ExtendLeft = ExtendLeft;
        this.ExtendRight = ExtendRight;
    }

    public void setPower (double power) {
        ExtendLeft.setPower(power);
        ExtendRight.setPower(power);
    }

    public double getPower () {
        return (ExtendLeft.getPower() + ExtendRight.getPower()) / 2;
    }
}
