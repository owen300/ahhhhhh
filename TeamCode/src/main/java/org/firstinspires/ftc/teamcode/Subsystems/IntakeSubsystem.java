package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class IntakeSubsystem extends SubsystemBase {
    public static final double DETECTION_DISTANCE = 15.0d;
    private static ColorSensor color;
    private static DistanceSensor dist;
    private static CRServo intake;
    private static CRServo intake1;
    private final Telemetry telemetry;

    public IntakeSubsystem(HardwareMap hMap, Telemetry telemetry) {
        this.telemetry=telemetry;
        intake = (CRServo) hMap.get(CRServo.class, "intake");
        intake1 = (CRServo) hMap.get(CRServo.class, "intake1");
        dist = (DistanceSensor) hMap.get(DistanceSensor.class, "color");
        color = (ColorSensor) hMap.get(ColorSensor.class, "color");
    }

    public void intake() {
        intake.setPower(1.0d);
        intake1.setPower(-1.0d);
    }

    public void stop() {
        intake.setPower(0.0d);
        intake1.setPower(0.0d);
    }

    public void outtake() {
        intake.setPower(-0.5);
        intake1.setPower(0.5);
    }

    public boolean detected() {
        return dist.getDistance(DistanceUnit.MM) <= 15.0d;
    }
    public boolean detectedSpec() {
        return dist.getDistance(DistanceUnit.MM) <= 40.0d;
    }

    @Override
    public void periodic(){
        telemetry.addData("dist",dist.getDistance(DistanceUnit.MM));

    }

    public String colorSeen() {
        int r = color.red();
        int blue = color.blue();
        int g = color.green();
        if (r > 200 && g > 200) {
            return "yellow";
        }
        if (r > 200) {
            return "red";
        }
        return "blue";
    }
}
