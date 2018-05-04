package com.example.qlassalle.geoguess;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by qlassalle on 02/05/2018.
 */

public class CheckGeneratedLocation extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... lesAdress) {
        String address = lesAdress[0];
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(address);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String strLine;
                while ((strLine = input.readLine()) != null) {
                    sb.append(strLine);
                }
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buildJsonObject(sb.toString());
    }

    private JSONObject buildJsonObject(String jsonOutput) {
        try {
            return new JSONObject(jsonOutput);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
