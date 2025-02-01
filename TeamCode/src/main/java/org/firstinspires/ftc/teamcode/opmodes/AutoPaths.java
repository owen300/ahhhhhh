package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.rr.tuning.MecanumDrive;

public class AutoPaths {
    public static Pose2d blueStart= new Pose2d((6*12)-6.25-48, (-6*12)+(18/2), Math.toRadians(90));
    public static Pose2d redStart= new Pose2d((-6*12)+6.25+48, (6*12)-(18/2), Math.toRadians(-90));
    public static Action firstSpec(MecanumDrive drive, String color){
        if(color=="blue") return drive.actionBuilder(blueStart).setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(0, -36),new Rotation2d(Math.sin(Math.toRadians(-90)),Math.cos(Math.toRadians(-90))))
                .build();
        return  drive.actionBuilder(redStart).setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(0, 36),new Rotation2d(Math.sin(Math.toRadians(90)),Math.cos(Math.toRadians(90))))
                .build();
    }
    public static Action getSpec(MecanumDrive drive, String color){
        if(color=="blue") return drive.actionBuilder(new Pose2d(0,-36,Math.toRadians(90)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d((6*12)-24,-48,Math.toRadians(90)),Math.toRadians(-90))
                .lineToY((-6*12)+(18/2))
                .build();
        return  drive.actionBuilder(new Pose2d(0,36,Math.toRadians(90)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d((-6*12)+24,48,Math.toRadians(-90)),Math.toRadians(90))
                .lineToY((6*12)-(18/2))
                .build();
    }
    public static Action secondSpec(MecanumDrive drive, String color){
        if(color=="blue") return drive.actionBuilder(new Pose2d((6*12)-24,(-6*12)+(18/2),Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(0,-36, Math.toRadians(90)),Math.toRadians(0))
                .build();
        return  drive.actionBuilder(new Pose2d((-6*12)+24,(6*12)-(18/2),Math.toRadians(-90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(0,36, Math.toRadians(-90)),Math.toRadians(180))
                .build();
    }
    public static Action park(MecanumDrive drive, String color){
        if(color=="blue") return drive.actionBuilder(new Pose2d(0,-36,Math.toRadians(90)))
                 .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d((6*12)-24,-65,Math.toRadians(90)),Math.toRadians(-90))
                .build();
        return  drive.actionBuilder(new Pose2d(0,36,Math.toRadians(-90)))
                 .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d((-6*12)+24,65,Math.toRadians(-90)),Math.toRadians(90))
                .build();
    }

}
