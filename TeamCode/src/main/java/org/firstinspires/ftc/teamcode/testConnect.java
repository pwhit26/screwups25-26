package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "testConnect")
public class testConnect extends LinearOpMode {
    private Servo servo;
    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(Servo.class, "servo");
        servo.setPosition(0);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                servo.setPosition(1);
                telemetry.addData("Servo Position: ", servo.getPosition());
                telemetry.update();
            }
        }
    }
}
