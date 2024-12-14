package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    DcMotorEx LiftLeft;
    DcMotorEx LiftRight;

    public Lift(DcMotorEx LiftLeft, DcMotorEx LiftRight){
        this.LiftLeft = LiftLeft;
        this.LiftRight = LiftRight;
    }
}
