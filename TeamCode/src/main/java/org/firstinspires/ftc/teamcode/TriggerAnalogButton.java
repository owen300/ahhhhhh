package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class TriggerAnalogButton extends Button {
    private GamepadEx gamepad;
    private double threshold;
    private GamepadKeys.Trigger trigger;

    public TriggerAnalogButton(GamepadEx gamepad2, GamepadKeys.Trigger trigger2, double threshold2) {
        this.threshold = threshold2;
        this.trigger = trigger2;
        this.gamepad = gamepad2;
    }

    public boolean get() {
        return this.gamepad.getTrigger(this.trigger) > this.threshold;
    }
}
