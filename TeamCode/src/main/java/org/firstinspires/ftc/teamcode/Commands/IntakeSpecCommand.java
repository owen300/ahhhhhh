package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

import java.util.concurrent.TimeUnit;

public class IntakeSpecCommand extends CommandBase {

    private final IntakeSubsystem intakeSubsystem;
    Timing.Timer timer = new Timing.Timer(0, TimeUnit.MILLISECONDS);


    public IntakeSpecCommand(IntakeSubsystem subsystem) {
        this.intakeSubsystem = subsystem;

        addRequirements(subsystem);
    }

    public void initialize() {
        this.intakeSubsystem.intake();
    }


    public boolean isFinished() {
        return intakeSubsystem.detectedSpec();
    }

    public void end(boolean interrupted) {
        this.intakeSubsystem.stop();
    }
}
