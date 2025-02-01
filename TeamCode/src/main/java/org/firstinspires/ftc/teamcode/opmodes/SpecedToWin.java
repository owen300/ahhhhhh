package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.DriveFieldCentric;
import org.firstinspires.ftc.teamcode.Commands.ExtensionGoToPosition;
import org.firstinspires.ftc.teamcode.Commands.IntakeAutoCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeSpecCommand;
import org.firstinspires.ftc.teamcode.Commands.ScoreCommand;
import org.firstinspires.ftc.teamcode.Commands.TiltGoToPosition;
import org.firstinspires.ftc.teamcode.Commands.TiltHang;
import org.firstinspires.ftc.teamcode.Commands.WristDeposit;
import org.firstinspires.ftc.teamcode.Commands.WristIntake;
import org.firstinspires.ftc.teamcode.Commands.WristSample;
import org.firstinspires.ftc.teamcode.Commands.WristStow;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TiltSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.WristSubsystem;
import org.firstinspires.ftc.teamcode.TriggerAnalogButton;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
@TeleOp
public class SpecedToWin extends CommandOpMode {
    static boolean spec=false;
    boolean run=false;
    @Override
    public void initialize(){
        GamepadEx gamepadD=new GamepadEx(gamepad1);
        GamepadEx gamepadC=new GamepadEx(gamepad2);
        DriveSubsystem drive = new DriveSubsystem(hardwareMap);
        ExtensionSubsystem extendo=new ExtensionSubsystem(hardwareMap,telemetry);
        TiltSubsystem tilt = new TiltSubsystem(hardwareMap,telemetry);
        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap,telemetry);
        WristSubsystem wrist=new WristSubsystem(hardwareMap);
        tilt.init();
        ScoreCommand score = new ScoreCommand(extendo,wrist,tilt,intake);
        TriggerAnalogButton driverTrigger =
                new TriggerAnalogButton(gamepadD, GamepadKeys.Trigger.LEFT_TRIGGER,0.7);
        TriggerAnalogButton scoreTrigger =
                new TriggerAnalogButton(gamepadC,GamepadKeys.Trigger.RIGHT_TRIGGER,0.7);


        //drive
        drive.setDefaultCommand(
                new DriveFieldCentric(
                        drive,
                        () -> gamepadD.getLeftY(),
                        () -> gamepadD.getLeftX(),
                        () -> gamepadD.getRightX(),
                        () -> driverTrigger.get()));

        //spec intake
        gamepadC.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new SequentialCommandGroup(
                        new ExtensionGoToPosition(extendo,ExtensionGoToPosition.INTAKE),
                        new WristIntake(wrist,true),
                        new IntakeAutoCommand(intake),
                        new ParallelCommandGroup(
                        new WristStow(wrist))
                ));
        gamepadC.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(intake::outtake)).whenReleased(intake::stop);

        //stow
        gamepadC.getGamepadButton(GamepadKeys.Button.Y).whenPressed( new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(intake::stop),
                        new WristStow(wrist),
                new ExtensionGoToPosition(extendo,ExtensionGoToPosition.STOW_POSITION)),
                new SequentialCommandGroup (
                        new TiltGoToPosition(tilt,TiltGoToPosition.PRE_STOW),
                        new TiltGoToPosition(tilt,TiltGoToPosition.STOW)) // the slow stow
        ));

        //score spec
        gamepadC.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed( new SequentialCommandGroup(
           new ParallelCommandGroup(
                   new WristSample(wrist),
                   new TiltGoToPosition(tilt,TiltGoToPosition.TELEOP_SP)),
                   new ExtensionGoToPosition(extendo,ExtensionGoToPosition.SPEC),
                new InstantCommand(()->SpecedToWin.spec(true))
        ));

        //score sample
        gamepadC.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed( new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new WristDeposit(wrist),
                        new TiltGoToPosition(tilt,TiltGoToPosition.TELEOP_BUCKETH)),
                new ExtensionGoToPosition(extendo,ExtensionGoToPosition.HIGH_BUCKET_POS),
                new InstantCommand(()->SpecedToWin.spec(false))
        ));

        //score command
        gamepadC.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new ConditionalCommand(score.getScoreCommand(true), score.getScoreCommand(false), () -> spec));

        //intake
        gamepadC.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new SequentialCommandGroup(
                        new TiltGoToPosition(tilt, TiltGoToPosition.TELEOP_INTAKE+5),
                        new ExtensionGoToPosition(extendo,ExtensionGoToPosition.INTAKE_FAR),
                        new WristIntake(wrist,true),
                        new IntakeAutoCommand(intake)
                ));

        //hang
        gamepadD.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new SequentialCommandGroup(
                        new TiltGoToPosition(tilt, TiltGoToPosition.hang),
                        new WristIntake(wrist, true)
                )
        );
        //reset imu
        gamepadD.getGamepadButton(GamepadKeys.Button.START).whenPressed(drive::resetIMU);

        // hang
        gamepadD.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whileHeld(
                new TiltHang(tilt)
                );
        gamepadD.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenReleased(
                new TiltGoToPosition(tilt, TiltGoToPosition.STOW)
        );
    }
    @Override
    public void run(){
        if(run) telemetry.update();
        run=true;
        super.run();
    }
    static void spec(boolean spec1){
       spec=spec1;
    }

}
