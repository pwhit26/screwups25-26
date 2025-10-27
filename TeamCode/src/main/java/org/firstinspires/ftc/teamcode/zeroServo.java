package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "zeroServo")
public class zeroServo extends LinearOpMode {
    private Servo servo1;
    private Servo servo2;
    @Override
    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo1.setPosition(0);
        servo2 = hardwareMap.get(Servo.class, "servo1");
        servo2.setPosition(0);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                servo1.setPosition(1);
                telemetry.addData("Servo Position: ", servo1.getPosition());
                servo2.setPosition(1);
                telemetry.addData("Servo Position: ", servo2.getPosition());
                telemetry.update();
            }
            else if (gamepad1.b) {
                servo1.setPosition(0);
                telemetry.addData("Servo Position: ", servo1.getPosition());
                servo2.setPosition(0);
                telemetry.addData("Servo Position: ", servo2.getPosition());
                telemetry.update();
            }
        }
    }
}