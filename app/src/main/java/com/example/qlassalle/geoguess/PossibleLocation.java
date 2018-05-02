package com.example.qlassalle.geoguess;

import java.util.Random;

/**
 * Created by qlassalle on 20/04/2018.
 */

public class PossibleLocation {

    Double xMin = 2.27;
    Double yMin = 48.89;
    Double xMax = 2.34;
    Double yMax = 48.82;

    public Point generateRandomLocation() {
        Point p = new Point();
        Random r = new Random();
        p.x = xMin + (xMax - xMin) * r.nextDouble();
        p.y = yMin + (yMax - yMin) * r.nextDouble();
        return p;
    }
}
