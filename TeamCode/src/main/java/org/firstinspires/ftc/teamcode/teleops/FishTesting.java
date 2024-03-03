 package org.firstinspires.ftc.teamcode.teleops;

 import org.firstinspires.ftc.teamcode.OD.FishDetector;
 import com.qualcomm.robotcore.eventloop.opmode.OpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

 @TeleOp(name="FISH TESTING", group = "Howard")
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
        telemetry.addData("FISH", fd.getXYInput());
     }
 
     /*
      * Code to run ONCE after the driver hits STOP
      */
     @Override
     public void stop() {
         fd.quit();
     }
 }