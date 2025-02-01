package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.teamcode.Subsystems.WristSubsystem;

public class WristStow extends CommandBase {
    Timing.Timer timer = new Timing.Timer(1000, TimeUnit.MILLISECONDS);
    private final WristSubsystem wristSubsystem;

    public WristStow(WristSubsystem subsystem) {
        this.wristSubsystem = subsystem;
        addRequirements(subsystem);
    }

    public void initialize() {
        this.timer.start();
        this.wristSubsystem.setWristStow();
    }

    public boolean isFinished() {
        return this.timer.done();
    }
}
