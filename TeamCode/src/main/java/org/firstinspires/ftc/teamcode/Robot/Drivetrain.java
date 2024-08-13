package org.firstinspires.ftc.teamcode.Robot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.math_utils.AngleMath;
import org.firstinspires.ftc.teamcode.math_utils.MotionProfile;
import org.firstinspires.ftc.teamcode.math_utils.Vector;
import org.firstinspires.ftc.teamcode.math_utils.VectorMotionProfile;
import org.firstinspires.ftc.teamcode.odom.OpticalSensor;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Robot Drivetrain
 */
public class Drivetrain implements DrivetrainConstants {
    private final DcMotorEx leftDrive, rightDrive, backDrive;
    private final VectorMotionProfile driveProfile;
    private final MotionProfile turnProfile;

    /**
     * Initializes the Drivetrain
     *
     * @param hwMap the hardwareMap
     */
    public Drivetrain(HardwareMap hwMap) {
        leftDrive = hwMap.get(DcMotorEx.class, "frontLeft");
        rightDrive = hwMap.get(DcMotorEx.class, "frontRight");
        backDrive = hwMap.get(DcMotorEx.class, "back");

        leftDrive.setDirection(DcMotorEx.Direction.REVERSE); //TODO: check
        rightDrive.setDirection(DcMotorEx.Direction.REVERSE);
        backDrive.setDirection(DcMotorEx.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        rightDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        backDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        leftDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        leftDrive.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);
        rightDrive.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);
        backDrive.setVelocityPIDFCoefficients(DRIVE_P, DRIVE_I, 0.0, 0.0);

        driveProfile = new VectorMotionProfile(DRIVE_PROFILE_SPEED);
        turnProfile = new MotionProfile(TURN_PROFILE_SPEED, TURN_PROFILE_MAX);
    }

    /**
     * Drives the robot field oriented
     *
     * @param driveInput the (x, y) input
     * @param turn the turning input
     */
    public void drive(Vector driveInput, double turn) {
        turn = turnProfile.calculate(turn);

        driveInput = driveProfile.calculate(
                driveInput.clipMagnitude(MAX_SPEED - Math.abs(turn)));
        double power = driveInput.magnitude();
        double angle = driveInput.angle() + Math.PI / 2;

        leftDrive.setPower(turn + power * Math.cos(angle + LEFT_DRIVE_OFFSET - CAMERA_OFFSET));
        rightDrive.setPower(turn + power * Math.cos(angle + RIGHT_DRIVE_OFFSET - CAMERA_OFFSET));
        backDrive.setPower(turn + power * Math.cos(angle + BACK_DRIVE_OFFSET - CAMERA_OFFSET));
    }

    /**
     * Drives the robot with Virtual Fence Enabled.
     * @param driveInput same as drive()
     * @param turn same as drive()
     * @param fenceRadius Fence Radius in Meters (how far the robot can go from origin)
     * @param od OpticalSensor instance to use for Virtual Fence
     */
    public void driveLimited(Vector driveInput, double turn, double fenceRadius, OpticalSensor od) {

        od.update();
        double minBlockedAngle = AngleMath.addAnglesRadians(od.getPosition().angle(), -Math.PI / 2);
        double maxBlockedAngle = AngleMath.addAnglesRadians(od.getPosition().angle(), Math.PI / 2);
        boolean canDrive = od.getPosition().magnitude() < fenceRadius && driveInput.angle() < minBlockedAngle && driveInput.angle() > maxBlockedAngle;

        if (canDrive) {
            drive(driveInput, turn);
        } else {
            drive(new Vector(0, 0), 0);
        }

    }

    /**
     * Get Motor Velocities
     *
     * @return the left, right, back velocities
     */
    public double[] getMotorVelocities() {
        return new double[]{
                leftDrive.getVelocity(),
                rightDrive.getVelocity(),
                backDrive.getVelocity(),
        };
    }
}