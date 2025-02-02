package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.systems.subsystems.Lift;
import org.firstinspires.ftc.teamcode.systems.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.systems.subsystems.outtake.Pendulum;
import org.firstinspires.ftc.teamcode.systems.subsystems.outtake.Rotation;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;
import org.firstinspires.ftc.teamcode.utilities.SystemFactory;
import org.firstinspires.ftc.teamcode.utilities.SystemMechanism;
import org.firstinspires.ftc.teamcode.utilities.SystemTeleOp;
import org.jetbrains.annotations.NotNull;

public class Outtake implements SystemMechanism {
    public final Claw claw;
    public final Pendulum pendulum;
    public final Rotation rotation;
    public final Lift lift;

    State currentState = State.Init;

    public Outtake(HardwareMap hardwareMap) {
        claw = new Claw(hardwareMap);
        pendulum = new Pendulum(hardwareMap);
        rotation = new Rotation(hardwareMap);
        lift = new Lift(hardwareMap);
    }

    @Override
    public boolean run(@NonNull TelemetryPacket packet) {
        return claw.run(packet) && pendulum.run(packet) && rotation.run(packet) && lift.run(packet);
    }

    @Override
    public void setPosition(Enum<?> position) {
        if (position instanceof State) {
            State state = (State) position;
            pendulum.setTargetPosition(state.getPendulum());
            rotation.setTargetPosition(state.getRotation());
            lift.setTargetPosition(state.getLift());
            currentState = state;
        }
    }

    @Override
    public Enum<?> getTargetPosition() {
        return currentState;
    }

    @Override
    public void update(@NotNull Gamepad gamepad) {
        if (gamepad.dpad_right) {
            setPosition(State.Transfer);
        }
        else if (gamepad.cross) {
            setPosition(State.Basket);
        }
        else if (gamepad.dpad_up) {
            setPosition(State.Bar);
        }
        else if (gamepad.square) {
            setPosition(State.Pickup);
        }
    }

    public enum State {
        Init(Positions.outtakePendulumInit, Positions.outtakeRotationInit, Positions.liftInit),
        Transfer(Positions.outtakePendulumTransfer, Positions.outtakeRotationTransfer, Positions.liftTransfer),
        Basket(Positions.outtakePendulumBasket, Positions.outtakeRotationBasket, Positions.liftBasket),
        Bar(Positions.outtakePendulumBar, Positions.outtakeRotationBar, Positions.liftBar),
        Pickup(Positions.outtakePendulumPickup, Positions.outtakeRotationPickup, Positions.liftPickup);
        private final double pendulum;
        private final double rotation;
        private final double lift;

        State(double pendulum, double rotation, double lift) {
            this.pendulum = pendulum;
            this.rotation = rotation;
            this.lift = lift;
        }
        public double getPendulum() {
            return pendulum;
        }
        public double getRotation() {
            return rotation;
        }
        public double getLift() {
            return lift;
        }
    }
}

class OuttakeFactory implements SystemFactory {
    @Override
    public SystemMechanism systemFactory(HardwareMap hardwareMap) {
        return new Outtake(hardwareMap);
    }
}

@TeleOp (name = "Outtake TeleOp", group = "B")
class OuttakeTeleOp extends SystemTeleOp {
    public OuttakeTeleOp() {
        super(new OuttakeFactory());
    }
}