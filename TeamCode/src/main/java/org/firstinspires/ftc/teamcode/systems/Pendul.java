package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.systems.Positions.PendulPositions;

public class Pendul {
    Servo PendulLeft;
    Servo PendulRight;

    public static final double PENDUL_MULTIPLIER = 0.001;

    public double target;

    public Pendul(Servo PendulLeft, Servo PendulRight){
        this.PendulLeft = PendulLeft;
        this.PendulRight = PendulRight;
        target = PendulPositions.DOWN;
    }

    public double getPosition(){
        return (PendulLeft.getPosition() + PendulRight.getPosition()) / 2;
    }

    public void update(){
        PendulLeft.setPosition(target);
        PendulRight.setPosition(target);
    }
}
