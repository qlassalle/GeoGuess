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
 * AsyncTask that performs an http request to check if position is ok
 */
public class CheckGeneratedLocation extends AsyncTask<String, Void, JSONObject> {

    /**
     * Performs an http request to the street view api to check if our position is correct. If
     * position is incorrect, API returns a ZERO_RESULTS which corresponds to a false location,
     * otherwise status is filled and it's what we're looking for after in PossibleLocation
     * .getRandomLocation() line 70
     * @param lesAdress An array containing our addresses, in our case it's a one element array
     * @return
     */
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

    /**
     * Creates a JSON object from a String
     * @param jsonOutput A String representing the JSON object in plain text
     * @return the JSON object correctly formatted, null if String isn't correct
     */
    private JSONObject buildJsonObject(String jsonOutput) {
        try {
            return new JSONObject(jsonOutput);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
