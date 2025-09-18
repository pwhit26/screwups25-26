package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp(name = "baseTele")
public class baseTele extends LinearOpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    @Override
    public void runOpMode() throws InterruptedException {
        while (opModeInInit()) {
            Constants.setConstants(FConstants.class, LConstants.class);
            follower = new Follower(hardwareMap);
            follower.setStartingPose(startPose);

            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backRight = hardwareMap.get(DcMotor.class, "backRight");
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        }
        waitForStart();
        while (opModeIsActive()) {
            follower.startTeleopDrive();
            follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
            follower.update();

            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));


            telemetry.update();
        }
    }
}
