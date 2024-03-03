package org.firstinspires.ftc.teamcode.controller;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * A Controller Axis
 */
public class Axis {
    private double value;

    /**
     * Instantiates the Button
     */
    public Axis() {}

    /**
     * The Axis's current value
     *
     * @return the current value
     */
    public double value() {
        return value;
    }

    /**
     * The Axis as a String to three decimal places
     *
     * @return whether the Axis is pressed
     */
    @NonNull
    public String toString() {
        return String.format(Locale.US, "%.3f", value);
    }

    /**
     * Updates the Axis
     *
     * @param newValue the new value
     */
    public void update(double newValue) {
        if(Math.abs(newValue) >= 0.01)
            value = newValue;
        else
            value = 0.0;
    }
}