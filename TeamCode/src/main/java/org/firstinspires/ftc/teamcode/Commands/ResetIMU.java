package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

public class ResetIMU extends CommandBase {
    private final DriveSubsystem drive;

    public ResetIMU(DriveSubsystem subsystem) {
        this.drive = subsystem;
        addRequirements(subsystem);
    }

    public void initialize() {
        this.drive.resetIMU();
    }

    public boolean isFinished() {
        return true;
    }
}
