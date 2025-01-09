package org.firstinspires.ftc.teamcode.systems;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Positions {
    public static class Intake {
        public static class Rotation {
            public static double parallel = 0.345;
            public static double perpendicular = 0.6466;

            public static double init = parallel;
        }
        public static class Pendul {
            public static double down = 0.43;
            public static double entrance = 0.50;
            public static double up = 0.84;

            public static double init = 0.9;
        }
        public static class Extend {
            public static double transfer = 0.32;

            public static double init = 0.258;
        }
    }
    //TODO: Add positions to Teleop
    /** @noinspection unused*/
    public static class Lift {
        public static double down = 0;
        public static double up = 2;
        public static double smash = 1;
        public static double basket = 3;
        public static double clear = 0.5;

        public static double init = 0;
    }
    public static class Outtake {
        public static class Rotation {
            public static double transfer = 0.9;
            public static double outtake = 0.4;
            public static double basket = 0.3;

            public static double init = transfer;
        }
        public static class Claw {
            public static double open = 0.4;
            public static double closed = 0.95;

            public static double init = closed;
        }
        public static class Pendul {
            public static double transfer = 0.93;
            public static double basket = 0.49;
            public static double outtake = 0.2;

            public static double init = 0.69;
        }
    }
}
