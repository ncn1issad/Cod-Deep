package org.firstinspires.ftc.teamcode

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition

object Lift : Subsystem() {
    lateinit var motor: MotorEx

    private val controller = PIDFController(0.005, 0.0, 0.0, StaticFeedforward(0.0))

    private const val NAME = "lift_motor"

    override fun initialize() {
        motor = MotorEx(NAME)
    }

    fun setPower(power: Double) {
        motor.power = power
    }

    fun setTargetPosition(position: Double) {
        RunToPosition(motor, position, controller, this).start()
    }

    val toLow: Command
        get() = RunToPosition(motor, 0.0, controller, this)

    val toHigh: Command
        get() = RunToPosition(motor, 1000.0, controller, this)

    val toMiddle: Command
        get() = RunToPosition(motor, 500.0, controller, this)
}