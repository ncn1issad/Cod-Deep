package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utilities.PIDController;

public class Lift {
    DcMotorEx LiftLeft;
    DcMotorEx LiftRight;

    public PIDController controller;

    public enum LiftPosition{
        DOWN(0),
        HALF(70),
        UP(110);

        private final int value;

        LiftPosition(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }
    }

    LiftPosition target;

    public Lift(DcMotorEx LiftLeft, DcMotorEx LiftRight){
        this.LiftLeft = LiftLeft;
        this.LiftRight = LiftRight;
        target = LiftPosition.DOWN;
        controller = new PIDController(0.01, 0.0, 0.0);
    }

    public void setPower (double power){
        LiftLeft.setPower(power);
        LiftRight.setPower(power);
    }

    public int getCurrentPosition() {
        return LiftRight.getCurrentPosition();
    }

    public void update() {
        double power = controller.update(target.getValue(), getCurrentPosition());
        setPower(power);
    }
}
