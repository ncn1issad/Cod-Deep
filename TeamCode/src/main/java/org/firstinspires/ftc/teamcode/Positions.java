package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

/**
 * Class containing all the positions for the robot's mechanisms.
 */
public class Positions {
    /**
     * Class containing all the positions for the intake mechanism.
     */
    public static class Intake {
        /**
         * Class containing all the positions for the intake extension.
         */
        @Config("Intake Extend Positions")
        public static class Extend {
            public static double in = 0.635;
            public static double out = 0.9;
        }
        /**
         * Class containing all the positions for the intake claw.
         */
        @Config("Intake Claw Positions")
        public static class Claw {
            public static double open = 0.46;
            public static double close = 0.79;
        }
        /**
         * Class containing all the positions for the intake rotation.
         */
        @Config("Intake Rotate Positions")
        public static class Rotate {
            public static double pickupWait = 0.1;
            public static double pickup = 0.05;
            public static double transfer = 0.75;
        }
        /**
         * Class containing all the positions for the intake spin.
         */
        @Config("Intake Spin Positions")
        public static class Spin {
            public static double middle = 0.525;
            public static double right = 0.785;
            public static double left = 0.265;
        }
        /**
         * Class containing all the positions for the intake pendulum.
         */
        @Config("Intake Pendulum Positions")
        public static class Pendulum {
            public static double pickupWait = 0.35;
            public static double pickup = 0.18;
            public static double transfer = 0.8;
        }
    }
    /**
     * Class containing all the positions for the outtake mechanism.
     */
    public static class Outtake {
        /**
         * Class containing all the positions for the outtake claw.
         */
        @Config("Outtake Claw Positions")
        public static class Claw {
            public static double open = 0.5;
            public static double close = 0.654;
        }
        /**
         * Class containing all the positions for the outtake rotation.
         */
        @Config("Outtake Rotate Positions")
        public static class Rotate {
            public static double pickup = 0.61;
            public static double transfer = 0.83;
            public static double bar = 0.77;
            public static double basket = 0.27;
        }
        /**
         * Class containing all the positions for the outtake pendulum.
         */
        @Config("Outtake Pendulum Positions")
        public static class Pendulum {
            public static double pickup = 0.834;
            public static double transfer = 0.19;
            public static double bar = 0.71;
            public static double clearBar = 0.7;
            public static double basket = 0.58;
        }
        /**
         * Class containing all the positions for the outtake lift.
         */
        @Config("Outtake Lift Positions")
        public static class Lift {
            public static double up = 3200.0;
            public static double hang = 1700.0;
            public static double half = 650.0;
            public static double transfer = 100.0;
            public static double down = 0.0;
        }
    }
}
