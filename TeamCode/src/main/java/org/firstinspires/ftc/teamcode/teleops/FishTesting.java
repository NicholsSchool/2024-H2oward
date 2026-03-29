 package org.firstinspires.ftc.teamcode.teleops;

 import org.firstinspires.ftc.robotcore.external.Telemetry;
 import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
 import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
 import org.firstinspires.ftc.teamcode.OD.FishDetector;

 import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
 import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
 import com.qualcomm.robotcore.eventloop.opmode.OpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 import com.acmerobotics.dashboard.FtcDashboard;

 import org.firstinspires.ftc.teamcode.Robot.Drivetrain;
 import org.firstinspires.ftc.teamcode.controller.Controller;
 import org.firstinspires.ftc.teamcode.gobilda_pinpoint.GoBildaPinpointDriver;
 import org.firstinspires.ftc.teamcode.math_utils.Vector;
 import org.firstinspires.ftc.teamcode.odom.OpticalSensor;

 @TeleOp(name="Fish Testing (GamePad)", group = "Howard")
 public class FishTesting extends OpMode
 {

    FishDetector fd;
    FtcDashboard dashboard;

    Controller controller;
    Drivetrain drivetrain;
    GoBildaPinpointDriver pinpoint;

     double fenceRadius = 1.0;

     /*
      * Code to run ONCE when the driver hits INIT
      */
     @Override
     public void init() {
         dashboard = FtcDashboard.getInstance();
         Telemetry dashboardTelemetry = dashboard.getTelemetry();
         telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);
         fd  = new FishDetector(hardwareMap);
         drivetrain = new Drivetrain(hardwareMap);
         controller = new Controller(gamepad1);

         pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
         pinpoint.recalibrateIMU();
         pinpoint.resetPosAndIMU();
         pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
         pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED);
         pinpoint.setOffsets(5.75, 5.375, DistanceUnit.INCH);

     }
  
     /*
      * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
      */
     @Override
     public void loop() {
         fd.update();
         pinpoint.update();

         controller.update();
         if (controller.dpadDown.wasJustPressed()) fenceRadius -= 0.5;

         if (controller.dpadUp.wasJustPressed()) fenceRadius += 0.5;

         telemetry.addData("FISH", fd.getFishCoords());
         telemetry.addData("Calculated", fd.getXYInput());
         telemetry.addData("Odometry Coords", pinpoint.getPosX(DistanceUnit.METER) + "," + pinpoint.getPosY(DistanceUnit.METER));
         telemetry.addData("Odometry Angle", pinpoint.getHeading(AngleUnit.DEGREES));
         controller.update();
         Vector drive = controller.leftStick.toVector();
         telemetry.addData("Drive Enabled",
                 drivetrain.driveLimited(drive, controller.rightStick.x.value() * 0.5, fenceRadius, pinpoint.getPosition(DistanceUnit.METER))
         );

         TelemetryPacket canvasPacket = new TelemetryPacket(false);
         canvasPacket.fieldOverlay()

                 //odom coords
                 .strokeCircle(
                         pinpoint.getPosX(DistanceUnit.INCH),
                         pinpoint.getPosY(DistanceUnit.INCH),
                         9
                 )
                 .setStroke("Green")
                 .strokeLine(
                         pinpoint.getPosX(DistanceUnit.INCH),
                         pinpoint.getPosY(DistanceUnit.INCH),
                         pinpoint.getPosX(DistanceUnit.INCH) + (9 * Math.cos(pinpoint.getHeading(AngleUnit.RADIANS))),
                         pinpoint.getPosY(DistanceUnit.INCH) + (9 * Math.sin(pinpoint.getHeading(AngleUnit.RADIANS)))
                 )
                 .setStroke("Orange")
                 .strokeLine(
                         pinpoint.getPosX(DistanceUnit.INCH),
                         pinpoint.getPosY(DistanceUnit.INCH),
                         pinpoint.getPosX(DistanceUnit.INCH) + (9 * Math.cos(controller.leftStick.toVector().angle() + Math.PI / 2)),
                         pinpoint.getPosY(DistanceUnit.INCH) + (9 * Math.sin(controller.leftStick.toVector().angle() + Math.PI / 2))
                 )
                 //fence circle
                 .setStroke("purple")
                 .strokeCircle(0, 0, DistanceUnit.METER.toInches(fenceRadius))

                 //odom label
//                 .setFill("white")
//                 .fillText("Odometry Position", pinpoint.getPosX(DistanceUnit.INCH) + 1, pinpoint.getPosY(DistanceUnit.INCH) + 1, "Sans Serif", 0)

                 //odom line
                 .setStroke("white")
                 .setStrokeWidth(1)
                 .strokeLine(0, 0, pinpoint.getPosX(DistanceUnit.INCH), pinpoint.getPosY(DistanceUnit.INCH));

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