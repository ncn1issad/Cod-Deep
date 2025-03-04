package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx

@TeleOp
class TeleOpProgram : NextFTCOpMode(Claw, Lift) {
    private val frontLeftName = "front_left"
    private val frontRightName = "front_right"
    private val backLeftName = "back_left"
    private val backRightName = "back_right"

    private lateinit var frontLeftMotor: MotorEx
    private lateinit var frontRightMotor: MotorEx
    private lateinit var backLeftMotor: MotorEx
    private lateinit var backRightMotor: MotorEx

    private lateinit var motors: Array<MotorEx>

    lateinit var driverControlled: Command

    override fun onInit() {
        frontLeftMotor = MotorEx(frontLeftName)
        backLeftMotor = MotorEx(backLeftName)
        backRightMotor = MotorEx(backRightName)
        frontRightMotor = MotorEx(frontRightName)

        // Change the motor directions to suit your robot.
        frontLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        backLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        frontRightMotor.direction = DcMotorSimple.Direction.FORWARD
        backRightMotor.direction = DcMotorSimple.Direction.FORWARD

        motors = arrayOf(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor)
    }

    override fun onStartButtonPressed() {
        driverControlled = MecanumDriverControlled(motors, gamepadManager.gamepad1)
        driverControlled()

        gamepadManager.gamepad2.leftBumper.pressedCommand = { Claw.close }
        gamepadManager.gamepad2.rightBumper.pressedCommand = { Claw.open }
    }


}