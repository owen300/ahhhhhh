package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.Subsystems.WristSubsystem;

import java.util.concurrent.TimeUnit;

public class WristScep extends CommandBase {
    Timing.Timer timer = new Timing.Timer(300, TimeUnit.MILLISECONDS);
    private final WristSubsystem wristSubsystem;

    public WristScep(WristSubsystem subsystem) {
        this.wristSubsystem = subsystem;
        addRequirements(subsystem);
    }

    public void initialize() {
        this.timer.start();
        this.wristSubsystem.setWristScore();
    }

    public boolean isFinished() {
        return this.timer.done();
    }
}
