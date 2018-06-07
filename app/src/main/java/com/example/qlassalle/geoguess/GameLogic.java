package com.example.qlassalle.geoguess;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLogic {

    private Score score;

    public GameLogic() {
        this.score = new Score();
    }

    /**
     * Methods retrieved from Stackoverflow.com
     * https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
     *
     *
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point
     * @returns Distance in Kilometers
     */
    public double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance) / 1000;
    }

    public void calculateScore(LatLng generatedLocation, LatLng userLocation, PossibleLocation
            currentLocation) {
        Double distance = distance(generatedLocation.latitude, userLocation.latitude,
                                   generatedLocation.longitude, userLocation.longitude);
        System.out.println("---------------------------------------");
        System.out.println("---------------------------------------");
        System.out.println("---------------------------------------");
        System.out.println("\n\n\n In game logic \n\n\n");
        System.out.println("distance : " + distance);
        System.out.println("unit gap : " + currentLocation.getGapUnit());
        int ecart = (int) (distance / currentLocation.getGapUnit());
        System.out.println("ecart calculé : " + ecart);
        short calculatedScore = (short) (100 - ecart);
        if(calculatedScore < 0 ) {
            calculatedScore = 0;
        }
        score.updateScore(calculatedScore);
        System.out.println("Le score pour " + currentLocation.getName() + " " + "est " +
                                   calculatedScore);
        System.out.println("---------------------------------------");
        System.out.println("---------------------------------------");
        System.out.println("---------------------------------------");

    }

    public Score getScore() {
        return score;
    }

    public void saveScore(Level currentGameLevel) {
        score.setLevel(currentGameLevel);
        score.setNbPoints();
        score.save();
    }

    public Map<Level, Integer> getBestScores() {
        int nbLevels = Level.values().length;
        Map<Level, Integer> bestScores = initializeMap(nbLevels);
        List<Score> allScores = Score.findWithQuery(Score.class, "select nb_points, level " +
                "from Score");
        if(allScores.isEmpty()) {
            // return an empty map rather than null to avoid to do null check when calling this
            // method
            return Collections.emptyMap();
        }
        for (Score currentScore : allScores) {
            if(bestScores.get(currentScore.getLevel()) < currentScore.getNbPoints()) {
                bestScores.put(currentScore.getLevel(), currentScore.getNbPoints());
            }
        }
        return bestScores;
    }

    private Map<Level, Integer> initializeMap(int nbLevels) {
        Map<Level, Integer> bestScores = new HashMap<>();
        for (int i = 0; i < nbLevels; i++) {
            bestScores.put(Level.values()[i], 0);
        }
        return bestScores;
    }
}
