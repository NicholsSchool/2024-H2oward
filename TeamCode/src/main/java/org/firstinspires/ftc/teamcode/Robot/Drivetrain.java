package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Robot Drivetrain
 */
public class Drivetrain {
    private final DcMotorEx back, frontLeft, frontRight;

    /**
     * Initializes the Drivetrain subsystem
     *
     * @param hwMap the hardwareMap
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param initialHeading the initial robot heading in radians
     * @param isBlue whether we are blue alliance
     */
    public Drivetrain(HardwareMap hwMap) {
        back = hwMap.get(DcMotorEx.class, "back");
        frontRight = hwMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hwMap.get(DcMotorEx.class, "frontLeft");

        back.setDirection(DcMotorEx.Direction.FORWARD);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        frontLeft.setDirection(DcMotorEx.Direction.FORWARD);

        back.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        back.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        back.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        //TODO: pid if needed
        // back.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);
        // frontRight.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);
        // frontLeft.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);
    }


    public void drive(double power, double angle) {

        //TODO: Math for drive method

    }
}