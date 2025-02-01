package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class TiltSubsystem extends SubsystemBase {
    private static double ACCEPTABLE_POSITION_TOLERANCE_DEGREES = 20.0d;
    private static int HORIZONTAL_ENCODER_VALUE = 0;
    private static double KF = 0.2d;
    private static double KI = 0.0d;
    public static double KP = 0.004d;
    public static double PID_SPEED = 0.6d;
    private static double TICKS_IN_DEGREE = 7.96592592592;
    private static double TOLERANCE_PID = 1.0d;
    private static double extensionConstant = 0.0d;
    private static double kD = 1.0E-4d;
    private PIDController pid = new PIDController(KP, KI, kD);
    private static double kV=0.1;
    private double targetAngle = 0.0d;
    Telemetry telemetry;
    private MotorGroup tilt;
    private Motor tilt_motor;
    private Motor tilt_motor2;
    private VoltageSensor volts;

    public TiltSubsystem(HardwareMap hMap, Telemetry telemetry2) {
        volts=hMap.get(VoltageSensor.class, "Control Hub");
        this.telemetry = telemetry2;
        this.tilt_motor = new Motor(hMap, "tilt");
        this.tilt_motor2 = new Motor(hMap, "tilt2");
        this.tilt_motor.setInverted(true);
        this.tilt_motor2.setInverted(true);
        this.tilt = new MotorGroup(this.tilt_motor, this.tilt_motor2);
        this.pid.setTolerance(TOLERANCE_PID);
        this.tilt.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        this.tilt.setRunMode(Motor.RunMode.RawPower);
    }

    public void init() {
        this.tilt_motor.resetEncoder();
    }

    public void setTargetAngle(double targetAngle2) {
        this.targetAngle = targetAngle2;
    }

    public boolean atTargetPosition() {
        return Math.abs(this.targetAngle - toAngle(this.tilt_motor.getCurrentPosition())) < ACCEPTABLE_POSITION_TOLERANCE_DEGREES;
    }

    private int toEncoder(double angle) {
        return (int) ((TICKS_IN_DEGREE * angle) + ((double) HORIZONTAL_ENCODER_VALUE));
    }

    private double toAngle(int encoderValue) {
        return ((double) (encoderValue - HORIZONTAL_ENCODER_VALUE)) / TICKS_IN_DEGREE;
    }

    private double toAngleFeedforward(int encoderValue) {
        return ((double) encoderValue) / TICKS_IN_DEGREE;
    }

    public void periodic() {
        int currentPos = this.tilt_motor.getCurrentPosition();
        double currentAngle = toAngle(currentPos);
        int targetPosition = toEncoder(this.targetAngle);
        double ffOutput = KF * Math.cos(Math.toRadians(currentAngle));
        this.pid.setSetPoint((double) targetPosition);
        double pidOutput = this.pid.calculate((double) currentPos, (double) targetPosition) * PID_SPEED;
        double output = ffOutput + pidOutput;
        this.telemetry.addData("ff: ", (Object) Double.valueOf(ffOutput));
        this.telemetry.addData("pid: ", (Object) Double.valueOf(pidOutput));
        this.telemetry.addData("Arm Angle: ", (Object) Double.valueOf(currentAngle));
        this.telemetry.addData("- output: ", (Object) Double.valueOf(output));
        this.telemetry.addData("radians", (Object) Double.valueOf(Math.toRadians(currentAngle)));
        //if(volts.getVoltage()<13.5)output = output * (13.5/getVoltage) * kV;
        tilt.set(output);
        callTelemetry();
    }

    private void callTelemetry() {
        this.telemetry.addData("Encoder Position: ", (Object) Integer.valueOf(this.tilt_motor.getCurrentPosition()));
        this.telemetry.addData("PID Target: ", (Object) Double.valueOf(this.pid.getSetPoint()));
    }
}
