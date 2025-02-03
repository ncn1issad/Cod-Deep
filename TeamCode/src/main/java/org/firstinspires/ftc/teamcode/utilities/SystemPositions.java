package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.config.Config;

/** @noinspection some fields may be unused*/
@Config
public class SystemPositions {
    // Intake Pendulum
    public static double intakePendulumDown = 0.43;
    public static double intakePendulumEntrance = 0.585;
    public static double intakePendulumUp = 0.94;
    public static double intakePendulumInit = 0.9;

    // Intake Extend
    public static double intakeExtendTransfer = 0.32;
    public static double intakeExtendInit = 0.258;

    // Lift
    public static int liftTransfer = 0;
    public static int liftBar = 1;
    public static int liftBasket = 3;
    public static int liftPickup = 0;
    public static int liftInit = 0;

    // OuttakeTest Rotation
    public static double outtakeRotationTransfer = 0.67;
    public static double outtakeRotationBar = 0.177;
    public static double outtakeRotationBasket = 0.3;
    public static double outtakeRotationPickup = 0.5;
    public static double outtakeRotationInit = outtakeRotationTransfer;

    // OuttakeTest Claw
    public static double outtakeClawOpen = 0.4;
    public static double outtakeClawClosed = 0.69;
    public static double outtakeClawInit = outtakeClawClosed;

    // OuttakeTest Pendulum
    public static double outtakePendulumTransfer = 0.7;
    public static double outtakePendulumBasket = 0.49;
    public static double outtakePendulumBar = 0.2;
    public static double outtakePendulumPickup = 0.0;
    public static double outtakePendulumInit = 0.69;
}
