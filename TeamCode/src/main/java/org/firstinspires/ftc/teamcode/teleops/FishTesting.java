 package org.firstinspires.ftc.teamcode.teleops;

 import org.firstinspires.ftc.robotcore.external.Telemetry;
 import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
 import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
 import org.firstinspires.ftc.teamcode.OD.FishDetector;

 import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
 import com.qualcomm.robotcore.eventloop.opmode.OpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 import com.acmerobotics.dashboard.FtcDashboard;

 import org.firstinspires.ftc.teamcode.Robot.Drivetrain;
 import org.firstinspires.ftc.teamcode.controller.Controller;
 import org.firstinspires.ftc.teamcode.math_utils.Vector;
 import org.firstinspires.ftc.teamcode.odom.OpticalSensor;

 @TeleOp(name="Fish Testing (GamePad)", group = "Howard")
 public class FishTesting extends OpMode
 {

    FishDetector fd;
    OpticalSensor od;

    FtcDashboard dashboard;

     Controller controller;
     Drivetrain drivetrain;

     /*
      * Code to run ONCE when the driver hits INIT
      */
     @Override
     public void init() {
         dashboard = FtcDashboard.getInstance();
         Telemetry dashboardTelemetry = dashboard.getTelemetry();
         fd  = new FishDetector(hardwareMap);
         od = new OpticalSensor("otos", hardwareMap, DistanceUnit.METER, AngleUnit.RADIANS);

         drivetrain = new Drivetrain(hardwareMap);
         controller = new Controller(gamepad1);

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

         controller.update();
         Vector drive = controller.leftStick.toVector();
         drivetrain.driveLimited(drive, controller.rightStick.x.value() * 0.5, 2, od);



         TelemetryPacket canvasPacket = new TelemetryPacket(false);
         canvasPacket.fieldOverlay()
                 //background
                 .setFill("black")
                 .fillRect(-20, -20, 40, 40)

                 //odom coords
                 .setFill("green")
                 .fillCircle(od.getPosition().x, od.getPosition().y, 0.5)

                 //odom label
                 .setFill("white")
                 .fillText("Odometry Position", od.getPosition().x + 1, od.getPosition().y + 1, "Sans Serif", 0)

                 //odom line
                 .setStroke("white")
                 .setStrokeWidth(2)
                 .strokeLine(0, 0, od.getPosition().x, od.getPosition().y);

         dashboard.sendTelemetryPacket(canvasPacket);
     }

     /*
      * Code to run ONCE after the driver hits STOP
      */
     @Override
     public void stop() {

         fd.quit();

     }
 }