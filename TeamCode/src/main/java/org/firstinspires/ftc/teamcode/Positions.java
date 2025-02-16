package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

public class Positions {
    public static class Intake {
        @Config
        public static class Extend {
            public static double in = 0.635;
            public static double out = 0.9;
        }
        @Config
        public static class Claw {
            public static double open = 0.46;
            public static double close = 0.79;
        }
        @Config
        public static class Rotate {
            public static double pickup = 0.017;
            public static double transfer = 0.753;
        }
        @Config
        public static class Spin {
            public static double middle = 0.525;
        }
        @Config
        public static class Pendulum {
            public static double pickupWait = 0.222;
            public static double pickup = 0.1;
            public static double transfer = 0.88;
        }
    }
}
