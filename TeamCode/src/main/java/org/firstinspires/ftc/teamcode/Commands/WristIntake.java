package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.teamcode.Subsystems.WristSubsystem;

public class WristIntake extends CommandBase {
    Timing.Timer timer = new Timing.Timer(800, TimeUnit.MILLISECONDS);
    private final WristSubsystem wristSubsystem;
    private boolean sample=true;

    public WristIntake(WristSubsystem subsystem, boolean sample) {
        this.wristSubsystem = subsystem;
        this.sample=sample;
        addRequirements(subsystem);
    }

    public void initialize() {
        this.timer.start();
        this.wristSubsystem.setWristIntake(sample);
    }

    public boolean isFinished() {
        return this.timer.done();
    }
}
