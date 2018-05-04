package com.example.qlassalle.geoguess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by qlassalle on 20/04/2018.
 */

public class PossibleLocation {

    Double xMin = 2.27;
    Double yMin = 48.89;
    Double xMax = 2.34;
    Double yMax = 48.82;

    private static final DecimalFormat df = new DecimalFormat(".#####");
    private static final String API_KEY = "AIzaSyB1iqthMV_WMs54Ljv4Ma97TlmXFvyoJXs";

    private Point generateRandomLocation() {
        Point p = new Point();
        Random r = new Random();
        p.x = Double.valueOf(df.format(xMin + (xMax - xMin) * r.nextDouble()));
        p.y = Double.valueOf(df.format(yMin + (yMax - yMin) * r.nextDouble()));
        return p;
    }

    public Point getRandomLocation() throws IOException, ExecutionException, InterruptedException, JSONException {
        Point p;
        boolean isAdressCorrect;
        // check if the generated location is correct
        do {
            p = generateRandomLocation();
            String address = "https://maps.googleapis.com/maps/api/streetview/metadata?size=600x300&"
                    + "location=" + p.y + "," + p.x + "&fov=90&heading=235&pitch=10&"
                    + "key=" + API_KEY;
            JSONObject addressJson = new CheckGeneratedLocation().execute(address).get();
            isAdressCorrect = "OK".equals(addressJson.getString("status"));
        } while(!isAdressCorrect);
        return p;
    }
}
