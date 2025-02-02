package org.firstinspires.ftc.teamcode.systems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.systems.subsystems.Lift;
import org.firstinspires.ftc.teamcode.systems.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.systems.subsystems.outtake.Pendulum;
import org.firstinspires.ftc.teamcode.systems.subsystems.outtake.Rotation;
import org.firstinspires.ftc.teamcode.systems.utilites.Positions;
import org.jetbrains.annotations.NotNull;

public class Outtake implements Action {
    public final Claw claw;
    public final Pendulum pendulum;
    public final Rotation rotation;
    public final Lift lift;

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

    public void setPosition(@NotNull OPositions position) {
        pendulum.setTargetPosition(position.getPendulum());
        rotation.setTargetPosition(position.getRotation());
        lift.setTargetPosition(position.getLift());
    }

    public enum OPositions {
        Init(Positions.outtakePendulumInit, Positions.outtakeRotationInit, Positions.liftInit),
        Transfer(Positions.outtakePendulumTransfer, Positions.outtakeRotationTransfer, Positions.liftTransfer),
        Basket(Positions.outtakePendulumBasket, Positions.outtakeRotationBasket, Positions.liftBasket),
        Bar(Positions.outtakePendulumBar, Positions.outtakeRotationBar, Positions.liftBar),
        Pickup(Positions.outtakePendulumPickup, Positions.outtakeRotationPickup, Positions.liftPickup);
        private final double pendulum;
        private final double rotation;
        private final double lift;

        OPositions(double pendulum, double rotation, double lift) {
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
