package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

public class DriveRobotCentric extends CommandBase {
    private final double SLOW_MODE_SPEED = 0.5d;
    private final DriveSubsystem drive;
    private DoubleSupplier rx;
    private BooleanSupplier slowMode;
    private DoubleSupplier x;
    private DoubleSupplier y;

    public DriveRobotCentric(DriveSubsystem subsystem, DoubleSupplier y2, DoubleSupplier x2, DoubleSupplier rx2, BooleanSupplier slowMode2) {
        this.y = y2;
        this.x = x2;
        this.rx = rx2;
        this.slowMode = slowMode2;
        this.drive = subsystem;
        addRequirements(subsystem);
    }

    public void execute() {
        if (this.slowMode.getAsBoolean()) {
            this.drive.runRobotCentric(this.y.getAsDouble(), this.x.getAsDouble(), this.rx.getAsDouble(), 0.5d);
        } else {
            this.drive.runRobotCentric(this.y.getAsDouble(), this.x.getAsDouble(), this.rx.getAsDouble(), 1.0d);
        }
    }
}
