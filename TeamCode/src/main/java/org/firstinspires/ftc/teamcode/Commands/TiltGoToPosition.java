package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.TiltSubsystem;

public class TiltGoToPosition extends CommandBase {
    public static double STOW = 0.0d;
    public static double TELEOP_BUCKETH = 90.5d;
    public static double hang = 105;
    public static double TELEOP_INTAKE = 0.0d;
    public static double TELEOP_SP = 84.0d;
    public static double TELEOP_SPI = 34.5d;
    private final double targetAngle;
    private final TiltSubsystem tiltSubsystem;

    public TiltGoToPosition(TiltSubsystem subsystem, double targetAngle2) {
        this.tiltSubsystem = subsystem;
        this.targetAngle = targetAngle2;
        addRequirements(subsystem);
    }

    public void initialize() {
        this.tiltSubsystem.setTargetAngle(this.targetAngle);
    }

    public boolean isFinished() {
        return this.tiltSubsystem.atTargetPosition();
    }
}
