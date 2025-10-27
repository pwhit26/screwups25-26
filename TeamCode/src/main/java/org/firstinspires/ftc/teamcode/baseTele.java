package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp(name = "baseTele")
public class baseTele extends LinearOpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);
    private DcMotor frontRight, frontLeft, backRight, backLeft, intake, turret;
    private Servo arm, indexer, turnTurret, angleTurret1, angleTurret2;
    private boolean b2Pressable, bLast;
    boolean armSequenceActive;
    boolean armSequenceComplete;
    long sequenceStartTime = 0;
    int armSequenceStep = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Constants.setConstants(FConstants.class, LConstants.class);
        //DRIVE INIT
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "rightBack");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = hardwareMap.get(DcMotor.class, "leftBack");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //INTAKE INIT
        intake = hardwareMap.get(DcMotor.class, "intake");
        b2Pressable = false;
        bLast = false;

        //SPINNY THING INIT
        arm = hardwareMap.get(Servo.class, "arm");
        indexer = hardwareMap.get(Servo.class, "indexer");

        //TURRET INIT
        turret = hardwareMap.get(DcMotor.class, "turret");
        turnTurret = hardwareMap.get(Servo.class, "turnTurret");
        angleTurret1 = hardwareMap.get(Servo.class, "leftTurret");
        angleTurret2 = hardwareMap.get(Servo.class, "rightTurret");

        waitForStart();
        follower.startTeleopDrive();

        while (opModeIsActive()) {
            //DRIVE CONTROLS
            follower.setTeleOpMovementVectors(-gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x, true);
            follower.update();

            //INTAKE CONTROLS
            if (gamepad1.b && !bLast) {
                b2Pressable = !b2Pressable;
            }
            if (b2Pressable) {
                intake.setPower(1);
            }
            else {
                intake.setPower(0);
            }
            bLast = gamepad1.b;

            //SPINNY THING CONTROLS
            if (gamepad1.a && !armSequenceActive) {
                armSequenceActive = true;
                armSequenceComplete = false;
                sequenceStartTime = System.currentTimeMillis();
            }

            if (armSequenceActive && !armSequenceComplete) {
                long elapsedTime = System.currentTimeMillis() - sequenceStartTime;
                switch (armSequenceStep) {
                    case 0:
                        arm.setPosition(1);
                        if (elapsedTime >= 500) {
                            armSequenceStep++;
                            sequenceStartTime = System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        arm.setPosition(0);
                        if (elapsedTime >= 700) {
                            armSequenceStep++;
                            sequenceStartTime = System.currentTimeMillis();
                        }
                        break;
                    case 2:
                        indexer.setPosition(0.5);
                        break;
                }
            }
            //TURRET CONTROLS

            //TELEMETRY
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));
            telemetry.addData("Intake Power", intake.getPower());
            telemetry.addData("Arm Position", arm.getPosition());
            telemetry.addData("Indexer Position", indexer.getPosition());
            telemetry.addData("Turret Power", turret.getPower());
            telemetry.addData("Horizontal Turret Angle", turnTurret.getPosition());
            telemetry.addData("Vertical Turret Angle (1)", angleTurret1.getPosition());
            telemetry.addData("Vertical Turret Angle (2)", angleTurret2.getPosition());

            telemetry.update();
        }
    }
}
