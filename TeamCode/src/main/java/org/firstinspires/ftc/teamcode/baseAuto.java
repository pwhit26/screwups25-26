package org.firstinspires.ftc.teamcode; // make sure this aligns with class location

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@Autonomous(name = "Example Auto", group = "Examples")
public class baseAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private final Pose start1 = new Pose(0, 0, Math.toRadians(0));
    private final Pose pos1 = new Pose(pos1x, pos1y, Math.toRadians(0));
    public static double start1x = 0.0;
    public static double start1y = 0.0;
    public static double pos1x = 2.0;
    public static double pos1y = 2.0;
    String pathState = "init";

    private PathChain fMove;

    public void buildPaths() {
        // Path for scoring preload
        fMove = follower.pathBuilder()
                .addPath(new BezierLine(new Point(start1), new Point(pos1)))
                .setLinearHeadingInterpolation(start1.getHeading(), pos1.getHeading())
                .build();
    }

    @Override
    public void init() {
        follower = new Follower(hardwareMap);
        buildPaths();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("path state", pathState);
    }

    public void start() {

    }



    public void autonomousPathUpdate() {
        switch (pathState) {
        case "move off line":
        if (!follower.isBusy()) {
            follower.followPath(fMove, true);
            setPathState("move to bar");
        }
        break;

    }
    }
    public void setPathState (String pState){
        pathState = pState;
        pathTimer.resetTimer();
    }
}