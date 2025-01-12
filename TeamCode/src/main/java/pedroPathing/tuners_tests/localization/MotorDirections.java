package pedroPathing.tuners_tests.localization;

import static com.pedropathing.follower.FollowerConstants.leftFrontMotorDirection;
import static com.pedropathing.follower.FollowerConstants.leftFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.leftRearMotorDirection;
import static com.pedropathing.follower.FollowerConstants.leftRearMotorName;
import static com.pedropathing.follower.FollowerConstants.rightFrontMotorDirection;
import static com.pedropathing.follower.FollowerConstants.rightFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.rightRearMotorDirection;
import static com.pedropathing.follower.FollowerConstants.rightRearMotorName;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Disabled
@TeleOp(name = "IntakeMotor Directions", group = "Teleop Test")
public class MotorDirections extends OpMode {
    private Telemetry telemetryA;

    private DcMotorEx leftFront;
    private DcMotorEx leftRear;
    private DcMotorEx rightFront;
    private DcMotorEx rightRear;
    private List<DcMotorEx> motors;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);

        leftFront = hardwareMap.get(DcMotorEx.class, leftFrontMotorName);
        leftRear = hardwareMap.get(DcMotorEx.class, leftRearMotorName);
        rightRear = hardwareMap.get(DcMotorEx.class, rightRearMotorName);
        rightFront = hardwareMap.get(DcMotorEx.class, rightFrontMotorName);
        leftFront.setDirection(leftFrontMotorDirection);
        leftRear.setDirection(leftRearMotorDirection);
        rightFront.setDirection(rightFrontMotorDirection);
        rightRear.setDirection(rightRearMotorDirection);

        motors = Arrays.asList(leftFront, leftRear, rightFront, rightRear);

        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.addLine("This will allow you to test the directions of your motors. You can change the directions in FTCDashboard -> FollowerConstants.");
        telemetryA.update();
    }

    @Override
    public void loop() {
        Constants.setConstants(FConstants.class, LConstants.class);
        leftFront.setDirection(leftFrontMotorDirection);
        leftRear.setDirection(leftRearMotorDirection);
        rightFront.setDirection(rightFrontMotorDirection);
        rightRear.setDirection(rightRearMotorDirection);

        if(gamepad1.a)
            leftFront.setPower(1);

        if(gamepad1.y)
            leftRear.setPower(1);

        if(gamepad1.b)
            rightFront.setPower(1);

        if(gamepad1.x)
            rightRear.setPower(1);

        telemetryA.addLine("Press A to spin the left front intakeMotor at 100% power");
        telemetryA.addLine("Press Y to spin the left rear intakeMotor at 100% power");
        telemetryA.addLine("Press B to spin the right front intakeMotor at 100% power");
        telemetryA.addLine("Press X to spin the right rear intakeMotor at 100% power");
        telemetryA.addData("Left Front IntakeMotor Direction: ", leftFrontMotorDirection);
        telemetryA.addData("Left Rear IntakeMotor Direction: ", leftRearMotorDirection);
        telemetryA.addData("Right Front IntakeMotor Direction: ", rightFrontMotorDirection);
        telemetryA.addData("Right Rear IntakeMotor Direction: ", rightRearMotorDirection);
        telemetryA.update();
    }
}
