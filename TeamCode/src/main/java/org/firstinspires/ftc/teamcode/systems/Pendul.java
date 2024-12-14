package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;

public class Pendul {
    Servo PendulLeft;
    Servo PendulRight;

    public static final double PENDUL_MULTIPLIER = 0.001;

    public enum PendulPosition{
        DOWN(0.275),
        BASKET(0.7),
        BAR(0.85),
        SLAM(0.6);

        private final double value;

        PendulPosition(double v) {
            this.value = v;
        }

        public double getValue() {
            return value;
        }
    }

    public double target;

    public Pendul(Servo PendulLeft, Servo PendulRight){
        this.PendulLeft = PendulLeft;
        this.PendulRight = PendulRight;
        target = PendulPosition.DOWN.getValue();
    }

    public double getPosition(){
        return (PendulLeft.getPosition() + PendulRight.getPosition()) / 2;
    }

    public void update(){
        PendulLeft.setPosition(target);
        PendulRight.setPosition(target);
    }
}
