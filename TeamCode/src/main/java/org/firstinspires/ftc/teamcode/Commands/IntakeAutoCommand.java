package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

import java.util.concurrent.TimeUnit;

public class IntakeAutoCommand extends CommandBase {

    private final IntakeSubsystem intakeSubsystem;
    Timing.Timer timer = new Timing.Timer(150, TimeUnit.MILLISECONDS);


    public IntakeAutoCommand(IntakeSubsystem subsystem) {
        this.intakeSubsystem = subsystem;

        addRequirements(subsystem);
    }

    public void initialize() {
        this.intakeSubsystem.intake();
    }

    public void execute() {

    }

    public boolean isFinished() {
        return intakeSubsystem.detected();
    }

    public void end(boolean interrupted) {
    intakeSubsystem.stop();
    }
}
