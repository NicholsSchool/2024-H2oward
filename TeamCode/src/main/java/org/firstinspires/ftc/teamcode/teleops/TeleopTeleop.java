package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.teamcode.Robot.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.DrivetrainConstants;
import org.firstinspires.ftc.teamcode.controller.Controller;
import org.firstinspires.ftc.teamcode.math_utils.Vector;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Teleop teleop", group = "Teleop")
public class TeleopTeleop extends OpMode implements DrivetrainConstants
{
    Controller controller;
    Drivetrain drivetrain;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        drivetrain = new Drivetrain(hardwareMap);
        controller = new Controller(gamepad1);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        controller.update();
        Vector drive = controller.leftStick.toVector();
        drive.scaleMagnitude(MAX_SPEED);

        drivetrain.drive(drive, controller.rightStick.x.value() * 0.5);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
