package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Intake.Extend;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.Outtake;
import org.firstinspires.ftc.teamcode.systems.SubSystems.Outtake.Pendul;
import org.firstinspires.ftc.teamcode.systems.Positions.IntakePositions;
import org.firstinspires.ftc.teamcode.systems.Positions.PendulPositions;

import java.util.function.Supplier;

@TeleOp(name = "TeleOp", group = "A")
public class Teleop extends OpMode {
    RobotHardware robot = new RobotHardware(this);

    FtcDashboard dashboard;

    final Lift lift = robot.lift;
    final Intake intake = robot.intake;
    final Outtake outtake = robot.outtake;

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
        robot.update(dashboard);
// Movement of the robot
        robot.movement(Move);

        intake.runIntake(Buttons.INTAKE_IN.getButton(), Buttons.INTAKE_OUT.getButton());
        intake.extend.setPower(Action.left_trigger - Action.right_trigger);

        if (Buttons.PARALLEL_BASKET.getButton()) {
            outtake.pendul.target = PendulPositions.BASKET;
            intake.rotation.target = IntakePositions.PARALLEL;
        } else if (Buttons.PERPENDICULAR_BARS.getButton()) {
            outtake.pendul.target = PendulPositions.BAR;
            intake.rotation.target = IntakePositions.PERPENDICULAR;
        } else if (Buttons.PARALLEL_DOWN.getButton()) {
            outtake.pendul.target = PendulPositions.DOWN;
            intake.rotation.target = IntakePositions.PARALLEL;
        } else if (Buttons.PERPENDICULAR_SLAM.getButton()) {
            outtake.pendul.target = PendulPositions.SLAM;
            intake.rotation.target = IntakePositions.PERPENDICULAR;
        } else if (Buttons.REVERSE_BARS.getButton()) {
            outtake.pendul.target = PendulPositions.BAR;
            intake.rotation.target = IntakePositions.REVERSE;
        } else if (Buttons.REVERSE_SLAM.getButton()) {
            outtake.pendul.target = PendulPositions.SLAM;
            intake.rotation.target = IntakePositions.REVERSE;
        }

        outtake.pendul.target -= Action.left_stick_y * Pendul.PENDUL_MULTIPLIER;

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Extend power", intake.extend.getPower());

        dashboard.sendTelemetryPacket(packet);
        dashboard.updateConfig();
    }
}
