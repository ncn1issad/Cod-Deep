package org.firstinspires.ftc.teamcode.utilities;

public class ServoSmoothing {
    public double Smoothing(double current, double target){
        return (target * 0.5) + (current * 0.5);
    }
}
