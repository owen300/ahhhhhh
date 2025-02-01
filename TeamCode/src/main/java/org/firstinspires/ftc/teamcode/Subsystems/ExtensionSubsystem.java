package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ExtensionSubsystem extends SubsystemBase {
    public static double ACCEPTABLE_POSITION_TOLERANCE = 20.0d;
    public static final int BACKBOARD_POSITION_INCREMENT = 20;
    public static int LOW_POSISTION = 0;
    public static double TOLERANCE_PID = 2;
    private static double kD = 0.00008d;
    private static double kF = 0.0d;
    private static double kI = 0.00001d;
    private static double kP = 0.008d;
    public static State state = State.deposit;
    private MotorGroup extension;
    private Motor extension_bottom;
    private Motor extension_top;
    private PIDFController pidf;
    private double target = 0.0d;
    Telemetry telemetry;

    public enum State {
        deposit,
        intake
    }

    public static int UNEXTENDED_POSITION() {
        if (state.equals(State.deposit)) {
            return -63;
        }
        return 0;
    }

    public ExtensionSubsystem(HardwareMap hMap, Telemetry telemetry2) {
        PIDFController pIDFController = new PIDFController(kP, kI, kD, kF);
        this.pidf = pIDFController;
        pIDFController.setTolerance(TOLERANCE_PID);
        this.extension_top = new Motor(hMap, "extension_motor_1");
        Motor motor = new Motor(hMap, "extension_motor_2");
        this.extension_bottom = motor;
        motor.setInverted(true);
        this.extension_top.setInverted(true);
        MotorGroup motorGroup = new MotorGroup(this.extension_top, this.extension_bottom);
        this.extension = motorGroup;
        this.telemetry = telemetry2;
        motorGroup.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        this.extension.setRunMode(Motor.RunMode.RawPower);
    }

    public void init() {
        this.extension_top.resetEncoder();
    }

    public int getCurrentPosition() {
        return this.extension_top.getCurrentPosition();
    }

    public void goToPosition(int targetPosition) {
        this.pidf.setSetPoint((double) targetPosition);
        this.target = (double) targetPosition;
        if (targetPosition == 1058 || targetPosition == 37 || targetPosition == 0) {
            state = State.deposit;
        } else if (targetPosition == 0 || targetPosition == 0) {
            state = State.intake;
        }
    }

    public void incrementUp() {
        if (this.extension_bottom.getCurrentPosition() + 50 <= 654) {
            this.pidf.setSetPoint((double) (this.extension_bottom.getCurrentPosition() + 50));
            this.target = this.pidf.getSetPoint();
            return;
        }
        this.pidf.setSetPoint(654.0d);
        this.target = 654.0d;
    }

    public void incrementDown() {
        if (this.extension_bottom.getCurrentPosition() - 50 >= UNEXTENDED_POSITION()) {
            this.pidf.setSetPoint((double) (this.extension_bottom.getCurrentPosition() - 50));
            this.target = this.pidf.getSetPoint();
            return;
        }
        this.pidf.setSetPoint((double) UNEXTENDED_POSITION());
        this.target = (double) UNEXTENDED_POSITION();
    }

    public boolean atTargetPosition() {
        return Math.abs(this.pidf.getSetPoint() - ((double) getCurrentPosition())) < ACCEPTABLE_POSITION_TOLERANCE;
    }

    public void manualControl(double joystick) {
        if (getCurrentPosition() > UNEXTENDED_POSITION() && getCurrentPosition() < 654) {
            this.extension.set(joystick);
        }
    }

    public void periodic() {
        callTelemetry();
        if (!atTargetPosition()) {
            this.extension.set(this.pidf.calculate((double) getCurrentPosition(), this.target));
        } else {
            this.extension.set(0.0d);
        }
    }

    public void callTelemetry() {
        this.telemetry.addData("Extension Position", (Object) Integer.valueOf(getCurrentPosition()));
        this.telemetry.addData("Extension Target Position", (Object) Double.valueOf(this.pidf.getSetPoint()));

    }
}
