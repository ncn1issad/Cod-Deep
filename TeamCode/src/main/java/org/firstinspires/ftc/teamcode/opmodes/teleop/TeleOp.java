package org.firstinspires.ftc.teamcode.opmodes.teleop;

import android.app.Notification;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.systems.Extend;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.Pendul;

import java.util.function.Function;

public class TeleOp extends OpMode {
    RobotHardware robot = new RobotHardware(this);

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

    private static Gamepad Move = Gamepads.MOVE.getGamepad();
    private static Gamepad Action = Gamepads.ACTION.getGamepad();

    enum Buttons {
        INTAKE_IN(Move.cross),
        INTAKE_OUT(Move.square),
        PARALLEL_BASKET(Action.dpad_up),
        PERPENDICULAR_BARS(Action.dpad_left),
        PARALLEL_DOWN(Action.dpad_right),
        PERPENDICULAR_SLAM(Action.dpad_down),
        REVERSE_BARS(Action.left_bumper),
        REVERSE_SLAM(Action.right_bumper);

        private final boolean button;

        Buttons(boolean b) {
            this.button = b;
        }

        public boolean getButton() {
            return button;
        }
    }

    @Override
    public void init() {
        robot.init();
        Gamepads.MOVE.setGamepad(gamepad1);
        Gamepads.ACTION.setGamepad(gamepad2);
    }

    @Override
    public void loop() {
// Update the position of systems
        lift.update();
        intake.update();
        pendul.update();
// Movement of the robot
        robot.movement(Gamepads.MOVE.getGamepad());

        intake.runIntake(Buttons.INTAKE_IN.getButton(), Buttons.INTAKE_OUT.getButton());
        extend.setPower(Gamepads.ACTION.getGamepad().left_trigger - Gamepads.ACTION.getGamepad().right_trigger);

        if (Buttons.PARALLEL_BASKET.getButton()) {
            pendul.target = Pendul.PendulPosition.BASKET;
        }
    }
}
