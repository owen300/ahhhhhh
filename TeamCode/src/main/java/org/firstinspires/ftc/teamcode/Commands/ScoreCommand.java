package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.Commands.ExtensionGoToPosition;
import org.firstinspires.ftc.teamcode.Commands.TiltGoToPosition;
import org.firstinspires.ftc.teamcode.Commands.WristStow;
import org.firstinspires.ftc.teamcode.Subsystems.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TiltSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.WristSubsystem;

public class ScoreCommand {
    private final ExtensionSubsystem extendo;
    private final IntakeSubsystem intake;
    private final int samplePullDown = 0;
    private final TiltSubsystem tilt;
    private final WristSubsystem wrist;

    public ScoreCommand(ExtensionSubsystem extendo2, WristSubsystem wrist2, TiltSubsystem tilt2, IntakeSubsystem intake2) {
        this.extendo = extendo2;
        this.tilt = tilt2;
        this.wrist = wrist2;
        this.intake = intake2;
    }

    private SequentialCommandGroup scoreSample2() {
        return new SequentialCommandGroup(new WristScep(wrist),new Outakespec(intake), new ParallelCommandGroup(new ExtensionGoToPosition(extendo,ExtensionGoToPosition.STOW_POSITION),new WristStow(wrist)),new TiltGoToPosition(tilt,TiltGoToPosition.STOW));
    }
    private SequentialCommandGroup scoreSample() {
        return new SequentialCommandGroup(new ExtensionGoToPosition(extendo, ExtensionGoToPosition.ONE_STAGE_EXTENSION), new Outakespec(intake));
    }
    /* access modifiers changed from: package-private */
    /* renamed from: lambda$scoreSample$0$org-firstinspires-ftc-teamcode-robot-commands-ScoreCommand  reason: not valid java name */


    private SequentialCommandGroup scoreBucket() {
        return new SequentialCommandGroup(new InstantCommand(intake::outtake), new WaitCommand(500),new InstantCommand(intake::stop), new WristIntake(wrist,true),new ParallelCommandGroup(new ExtensionGoToPosition(extendo,ExtensionGoToPosition.STOW_POSITION),new WristStow(wrist)),new TiltGoToPosition(tilt,TiltGoToPosition.STOW));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$scoreBucket$1$org-firstinspires-ftc-teamcode-robot-commands-ScoreCommand  reason: not valid java name */
    public /* synthetic */ void m0lambda$scoreBucket$1$orgfirstinspiresftcteamcoderobotcommandsScoreCommand() {
        this.intake.outtake();
    }

    public SequentialCommandGroup getScoreCommand(boolean spec) {
        if (!spec) {
            return scoreBucket();
        }
        return scoreSample();
    }
}
