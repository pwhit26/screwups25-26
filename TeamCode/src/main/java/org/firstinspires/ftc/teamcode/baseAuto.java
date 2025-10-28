package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Config
@Autonomous(name = "Example Auto", group = "Examples")
public class baseAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    public static double start1x = 0.0;
    public static double start1y = 0.0;
    public static double pos1x = 2.0;
    public static double pos1y = 2.0;

    private final Pose start1 = new Pose(start1x, start1y, Math.toRadians(0));
    private final Pose pos1 = new Pose(pos1x, pos1y, Math.toRadians(0));

    private PathChain fMove;
    private String pathState = "init";

    public void buildPaths() {
        fMove = follower.pathBuilder()
                .addPath(new BezierLine(new Point(start1), new Point(pos1)))
                .setLinearHeadingInterpolation(start1.getHeading(), pos1.getHeading())
                .build();
    }

    @Override
    public void init() {
        follower = new Follower(hardwareMap);
        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        buildPaths();
    }

    @Override
    public void start() {
        setPathState("move off line");
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("path state", pathState);
        telemetry.update();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case "move off line":
                if (!follower.isBusy()) {
                    follower.followPath(fMove, true);
                    setPathState("done");
                }
                break;

        }
    }

    public void setPathState(String pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
}
