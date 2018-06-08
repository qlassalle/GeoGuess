package com.example.qlassalle.geoguess;

import com.google.android.gms.maps.model.LatLng;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Class in charge of the game logic, more precisely of calculating and storing the score
 */
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

    /**
     * Calculate the score comparing the distance between the point chose by the user and the
     * location of the real point
     * @param generatedLocation position of the generated point by the app
     * @param userLocation position of the location chose by the user
     * @param currentLocation the full object of the generated location. Used to retrieve the
     *                        allowed gap for the point
     */
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

    /**
     * Set level, update score and save it
     * @param currentGameLevel the level chose by the user
     */
    public void saveScore(Level currentGameLevel) {
        score.setLevel(currentGameLevel);
        score.setNbPoints();
        score.save();
    }

    /**
     * Creates a map containing the best score for each difficulty level
     * @return A map with the difficulty as the key and the score as the value
     */
    public Map<Level, Integer> getBestScores() {
        int nbLevels = Level.values().length;
        Map<Level, Integer> bestScores = initializeMap(nbLevels);
        List<Score> allScores = Score.findWithQuery(Score.class, "select nb_points, level " +
                "from Score");
        for (Score currentScore : allScores) {
            if(bestScores.get(currentScore.getLevel()) < currentScore.getNbPoints()) {
                bestScores.put(currentScore.getLevel(), currentScore.getNbPoints());
            }
        }
        return bestScores;
    }

    /**
     * Initializes the map with zeroes to handle case where user has never played
     * @param nbLevels Number of levels provided by the game
     * @return A map with the level as the key and zeroes as values
     */
    private Map<Level, Integer> initializeMap(int nbLevels) {
        Map<Level, Integer> bestScores = new EnumMap<>(Level.class);
        for (int i = 0; i < nbLevels; i++) {
            bestScores.put(Level.values()[i], 0);
        }
        return bestScores;
    }

    public Score getScore() {
        return score;
    }
}
