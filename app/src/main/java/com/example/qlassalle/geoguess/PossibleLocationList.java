package com.example.qlassalle.geoguess;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PossibleLocationList {

    private static int NUMBER_OF_LOCATIONS_PER_GAME = 1;

    private Map<Level, List<PossibleLocation>> possibleLocations;

    public PossibleLocationList() {
        initMap();
    }

    private void initMap() {
        possibleLocations = new EnumMap<>(Level.class);
        possibleLocations.put(Level.EASY, new ArrayList<PossibleLocation>() {{
            // latitude min = point bas | max = point haut --> 1er argument dans les coordonnées maps
            // longitude min = point est | max = point ouest --> 2nd argument dans les coordonnées maps

            add(new PossibleLocation("Paris", 48.82, 48.89, 2.30, 2.39));
            add(new PossibleLocation("Londres", 51.48, 51.53, -0.14, -0.10));
            add(new PossibleLocation("New York", 40.75, 40.80, -73.98, -73.95));
            add(new PossibleLocation("Shangai", 31.19, 31.25, 121.42, 121.49));
            add(new PossibleLocation("Pekin", 39.87, 39.96, 116.30, 116.46));
            add(new PossibleLocation("Rio de Janeiro", -22.95, -22.95, -43.21, -43.20));
            add(new PossibleLocation("Moscou", 55.66, 55.84, 37.43, 37.79));
            add(new PossibleLocation("Abidjan", 5.34, 5.37, -4.02, -3.98));
        }});
    }

    public Deque<PossibleLocation> pickRandomLocations(Level level) {
        // retrieve list corresponding to the level
        List<PossibleLocation> locationList = possibleLocations.get(level);
        // we declare a set to not have to check whether an element is already there or not
        Set<PossibleLocation> locationSet = new HashSet<>(NUMBER_OF_LOCATIONS_PER_GAME);
        Random random = new Random();
        while (locationSet.size() != NUMBER_OF_LOCATIONS_PER_GAME) {
            locationSet.add(locationList.get(random.nextInt(locationList.size())));
        }
        // We use a Deque as a stack because it's easier to use here. We simply generate a bunch
        // of possible locations, push them onto the stack and then pop them
        Deque<PossibleLocation> chosenLocations = new ArrayDeque<>();
        chosenLocations.addAll(locationSet);
        return chosenLocations;
    }
}
