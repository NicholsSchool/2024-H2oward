package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Robot Drivetrain
 */
public class Drivetrain {
    private final DcMotorEx front, backLeft, backRight;
    private final AHRS navx;

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
        front = hwMap.get(DcMotorEx.class, "front");
        backRight = hwMap.get(DcMotorEx.class, "backRight");
        backLeft = hwMap.get(DcMotorEx.class, "backLeft");

        front.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);

        front.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        front.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        front.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        //TODO: pid if needed
        // front.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);
        // backRight.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);
        // backLeft.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);

        navx = AHRS.getInstance(hwMap.get(NavxMicroNavigationSensor.class,
                "navx"), AHRS.DeviceDataType.kProcessedData);
        navx.zeroYaw();
    }

    /**
     * Drives the robot field oriented
     *
     * @param driveInput the (x, y) input
     * @param turn the turning input
     * @param autoAlign whether to autoAlign
     * @param lowGear whether to put the robot to virtual low gear
     */
    public void drive(Vector driveInput, double turn, boolean autoAlign, boolean lowGear) {
        turn = turnProfile.calculate(autoAlign ? turnToAngle() : turn);

        driveInput = driveProfile.calculate(driveInput.clipMagnitude(
                (lowGear ? VIRTUAL_LOW_GEAR : VIRTUAL_HIGH_GEAR) - Math.abs(turn)));
        double power = driveInput.magnitude();
        double angle = driveInput.angle();

        front.setPower(turn + power * Math.cos(angle + LEFT_DRIVE_OFFSET - pose.angle));
        backRight.setPower(turn + power * Math.cos(angle + RIGHT_DRIVE_OFFSET - pose.angle));
        backLeft.setPower(turn + power * Math.cos(angle + BACK_DRIVE_OFFSET - pose.angle));
    }

    /**
     * Get Motor Velocities
     *
     * @return the left, right, back velocities
     */
    public double[] getMotorVelocities() {
        return new double[]{
                front.getVelocity(),
                backRight.getVelocity(),
                backLeft.getVelocity(),
        };
    }
}