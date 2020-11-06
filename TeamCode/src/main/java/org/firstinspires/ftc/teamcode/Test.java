package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class Test extends BaseRobot{
    private int stage = 0;
    @Override
    public void init () {
        super.init();
        claw(ConstantVariables.K_CLAW_SERVO_OPEN);
    }

    @Override
    public void start (){
        super.start();
    }

    @Override
    public void loop (){
        super.loop();
        switch(stage) {
            case 0:
                if (auto_mecanum(-0.5, 9)){
                    reset_drive_encoders();
                    stage++;
                }
                break;
            default:
                break;
        }

    }
}
