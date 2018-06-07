package com.example.qlassalle.geoguess;

import com.orm.SugarRecord;

public class Score extends SugarRecord<Score> {

    private int nbPoints = 0;
    // Started with an enum here but I couldn't save the enum with Sugar ORM so I have to change
    // for a String. Not a nice thing but I was short on time and didn't manage to find another way
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

    @Override
    public String toString() {
        return "Score{" + "nbPoints=" + nbPoints + ", level=" + level + '}';
    }

    public void setNbPoints() {
        this.nbPoints /= PossibleLocationList.NUMBER_OF_LOCATIONS_PER_GAME;
    }
}
