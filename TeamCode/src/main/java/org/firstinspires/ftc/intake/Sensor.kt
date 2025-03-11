package org.firstinspires.ftc.intake

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

/**
 * The color sensor of the intake system.
 * @param hardwareMap The hardware map for the robot.
 */
class Sensor(
    hardwareMap: HardwareMap
) {
    /**
     * The color sensor itself
     */
    val sensor: RevColorSensorV3 = hardwareMap.get(RevColorSensorV3::class.java, "colorSensor")
    /**
     * The sensor that detects if the claw is holding a sample.
     */
    val isHoveringSample: Boolean
        get() = sensor.getDistance(DistanceUnit.CM)  < 4.0
    /**
     * Checks if the sensor is detecting red.
     */
    val isRed: Boolean
        get() = sensor.red() > sensor.green() && sensor.red() > 250
    /**
     * Checks if the sensor is detecting blue.
     */
    val isBlue: Boolean
        get() = sensor.blue() > 2 * sensor.green()
    /**
     * Checks if the sensor is detecting yellow.
     */
    val isYellow: Boolean
        get() = sensor.green() > sensor.red() && sensor.green() > 450 && sensor.blue() < 1000
}