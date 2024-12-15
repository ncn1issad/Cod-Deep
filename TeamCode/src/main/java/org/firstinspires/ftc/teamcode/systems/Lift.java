package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.systems.PIDCoefficients.LiftPID;
import org.firstinspires.ftc.teamcode.systems.Positions.LiftPositions;
import org.firstinspires.ftc.teamcode.utilities.PIDController;

public class Lift {
    DcMotorEx LiftLeft;
    DcMotorEx LiftRight;

    private final PIDController controller;
    private LiftPID PIDCoefficients;

    int target;

    public Lift(DcMotorEx LiftLeft, DcMotorEx LiftRight){
        this.LiftLeft = LiftLeft;
        this.LiftRight = LiftRight;
        target = LiftPositions.DOWN;
        this.controller = new PIDController(PIDCoefficients.Kp, PIDCoefficients.Ki, PIDCoefficients.Kd);
    }

    public void setPower (double power){
        LiftLeft.setPower(power);
        LiftRight.setPower(power);
    }

    public int getCurrentPosition() {
        return LiftRight.getCurrentPosition();
    }

    public void update() {
        double power = controller.update(target, getCurrentPosition());
        setPower(power);
    }
}
