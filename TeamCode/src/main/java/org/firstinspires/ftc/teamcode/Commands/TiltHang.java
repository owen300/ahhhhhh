package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.TiltSubsystem;

public class TiltHang extends CommandBase {
    public static double POWER = -1;
    private final TiltSubsystem tiltSubsystem;

    public TiltHang(TiltSubsystem subsystem) {
        this.tiltSubsystem = subsystem;
        addRequirements(subsystem);
    }

    public void initialize() {
        tiltSubsystem.setPower(POWER);
    }

    @Override
    public void end(boolean interrupted) {
        tiltSubsystem.setPower(POWER);
    }
}
