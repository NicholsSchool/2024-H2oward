package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.OD.FishDetector;
import org.firstinspires.ftc.teamcode.Robot.Drivetrain;
import org.firstinspires.ftc.teamcode.controller.Controller;
import org.firstinspires.ftc.teamcode.gobilda_pinpoint.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.math_utils.Point;
import org.firstinspires.ftc.teamcode.math_utils.Vector;
import org.firstinspires.ftc.teamcode.odom.OpticalSensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Fish Auto Fenced", group = "Howard")
public class FishTeleop extends OpMode
{
   FishDetector fd;
   Drivetrain drivetrain;
   OpticalSensor od;
   GoBildaPinpointDriver pinpoint;
   Controller controller;

   private double fenceRadius = 1.0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
       fd = new FishDetector(hardwareMap);
       pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
       drivetrain = new Drivetrain(hardwareMap);
       controller = new Controller(gamepad1);
       pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
       pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
       pinpoint.setOffsets(0, 0, DistanceUnit.METER);
    }
 
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
      fd.update();
      pinpoint.update();
      if (controller.dpadDown.wasJustPressed()) {
          fenceRadius -= 0.1;
      }

      if (controller.dpadUp.wasJustPressed()) {
          fenceRadius += 0.1;
      }

      Vector driveVec = new Vector(0.5 * fd.getXYInput().x, 0.5 * fd.getXYInput().y);
      drivetrain.driveLimited(driveVec, 0.0, fenceRadius, pinpoint.getPosition());
      telemetry.addData("Fence Radius (DPad to adjust)", fenceRadius + "m");
      telemetry.addData("pos", pinpoint.getPosition());
        telemetry.addData("Odometry Coords", pinpoint.getPosX(DistanceUnit.METER) + "," + pinpoint.getPosY(DistanceUnit.METER));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        fd.quit();
    }
}
