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

    private static int NUMBER_OF_LOCATIONS_PER_GAME;

    private Map<Level, List<PossibleLocation>> possibleLocations;

    public PossibleLocationList() {
        initMap();
    }

    private void initMap() {
        possibleLocations = new EnumMap<>(Level.class);
        possibleLocations.put(Level.EASY, new ArrayList<PossibleLocation>() {{
            // latitude min = point bas | max = point haut --> 1er argument dans les coordonnées maps
            // longitude min = point est | max = point ouest --> 2nd argument dans les coordonnées maps

            add(new PossibleLocation("Paris", 48.89, 48.82, 2.30, 2.39));
            add(new PossibleLocation("Londres", 51.48, 51.53, -0.14, -0.10));
//            add(new PossibleLocation("New York", -73.99, 40.70, -73.95, 40.84));
//            add(new PossibleLocation("Shangai", 31.25, 121.41, 31.17, 121.47));
//            add(new PossibleLocation("Pekin", 2.27, 48.89, 2.34, 48.82));
//            add(new PossibleLocation("Rio de Janeiro", 2.27, 48.89, 2.34, 48.82));
//            add(new PossibleLocation("Moscou", 2.27, 48.89, 2.34, 48.82));
//            add(new PossibleLocation("Abidjan", 2.27, 48.89, 2.34, 48.82));
        }});
        NUMBER_OF_LOCATIONS_PER_GAME = possibleLocations.get(Level.EASY).size();
    }

    public Deque<PossibleLocation> pickRandomLocations(Level level) {
        // retrieve list corresponding to the level
        List<PossibleLocation> locationList = possibleLocations.get(level);
        // we declare a set to not have to check whether an element is already there or not
        Set<PossibleLocation> locationSet = new HashSet<>(NUMBER_OF_LOCATIONS_PER_GAME);
        Random random = new Random();
        while (locationSet.size() != NUMBER_OF_LOCATIONS_PER_GAME) {
            System.out.println(random.nextInt(locationList.size()));
            locationSet.add(locationList.get(random.nextInt(locationList.size())));
        }
        Deque<PossibleLocation> chosenLocations = new ArrayDeque<>();
        chosenLocations.addAll(locationSet);
        return chosenLocations;
    }
}
