package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d((-6*12)+6.25+48, (6*12)-(18/2), Math.toRadians(-90)))
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(0, 36),new Rotation2d(Math.sin(Math.toRadians(90)),Math.cos(Math.toRadians(90))))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d((-6*12)+24,48,Math.toRadians(-90)),Math.toRadians(90))
                .lineToY((6*12)-(18/2))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(0,36, Math.toRadians(-90)),Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d((-6*12)+24,65,Math.toRadians(-90)),Math.toRadians(90))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}