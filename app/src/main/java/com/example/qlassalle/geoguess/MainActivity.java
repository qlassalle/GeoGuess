package com.example.qlassalle.geoguess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "AIzaSyB1iqthMV_WMs54Ljv4Ma97TlmXFvyoJXs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("displayed this");

        Intent intent = new Intent(this, SplitStreetView.class);
        startActivity(intent);
    }

    private void sendRequest() throws IOException, ExecutionException, InterruptedException {
        PossibleLocation pl = new PossibleLocation();
        Point p;
        String address;
        JSONObject addressJson;
        // check if the generated location is correct
        do {
            p = pl.generateRandomLocation();
            address = "https://maps.googleapis.com/maps/api/streetview/metadata?size=600x300&"
                    + "location=" + p.y + "," + p.x + "&fov=90&heading=235&pitch=10&"
                    + "key=" + API_KEY;
            addressJson = new CheckGeneratedLocation().execute(address).get();
        } while(addressJson == null);
        System.out.println(addressJson);
        try {
            JSONObject locations = addressJson.getJSONObject("location");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
