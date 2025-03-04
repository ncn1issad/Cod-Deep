package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

object Claw : Subsystem() {
    lateinit var servo: Servo
    private const val NAME = "claw_servo"

    override fun initialize() {
        servo = OpModeData.hardwareMap.get(Servo::class.java, NAME)
    }

    val open: Command
        get() = ServoToPosition(servo, 0.2, this)

    val close: Command
        get() = ServoToPosition(servo, 0.8, this)
}