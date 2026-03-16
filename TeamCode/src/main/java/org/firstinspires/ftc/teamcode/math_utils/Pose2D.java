package org.firstinspires.ftc.teamcode.math_utils;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Pose2D {

    public DistanceUnit distanceUnit;
    public double x;
    public double y;
    public AngleUnit angleUnit;
    public double heading;

    public Pose2D (DistanceUnit distanceUnit, double x, double y, AngleUnit angleUnit, double heading){

    }

    public double getX (DistanceUnit distanceUnit){
        return distanceUnit.fromMeters(x);
    }

    public double getY (DistanceUnit distanceUnit){
        return distanceUnit.fromMeters(y);
    }

    public double getHeading (AngleUnit angleUnit){
        return angleUnit.fromRadians(heading);
    }

    public double getMagnitude (DistanceUnit distanceUnit) {
        return distanceUnit.fromMeters(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }


}
