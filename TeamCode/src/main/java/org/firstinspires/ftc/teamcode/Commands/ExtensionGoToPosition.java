package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ExtensionSubsystem;

public class ExtensionGoToPosition extends CommandBase {
    public static final int HIGH_BUCKET_POS = 1030;
    public static final int INTAKE = 50;
    public static final int INTAKE_FAR = 654;
    public static final int LOW_BUCKET_POS = 37;
    public static final int ONE_STAGE_EXTENSION = 400;
    public static final int SPEC = 25;
    public static final int STOW_POSITION = 50;
    private final ExtensionSubsystem extensionSubsystem;
    private final int targetPosition;

    public ExtensionGoToPosition(ExtensionSubsystem subsystem, int targetPosition2) {
        this.extensionSubsystem = subsystem;
        this.targetPosition = targetPosition2;
        addRequirements(subsystem);
    }

    public void execute() {
        this.extensionSubsystem.goToPosition(this.targetPosition);
    }

    public boolean isFinished() {
        return this.extensionSubsystem.atTargetPosition();
    }
}
