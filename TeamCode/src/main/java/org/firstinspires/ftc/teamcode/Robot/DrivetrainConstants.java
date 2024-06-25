package org.firstinspires.ftc.teamcode.Robot;

/**
 * Constants for the Drivetrain
 */
public interface DrivetrainConstants {
    //TODO: tune everything

    /** The Maximum Speed of the Driving Profile */
    double DRIVE_PROFILE_SPEED = 0.50;

    /** The Maximum Speed of the Turning Profile */
    double TURN_PROFILE_SPEED = 1.0;

    /** The Maximum Output Value Magnitude of the Turning Profile */
    double TURN_PROFILE_MAX = 0.25;

    /** The maximum drive input */
    double MAX_SPEED = 0.75;

    /** Left Drive Wheel Angle Offset (30 degrees) */
    double LEFT_DRIVE_OFFSET = Math.PI / 6.0;

    /** Right Drive Wheel Angle Offset (150 degrees) */
    double RIGHT_DRIVE_OFFSET = 5.0 * Math.PI / 6.0;

    /** Back Drive Wheel Angle Offset (270 degrees) */
    double BACK_DRIVE_OFFSET = 1.5 * Math.PI;

    double CAMERA_OFFSET = 0;

    /** Drive Motor Proportional Gain */
    double DRIVE_P = 16.0;

    /** Drive Motor Integral Gain */
    double DRIVE_I = 6.0;

    /** The Maximum Ticks per second velocity of 40:1 Hex Motors */
    double MAX_MOTOR_VELOCITY = 2800.0;
}