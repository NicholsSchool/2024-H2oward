package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.teamcode.OD.FishDetector;
import org.firstinspires.ftc.teamcode.Robot.Drivetrain;
import org.firstinspires.ftc.teamcode.math_utils.Point;
import org.firstinspires.ftc.teamcode.math_utils.Vector;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="FISH DRIVE", group = "Howard")
public class FishTeleop extends OpMode
{
   FishDetector fd;
   Drivetrain drivetrain;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
       fd = new FishDetector(hardwareMap);
       drivetrain = new Drivetrain(hardwareMap);
    }
 
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
      fd.update();
      telemetry.addData("Fish Location", fd.getFishCoords());
      Point fishInput = fd.getXYInput();
      double procInputX = (fishInput.x - 540)/540;
      double procInputY = (-fishInput.y + 540)/540;
      telemetry.addData("Processed Drive Vector", new double[]{procInputX, procInputY});
      Vector driveVec = new Vector(procInputX, procInputY);
      drivetrain.drive(driveVec, 0.0);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        fd.quit();
    }
}
