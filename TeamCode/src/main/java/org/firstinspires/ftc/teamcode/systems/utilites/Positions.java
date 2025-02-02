package org.firstinspires.ftc.teamcode.systems.utilites;

import com.acmerobotics.dashboard.config.Config;

/** @noinspection some fields may be unused*/
@Config
public class Positions {
    // Intake Rotation
    public static double intakeRotationParallel = 0.345;
    public static double intakeRotationPerpendicular = 0.6466;
    public static double intakeRotationInit = intakeRotationParallel;

    // Intake Pendul
    public static double intakePendulDown = 0.43;
    public static double intakePendulEntrance = 0.585;
    public static double intakePendulUp = 0.94;
    public static double intakePendulInit = 0.9;

    // Intake Extend
    public static double intakeExtendTransfer = 0.32;
    public static double intakeExtendInit = 0.258;

    // Lift
    public static double liftDown = 0;
    public static double liftUp = 2;
    public static double liftSmash = 1;
    public static double liftBasket = 3;
    public static double liftClear = 0.5;
    public static double liftInit = 0;

    // OuttakeTest Rotation
    public static double outtakeRotationTransfer = 0.67;
    public static double outtakeRotationOuttake = 0.177;
    public static double outtakeRotationBasket = 0.3;
    public static double outtakeRotationInit = outtakeRotationTransfer;

    // OuttakeTest Claw
    public static double outtakeClawOpen = 0.4;
    public static double outtakeClawClosed = 0.69;
    public static double outtakeClawInit = outtakeClawClosed;

    // OuttakeTest Pendul
    public static double outtakePendulTransfer = 0.7;
    public static double outtakePendulBasket = 0.49;
    public static double outtakePendulOuttake = 0.2;
    public static double outtakePendulInit = 0.69;
}
