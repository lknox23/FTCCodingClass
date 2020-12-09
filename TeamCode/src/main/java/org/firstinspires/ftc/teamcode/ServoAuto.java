package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class ServoAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private Servo myServo = null;

    public void runOpMode(){
        myServo = hardwareMap.get(Servo.class, "my_servo");

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            if (runtime.seconds()<5) {
                myServo.setPosition(0.5);
            }
            else {
                myServo.setPosition(0);
            }
        }
    }
}
