package com.example.qlassalle.geoguess;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void sendRequest() throws IOException {
        PossibleLocation pl = new PossibleLocation();
        Point p;
        List<String> lesAdresses = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            p = pl.generateRandomLocation();
            lesAdresses.add("https://maps.googleapis.com/maps/api/streetview/metadata?size=600x300&"
                    + "location=" + p.y + "," + p.x + "&fov=90&heading=235&pitch=10&"
                    + "key=AIzaSyB1iqthMV_WMs54Ljv4Ma97TlmXFvyoJXs");
        }
        for (String lesAdress : lesAdresses) {
            new CheckGeneratedLocation().execute(lesAdress);
        }
        System.out.println("done");
    }
}
