 package org.firstinspires.ftc.teamcode.teleops;

 import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
 import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
 import org.firstinspires.ftc.teamcode.OD.FishDetector;
 import com.qualcomm.robotcore.eventloop.opmode.OpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

 import org.firstinspires.ftc.teamcode.odom.OpticalSensor;

 @TeleOp(name="FISH TESTING", group = "Howard")
 public class FishTesting extends OpMode
 {

    FishDetector fd;
    OpticalSensor od;

     /*
      * Code to run ONCE when the driver hits INIT
      */
     @Override
     public void init() {

         fd  = new FishDetector(hardwareMap);
         od = new OpticalSensor("otos", hardwareMap, DistanceUnit.METER, AngleUnit.RADIANS);
     }
  
     /*
      * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
      */
     @Override
     public void loop() {
         fd.update();
         od.update();
         telemetry.addData("FISH", fd.getFishCoords());
         telemetry.addData("Calculated", fd.getXYInput());
         telemetry.addData("Odometry Coords", od.getPosition().x + "," + od.getPosition().y);
         telemetry.addData("Odometry Angle", od.getPosition().angle());
     }
 
     /*
      * Code to run ONCE after the driver hits STOP
      */
     @Override
     public void stop() {

         fd.quit();
         od.quit();

     }
 }