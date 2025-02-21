package org.firstinspires.ftc.teamcode.utils;

import org.firstinspires.ftc.teamcode.intake.IntakePositions;
import org.firstinspires.ftc.teamcode.outtake.OuttakePositions;

public class PositionPair {
    public final OuttakePositions outtake;
    public final IntakePositions intake;
    public PositionPair(OuttakePositions outtake, IntakePositions intake) {
        this.outtake = outtake;
        this.intake = intake;
    }
}
