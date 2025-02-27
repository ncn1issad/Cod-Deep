package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.intake.Claw;

import java.util.function.Supplier;

/**
 * A delegate for toggle buttons.
 * <p>
 * When the button is pressed, the state will toggle between `true` and `false` on each press.
 */
public class TogglePress {
    private final SinglePress singlePress;
    private boolean state = false;

    /**
     * Creates a new TogglePress delegate.
     * @param getState a supplier for the button state.
     */
    public TogglePress(Supplier<Boolean> getState) {
        singlePress = new SinglePress(getState);
    }

    /**
     * Gets the state of the toggle.
     * @return true if the toggle is on, false otherwise.
     */
    public boolean isToggled() {
        if (singlePress.isPressed()) state = !state;
        return state;
    }
}

/**
 * TeleOp class for testing the TogglePress delegate.
 */
@TeleOp(name = "Claw Toggle Press Test", group = "E")
@Disabled
class ClawTogglePressTest extends OpMode {
    Claw claw;
    private final TogglePress togglePress = new TogglePress(() -> gamepad1.a);

    @Override
    public void init() {
        claw = new Claw(hardwareMap);
        telemetry.addData("Claw Toggle Press Test", "Press A to toggle the claw");
        telemetry.update();
    }

    @Override
    public void loop() {
        claw.set(togglePress.isToggled());
    }
}
