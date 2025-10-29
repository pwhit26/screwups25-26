package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp(name = "baseTele")
public class baseTele extends LinearOpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);
    private DcMotor frontRight, frontLeft, backRight, backLeft, intake, turret1, turret2;
    private Servo turnTurret, angleTurret0, angleTurret1;
    public static double turnTurretLowerBound = 0;
    public static double turnTurretUpperBound = 0.8;
    private boolean b2Pressable, bLast, a2Pressable, aLast;
    //private Servo arm, indexer;
    /*boolean armSequenceActive;
    boolean armSequenceComplete;
    long sequenceStartTime = 0;
    int armSequenceStep = 0;
     */

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
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        b2Pressable = false;
        bLast = false;

        /*
        //SPINNY THING INIT
        arm = hardwareMap.get(Servo.class, "arm");
        indexer = hardwareMap.get(Servo.class, "indexer");
         */

        //TURRET INIT
        turret1 = hardwareMap.get(DcMotor.class, "turret1");
        turret1.setDirection(DcMotorSimple.Direction.REVERSE);
        turret1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turret2 = hardwareMap.get(DcMotor.class, "turret2");
        turret2.setDirection(DcMotorSimple.Direction.REVERSE);
        turret2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turnTurret = hardwareMap.get(Servo.class, "turnTurret");
        turnTurret.scaleRange(turnTurretLowerBound, turnTurretUpperBound);
        turnTurret.setPosition(0.86);
        angleTurret0 = hardwareMap.get(Servo.class, "angleTurret0");
        angleTurret0.setPosition(0.2);
        angleTurret1 = hardwareMap.get(Servo.class, "angleTurret1");
        angleTurret1.setPosition(0.8);
        a2Pressable = false;
        aLast = false;

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

            /*
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
             */
            //TURRET CONTROLS
            if (gamepad1.dpad_up) {
                angleTurret0.setPosition(0);
                telemetry.addData("Servo Position: ", angleTurret0.getPosition());
                angleTurret1.setPosition(1);
                telemetry.addData("Servo Position: ", angleTurret1.getPosition());
                telemetry.update();
            }
            else if (gamepad1.dpad_right) {
                angleTurret0.setPosition(0.1);
                telemetry.addData("Servo Position: ", angleTurret0.getPosition());
                angleTurret1.setPosition(0.9);
                telemetry.addData("Servo Position: ", angleTurret1.getPosition());
                telemetry.update();
            }
            else if (gamepad1.dpad_down) {
                angleTurret0.setPosition(0.2);
                telemetry.addData("Servo Position: ", angleTurret0.getPosition());
                angleTurret1.setPosition(0.8);
                telemetry.addData("Servo Position: ", angleTurret1.getPosition());
                telemetry.update();
            }
            else if (gamepad1.dpad_left) {
                angleTurret0.setPosition(0.25);
                telemetry.addData("Servo Position: ", angleTurret0.getPosition());
                angleTurret1.setPosition(0.75);
                telemetry.addData("Servo Position: ", angleTurret1.getPosition());
                telemetry.update();
            }
            if (gamepad1.a && !aLast) {
                a2Pressable = !a2Pressable;
            }
            if (a2Pressable) {
                turret1.setPower(1);
                turret2.setPower(1);
            }
            else {
                turret1.setPower(0);
                turret2.setPower(0);
            }
            aLast = gamepad1.a;

            //TELEMETRY
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));
            telemetry.addData("Intake Power", intake.getPower());
            //telemetry.addData("Arm Position", arm.getPosition());
            //telemetry.addData("Indexer Position", indexer.getPosition());
            telemetry.addData("Turret Power", turret1.getPower());
            telemetry.addData("Turret Rotation Position", turnTurret.getPosition());
            telemetry.addData("Turret Angle (1)", angleTurret0.getPosition());
            telemetry.addData("Turret Angle (2)", angleTurret1.getPosition());

            telemetry.update();
        }
    }
}
