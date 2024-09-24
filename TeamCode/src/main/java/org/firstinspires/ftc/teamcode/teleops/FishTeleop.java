package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.OD.FishDetector;
import org.firstinspires.ftc.teamcode.Robot.Drivetrain;
import org.firstinspires.ftc.teamcode.math_utils.Point;
import org.firstinspires.ftc.teamcode.math_utils.Vector;
import org.firstinspires.ftc.teamcode.odom.OpticalSensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Fish Auto 1m Fence", group = "Howard")
public class FishTeleop extends OpMode
{
   FishDetector fd;
   Drivetrain drivetrain;
   OpticalSensor od;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
       fd = new FishDetector(hardwareMap);
       od = new OpticalSensor("otos", hardwareMap, DistanceUnit.METER, AngleUnit.RADIANS);
       drivetrain = new Drivetrain(hardwareMap);
    }
 
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
      fd.update();
      //od.update is called in driveLimited.
      Vector driveVec = new Vector(0.5 * fd.getXYInput().x, 0.5 * fd.getXYInput().y);
      drivetrain.driveLimited(driveVec, 0.0, 1.0, od);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        fd.quit();
    }
}
