package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

import java.util.concurrent.TimeUnit;

public class Outakespec extends CommandBase {
    Timing.Timer timer = new Timing.Timer(500, TimeUnit.MILLISECONDS);
    private final IntakeSubsystem intake;

    public Outakespec(IntakeSubsystem subsystem) {
        this.intake = subsystem;
        addRequirements(subsystem);
    }

    public void initialize() {
        this.timer.start();
        intake.outtake();
    }

    public boolean isFinished() {
        return this.timer.done();
    }

    @Override
    public void end(boolean interrupted) {
        this.intake.stop();
    }
}
