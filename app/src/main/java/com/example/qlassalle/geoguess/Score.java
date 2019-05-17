package com.example.qlassalle.geoguess;

import com.orm.SugarRecord;

/**
 * Entity class. Contains the score and a level associated with it
 */
public class Score extends SugarRecord<Score> {

    private int nbPoints = 0;
    private Level level;

    public Score() {}

    public Score(int nbPoints, Level level) {
        this.nbPoints = nbPoints;
        this.level = level;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void updateScore(int score) {
        this.nbPoints += score;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }


    /**
     * Compute the final score. Divide the sum of the different results by the number of location
     * to obtain a level of accuracy for the round
     */
    public void setNbPoints() {
        this.nbPoints /= PossibleLocationList.NUMBER_OF_LOCATIONS_PER_GAME;
    }


    @Override
    public String toString() {
        return "Score{" + "nbPoints=" + nbPoints + ", level=" + level + '}';
    }
}
