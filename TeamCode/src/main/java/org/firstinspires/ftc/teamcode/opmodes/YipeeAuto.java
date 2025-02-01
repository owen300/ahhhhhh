package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ActionCommand;
import org.firstinspires.ftc.teamcode.Commands.ExtensionGoToPosition;
import org.firstinspires.ftc.teamcode.Commands.ScoreCommand;
import org.firstinspires.ftc.teamcode.Commands.TiltGoToPosition;
import org.firstinspires.ftc.teamcode.Commands.WristSample;
import org.firstinspires.ftc.teamcode.Subsystems.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TiltSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.WristSubsystem;
import org.firstinspires.ftc.teamcode.rr.tuning.MecanumDrive;

import java.util.HashSet;
import java.util.Set;

@Autonomous
public class YipeeAuto extends CommandOpMode {
    String color="blue";
    Pose2d startpos;
    boolean run=false;
    MecanumDrive drive;
    ExtensionSubsystem extendo;
    TiltSubsystem tilt;
    IntakeSubsystem intake;
    WristSubsystem wrist;
    ScoreCommand score;
    @Override
    public void initialize(){
        extendo=new ExtensionSubsystem(hardwareMap,telemetry);
        tilt = new TiltSubsystem(hardwareMap,telemetry);
        intake = new IntakeSubsystem(hardwareMap,telemetry);
        wrist=new WristSubsystem(hardwareMap);
        tilt.init();
        score = new ScoreCommand(extendo,wrist,tilt,intake);
        while(opModeInInit()){
            if(gamepad1.a)color="red";
            if(color=="red")startpos=AutoPaths.redStart;
            else startpos=AutoPaths.blueStart;
        }
        drive=new MecanumDrive(hardwareMap,startpos);
    }

    @Override
    public void run(){
        if(!run){
            Set<Subsystem> set=new HashSet<Subsystem>();
            CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                    new ActionCommand(AutoPaths.firstSpec(drive,color),set),
                    new SequentialCommandGroup(
                            new ParallelCommandGroup(
                                    new WristSample(wrist),
                                    new TiltGoToPosition(tilt,TiltGoToPosition.TELEOP_SP)),
                            new ExtensionGoToPosition(extendo,ExtensionGoToPosition.SPEC))),
                    score.getScoreCommand(true),
                    new ActionCommand(AutoPaths.getSpec(drive,color),set),
                    new ActionCommand(AutoPaths.secondSpec(drive,color),set),
                    new ActionCommand(AutoPaths.park(drive,color),set)
            ));
            run=true;
        }
        telemetry.update();
        super.run();
    }
}
