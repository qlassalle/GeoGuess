package com.example.qlassalle.geoguess;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class PossibleLocation {

    private String name;
    private Double latitudeMin;
    private Double latitudeMax;
    private Double longitudeMin;
    private Double longitudeMax;

    private static final String API_KEY = "AIzaSyB1iqthMV_WMs54Ljv4Ma97TlmXFvyoJXs";

    public PossibleLocation(String name, Double latitudeMin, Double latitudeMax, Double longitudeMin,
                            Double longitudeMax) {
        this.name = name;
        this.latitudeMin = latitudeMin;
        this.longitudeMin = longitudeMin;
        this.latitudeMax = latitudeMax;
        this.longitudeMax = longitudeMax;
    }

    private Point generateRandomLocation() {
        Point p = new Point();
        Random r = new Random();
        p.latitude = latitudeMin + (latitudeMax - latitudeMin) * r.nextDouble();
        p.longitude = longitudeMin + (longitudeMax - longitudeMin) * r.nextDouble();
        return p;
    }

    public LatLng getRandomLocation() throws IOException, ExecutionException, InterruptedException, JSONException {
        Point p;
        boolean isAdressCorrect;
        String address;
        // check if the generated location is correct
        do {
            p = generateRandomLocation();
            address = "https://maps.googleapis.com/maps/api/streetview/metadata?size=600x300&"
                    + "location=" + p.latitude + "," + p.longitude + "&fov=90&heading=235&pitch=10&"
                    + "key=" + API_KEY;
            JSONObject addressJson = new CheckGeneratedLocation().execute(address).get();
            isAdressCorrect = "OK".equals(addressJson.getString("status"));
            if(isAdressCorrect) {
                p.latitude = Double.valueOf(addressJson.getJSONObject("location").getString("lat"));
                p.longitude = Double.valueOf(addressJson.getJSONObject("location").getString("lng"));
            }
        } while(!isAdressCorrect);
        return new LatLng(p.latitude, p.longitude);
    }

    public String getName() {
        return name;
    }
}
