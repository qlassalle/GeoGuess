/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.qlassalle.geoguess;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ExecutionException;

/**
 * This shows how to create a simple activity with streetview and a map
 */
public class SplitStreetView extends AppCompatActivity implements OnMarkerDragListener,
        OnStreetViewPanoramaChangeListener {

    private static final String MARKER_POSITION_KEY = "MarkerPosition";

    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    private StreetViewPanorama mStreetViewPanorama;

    private Marker mMarker;

    private Deque<PossibleLocation> possibleLocation;
    private LatLng generatedLocation;
    private PossibleLocation currentLocation;
    private GameLogic gl = new GameLogic();
    private Level currentGameLevel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_street_view);

        final LatLng markerPosition;
        if (savedInstanceState == null) {
            markerPosition = SYDNEY;
        } else {
            markerPosition = savedInstanceState.getParcelable(MARKER_POSITION_KEY);
        }

        final PossibleLocationList possibleLocationList = new PossibleLocationList();
        Intent intent = getIntent();
        currentGameLevel = (Level) intent.getSerializableExtra("level");
        PossibleLocationList.NUMBER_OF_LOCATIONS_PER_GAME = intent.getIntExtra("nbCity", 1);
        possibleLocation = possibleLocationList.pickRandomLocations(currentGameLevel);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById
                        (R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                mStreetViewPanorama = panorama;
                mStreetViewPanorama.setOnStreetViewPanoramaChangeListener(SplitStreetView.this);
                // Only need to set the position once as the streetview fragment will maintain
                // its state.
                if (savedInstanceState == null) {
                    try {
                        currentLocation = possibleLocation.pop();
                        generatedLocation = currentLocation.getRandomLocation();
                        changePosition(generatedLocation);
                    } catch (IOException | ExecutionException | JSONException |
                            InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            /**
             *
             * @param map
             */
            @Override
            public void onMapReady(GoogleMap map) {
                map.setOnMarkerDragListener(SplitStreetView.this);
                // Creates a draggable marker. Long press to drag.
                mMarker = map.addMarker(new MarkerOptions().position(markerPosition).draggable
                        (true).visible(false));

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng userLocation) {
                        try {
                            gl.calculateScore(generatedLocation, userLocation,
                                                   currentLocation);
                            if (possibleLocation.isEmpty()) {
                                gl.saveScore(currentGameLevel);
                                backToMain();
                                // go back to main activity
                            } else {
                                currentLocation = possibleLocation.pop();
                                generatedLocation = currentLocation.getRandomLocation();
                                changePosition(generatedLocation);
                            }
                        } catch (IOException | ExecutionException | InterruptedException |
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void changePosition(LatLng newPosition) {
        mStreetViewPanorama.setPosition(newPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MARKER_POSITION_KEY, mMarker.getPosition());
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation location) {
        if (location != null) {
            mMarker.setPosition(location.position);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        mStreetViewPanorama.setPosition(marker.getPosition(), 150);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }
}