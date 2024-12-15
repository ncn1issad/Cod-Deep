package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.systems.Extend;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.Pendul;
import org.firstinspires.ftc.teamcode.systems.Positions.IntakePositions;
import org.firstinspires.ftc.teamcode.systems.Positions.PendulPositions;

import java.util.function.Supplier;

public class TeleOp extends OpMode {
    RobotHardware robot = new RobotHardware(this);

    FtcDashboard dashboard;

    final Lift lift = robot.lift;
    final Intake intake = robot.intake;
    final Pendul pendul = robot.pendul;
    final Extend extend = robot.extend;

    enum Gamepads {
        MOVE,
        ACTION;
// The gamepads are actually set in the init function
        private Gamepad gamepad;

        public void setGamepad(Gamepad gamepad) {
            this.gamepad = gamepad;
        }

        public Gamepad getGamepad() {
            return gamepad;
        }
    }

    private static Gamepad Move;
    private static Gamepad Action;

    public enum Buttons {
        INTAKE_IN(() -> Move.cross),
        INTAKE_OUT(() -> Move.square),
        PARALLEL_BASKET(() -> Action.dpad_up),
        PERPENDICULAR_BARS(() -> Action.dpad_left),
        PARALLEL_DOWN(() -> Action.dpad_right),
        PERPENDICULAR_SLAM(() -> Action.dpad_down),
        REVERSE_BARS(() -> Action.left_bumper),
        REVERSE_SLAM(() -> Action.right_bumper);

        private final Supplier<Boolean> button;

        Buttons(Supplier<Boolean> b) {
            this.button = b;
        }

        public boolean getButton() {
            return button.get();
        }
    }

    @Override
    public void init() {
        robot.init();
        Gamepads.MOVE.setGamepad(gamepad1);
        Gamepads.ACTION.setGamepad(gamepad2);

        Move = Gamepads.MOVE.getGamepad();
        Action = Gamepads.ACTION.getGamepad();

        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {
// Update the position of systems
        lift.update();
        intake.update();
        pendul.update();
// Movement of the robot
        robot.movement(Move);

        intake.runIntake(Buttons.INTAKE_IN.getButton(), Buttons.INTAKE_OUT.getButton());
        extend.setPower(Action.left_trigger - Action.right_trigger);

        if (Buttons.PARALLEL_BASKET.getButton()) {
            pendul.target = PendulPositions.BASKET;
            intake.target = IntakePositions.PARALLEL;
        } else if (Buttons.PERPENDICULAR_BARS.getButton()) {
            pendul.target = PendulPositions.BAR;
            intake.target = IntakePositions.PERPENDICULAR;
        } else if (Buttons.PARALLEL_DOWN.getButton()) {
            pendul.target = PendulPositions.DOWN;
            intake.target = IntakePositions.PARALLEL;
        } else if (Buttons.PERPENDICULAR_SLAM.getButton()) {
            pendul.target = PendulPositions.SLAM;
            intake.target = IntakePositions.PERPENDICULAR;
        } else if (Buttons.REVERSE_BARS.getButton()) {
            pendul.target = PendulPositions.BAR;
            intake.target = IntakePositions.REVERSE;
        } else if (Buttons.REVERSE_SLAM.getButton()) {
            pendul.target = PendulPositions.SLAM;
            intake.target = IntakePositions.REVERSE;
        }

        pendul.target -= Action.left_stick_y * Pendul.PENDUL_MULTIPLIER;
    }
}
