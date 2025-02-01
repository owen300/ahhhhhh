package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class WristSubsystem extends SubsystemBase {
    private static final double WRISTURN_DEPOSIT = 0.4d;
    private static final double WRISTURN_INTAKE = 0.52d;
    private static final double WRISTURN_SAMPLE = 0.5d;
    private static final double WRISTURN_STOW = 0.6d;
    private static final double WRIST_DEPOSIT = 0.6d;
    private static final double WRIST_INTAKE = 0.48d;
    private static final double WRIST_SAMPLE = 0.5d;
    private static final double WRIST_STOW = 0.4d;
    private static Telemetry telemetry;
    private static Servo wristServo;
    private static Servo wristurnServo;

    public WristSubsystem(HardwareMap hMap) {
        wristServo = (Servo) hMap.get(Servo.class, "wrist");
        wristurnServo = (Servo) hMap.get(Servo.class, "wristurn");
    }

    public void setWristIntake(boolean sample) {
        wristServo.setPosition(sample ? WRIST_INTAKE: 0.75);
        wristurnServo.setPosition(sample ? WRISTURN_INTAKE : 0.24);
    }
    public void setWristScore() {
        wristServo.setPosition(0.77);
        wristurnServo.setPosition(0.26);
    }

    public void setWristDeposit() {
        wristServo.setPosition(0.52);
        wristurnServo.setPosition(0.5d);
    }

    public static double getWristPosition() {
        return wristServo.getPosition();
    }

    public static double getWristTurnPosition() {
        return wristurnServo.getPosition();
    }

    public void setWristStow() {
        wristServo.setPosition(0.20d);
        wristurnServo.setPosition(0.8);
    }

    public void setWristSample() {
        wristServo.setPosition(0.44d+0.05d);
        wristurnServo.setPosition(0.56d-0.05d);
    }
}
