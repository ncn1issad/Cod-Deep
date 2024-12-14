package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.Servo;

public class Pendul {
    Servo PendulLeft;
    Servo PendulRight;

    public Pendul(Servo PendulLeft, Servo PendulRight){
        this.PendulLeft = PendulLeft;
        this.PendulRight = PendulRight;
    }
}
