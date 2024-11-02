package org.firstinspires.ftc.teamcode.Teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TeamChassis extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // configuration:
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        //Reversing motors to mirror
        //Reverse other side motors if movement does not match
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double ypower = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double xpower = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rotation = gamepad1.right_stick_x;

            //Calculations:
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(ypower) + Math.abs(xpower) + Math.abs(rotation), 1);
            double frontLeftPower = (ypower + xpower + rotation) / denominator;
            double backLeftPower = (ypower - xpower + rotation) / denominator;
            double frontRightPower = (ypower - xpower - rotation) / denominator;
            double backRightPower = (ypower + xpower - rotation) / denominator;

            //Setting Power to each motor:
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
        }
    }
}