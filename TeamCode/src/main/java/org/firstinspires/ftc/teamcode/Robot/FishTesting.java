 package org.firstinspires.ftc.teamcode.Robot;

 import org.firstinspires.ftc.teamcode.OD.FishDetector;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
 import com.qualcomm.robotcore.eventloop.opmode.OpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

 @TeleOp(name="FISH TESTING")
 public class FishTesting extends OpMode
 {

    FishDetector fd;

     /*
      * Code to run ONCE when the driver hits INIT
      */
     @Override
     public void init() {
        fd  = new FishDetector(hardwareMap);
     }
  
     /*
      * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
      */
     @Override
     public void loop() {
         fd.update();
         telemetry.addData("FISH_X", fd.getXYInput()[0]);
         telemetry.addData("FISH_Y", fd.getXYInput()[1]);
     }
 
     /*
      * Code to run ONCE after the driver hits STOP
      */
     @Override
     public void stop() {
     }
 
 }
 