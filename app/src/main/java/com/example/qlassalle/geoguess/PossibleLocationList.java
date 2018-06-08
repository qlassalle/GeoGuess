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

    public static int NUMBER_OF_LOCATIONS_PER_GAME = 3;

    private Map<Level, List<PossibleLocation>> possibleLocations;

    public PossibleLocationList() {
        initMap();
    }

    /**
     * Initialize the map containing all the possible locations of our app. Design chosen is
     * explained in the pdf provided with the app.
     */
    private void initMap() {
        possibleLocations = new EnumMap<>(Level.class);
        possibleLocations.put(Level.EASY, new ArrayList<PossibleLocation>() {{
            // latitude min = point bas | max = point haut --> 1er argument dans les coordonnées
            // maps
            // longitude min = point est | max = point ouest --> 2nd argument dans les
            // coordonnées maps

            add(new PossibleLocation("Paris", 48.82, 48.89, 2.30, 2.39, 3));
            add(new PossibleLocation("Londres", 51.48, 51.53, -0.14, -0.10, 3));
            add(new PossibleLocation("New York", 40.75, 40.80, -73.98, -73.95, 3));
            add(new PossibleLocation("Shangai", 31.19, 31.25, 121.42, 121.49, 30));
            add(new PossibleLocation("Pekin", 39.87, 39.96, 116.30, 116.46, 30));
            add(new PossibleLocation("Rio de Janeiro", -22.95, -22.95, -43.21, -43.20, 10));
            add(new PossibleLocation("Moscou", 55.66, 55.84, 37.43, 37.79, 30));
            add(new PossibleLocation("Abidjan", 5.34, 5.37, -4.02, -3.98, 30));
        }});

        possibleLocations.put(Level.MEDIUM, new ArrayList<PossibleLocation>() {{
            add(new PossibleLocation("Barcelone", 41.38, 41.42, 2.20, 2.14, 20));
            add(new PossibleLocation("Turin", 45.01, 45.12, 7.70, 7.57, 20));
            add(new PossibleLocation("Berlin", 52.49, 52.52, 13.44, 13.37, 20));
            add(new PossibleLocation("Singapour", 1.28, 1.45, 103.96, 103.78, 20));
            add(new PossibleLocation("Sydney", -33.87, -33.86, 151.21, 151.20, 20));
            add(new PossibleLocation("Miami", 25.75, 25.78, -80.18, -80.22, 25));
            add(new PossibleLocation("Lisbonne", 38.70, 38.73, -9.11, -9.17, 20));
            add(new PossibleLocation("Cophenague", 55.66, 55.68, 12.58, 12.56, 20));
        }});

        possibleLocations.put(Level.HARD, new ArrayList<PossibleLocation>() {{
            add(new PossibleLocation("Angers", 47.46, 47.47, -0.54, -0.56, 3));
            add(new PossibleLocation("Naples", 40.85, 40.86, 14.27, 14.26, 5));
            add(new PossibleLocation("Atlanta", 33.74, 33.76, -84.37, -84.39, 10));
            add(new PossibleLocation("Mexico", 19.39, 19.46, -99.09, -99.17, 10));
            add(new PossibleLocation("Iatoustk, Sibérie", 61.97, 62.05, 129.74, 129.62,
                                     30));
            add(new PossibleLocation("Riga", 56.95, 56.96, 24.12, 24.10, 15));
            add(new PossibleLocation("Yaoundé", 3.85, 3.88, 11.53, 11.50, 15));
            add(new PossibleLocation("Buenos Aires", -34.60, -34.59, -56.36, -56.38, 10));
        }});
    }

    /**
     * Pick random locations in one of the list provided above. List is chosen regarding the
     * level provided by the user.
     * @param level The level in which the locations must be stored
     * @return A random list of locations
     */
    public Deque<PossibleLocation> pickRandomLocations(Level level) {
        // retrieve list corresponding to the level
        List<PossibleLocation> locationList = possibleLocations.get(level);
        // we declare a set to avoid checking whether an element is already there or not
        Set<PossibleLocation> locationSet = new HashSet<>(NUMBER_OF_LOCATIONS_PER_GAME);
        Random random = new Random();
        while (locationSet.size() != NUMBER_OF_LOCATIONS_PER_GAME) {
            locationSet.add(locationList.get(random.nextInt(locationList.size())));
        }

        // We use a Deque as a stack because it's easier to use here and it is exactly
        // designed for what we want to achieve. We simply generate a bunch of possible
        // locations, push them onto the stack and then pop them one by one after the
        // user clicks somewhere on the map
        Deque<PossibleLocation> chosenLocations = new ArrayDeque<>();
        chosenLocations.addAll(locationSet);
        return chosenLocations;
    }

    /**
     * Returns the number of locations available in a list. As we are always returning the size
     * of the easy list, list must be updated together to always have the same amount of possible
     * locations.
     * @return number of possible locations in the easy list
     */
    public int numberOfPossibleLocations() {
        return possibleLocations.get(Level.EASY).size();
    }
}
