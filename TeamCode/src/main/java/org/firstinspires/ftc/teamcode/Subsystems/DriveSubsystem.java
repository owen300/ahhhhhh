package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveSubsystem extends SubsystemBase {
    private static IMU imu;
    private final DcMotor leftFront;
    private final DcMotor leftRear;
    private double offset = 0.0d;
    private final DcMotor rightFront;
    private final DcMotor rightRear;

    public DriveSubsystem(HardwareMap hardwareMap) {
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        imu = (IMU) hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.DOWN, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD)));
        DcMotor dcMotor = (DcMotor) hardwareMap.get(DcMotor.class, "rightRear");
        this.rightFront = dcMotor;
        DcMotor dcMotor2 = (DcMotor) hardwareMap.get(DcMotor.class, "rightFront");
        this.rightRear = dcMotor2;
        DcMotor dcMotor3 = (DcMotor) hardwareMap.get(DcMotor.class, "leftRear");
        this.leftFront = dcMotor3;
        DcMotor dcMotor4 = (DcMotor) hardwareMap.get(DcMotor.class, "leftFront");
        this.leftRear = dcMotor4;
        dcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotor3.setDirection(DcMotorSimple.Direction.REVERSE);
        dcMotor4.setDirection(DcMotorSimple.Direction.REVERSE);
        dcMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcMotor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dcMotor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void init() {
        imu.resetYaw();
    }

    public double heading() {
        return AngleUnit.normalizeRadians(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) - this.offset);
    }

    public void resetIMU() {
        this.offset = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void runRobotCentric(double y, double x, double rx, double topSpeed) {
        run(y, x, rx, 0.0d, topSpeed);
    }

    public void runFeildCentric(double y, double x, double rx, double topSpeed) {
        run(y, x, rx, heading(), topSpeed);
    }

    private void run(double y, double x, double rx, double botHeading, double topSpeed) {
        double d = botHeading;
        double d2 = topSpeed;
        double x2 = 1.1d * x;
        double y2 = y * y * y;
        double x3 = x2 * x2 * x2;
        double rx2 = rx * rx * rx;
        double rotX = (Math.cos(-d) * x3) - (Math.sin(-d) * y2);
        double rotY = (Math.sin(-d) * x3) + (Math.cos(-d) * y2);
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx2), 1.0d);
        double LeftFrontPower = Range.scale(((rotY + rotX) + rx2) / denominator, -1.0d, 1.0d, -d2, topSpeed);
        double LeftFrontPower2 = LeftFrontPower;
        double LeftRearPower = Range.scale(((rotY - rotX) + rx2) / denominator, -1.0d, 1.0d, -d2, topSpeed);
        double LeftRearPower2 = LeftRearPower;
        double d3 = -d2;
        double RightRearPower = Range.scale(((rotY + rotX) - rx2) / denominator, -1.0d, 1.0d, d3, topSpeed);
        this.leftFront.setPower(LeftFrontPower2);
        this.leftRear.setPower(LeftRearPower2);
        this.rightFront.setPower(Range.scale(((rotY - rotX) - rx2) / denominator, -1.0d, 1.0d, -d2, topSpeed));
        this.rightRear.setPower(RightRearPower);
    }
}
