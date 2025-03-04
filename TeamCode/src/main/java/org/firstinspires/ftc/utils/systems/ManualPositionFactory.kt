package org.firstinspires.ftc.utils.systems

import com.qualcomm.robotcore.hardware.HardwareMap

fun interface ManualPositionFactory {
    /**
     * Creates a new class that implements the [ManualPositionMechanism] interface.
     */
    fun manualPositionFactory(hardwareMap: HardwareMap): ManualPositionMechanism
}