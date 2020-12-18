package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
@Disabled
public class Project extends LinearOpMode {

    private double TICKS_PER_ROTATION=1500;
    private double INCHES_PER_ROTATION=3;
    private double TICKS_PER_INCH=TICKS_PER_ROTATION*INCHES_PER_ROTATION;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    private Servo MyServo = null;
    private void
    int stage = 0;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");

        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double frontLeftPower=y + x + rx;
            double backLeftPower=y - x + rx;
            double frontRightPower=y - x - rx;
            double backRightPower=y + x - rx;

            MyServo = hardwareMap.get(Servo.class, "Claw");
            MyServo.setPosition(0.0);
            if (runtime.seconds()>=2) {
                MyServo.setPosition(90/360);
                //grabbing foundation
            } else if (runtime.seconds()>=6) {
                MyServo.setPosition(0.0);
                //releasing foundation in building site
            } else if (runtime.seconds()>=14) {
                MyServo.setPosition(90/360);
                //grabbing sky stone
            } else if (runtime.seconds()>=22) {
                MyServo.setPosition(0.0);
                //releasing sky stone
            }

            mecanum(1, 0, 0);
            if (runtime.seconds()>=2) {
                mecanum(0, 0, 0);
                //grabbing foundation
            } else if (runtime.seconds()>=4){
                mecanum(-1, 0, 0);
            } else if (runtime.seconds()>=6){
                mecanum(0, 0, 0);
                //releasing foundation in building site
            } else if (runtime.seconds()>=8){
                mecanum(0, 1, 0);
                //moving to other side of field
            } else if (runtime.seconds()>=12){
                mecanum(1, 0, 0);
            } else if (runtime.seconds()>=14){
                mecanum(0, 0, 0);
                //grabbing sky stone
            } else if (runtime.seconds()>=16){
                mecanum(-1, 0, 0);
            } else if (runtime.seconds()>=18){
                mecanum(0, -1, 0);
                //moving back to starting location
            } else if (runtime.seconds()>=22){
                mecanum(0, 0, 0);
                //dropping stone on foundation
            } else if (runtime.seconds()>=24){
                mecanum(0, 2, 0);
                //driving to stop over separating line
            } else if (runtime.seconds()>=26){
                mecanum(0, 0, 0);
            }

        }
    }

    public void reset_encoders(){

    }

    public void mecanum (double y, double x, double rx) {

        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        double frontLeftPower=y + x + rx;
        double backLeftPower=y - x + rx;
        double frontRightPower=y - x - rx;
        double backRightPower=y + x - rx;
        if (Math.abs(frontLeftPower) > 1 || Math.abs(backLeftPower) > 1 ||
                Math.abs(frontRightPower) > 1 || Math.abs(backRightPower) > 1 ) {
            // Find the largest power
            double max = 0;
            max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
            max = Math.max(Math.abs(frontRightPower), max);
            max = Math.max(Math.abs(backRightPower), max);

            // Divide everything by max (it's positive so we don't need to worry
            // about signs)
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }
        frontLeftDrive.setPower(frontLeftPower);
        backLeftDrive.setPower(backLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backRightDrive.setPower(backRightPower);

    }

}

