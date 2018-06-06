package com.example.qlassalle.geoguess;

import com.orm.SugarRecord;

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

    @Override
    public String toString() {
        return "Score{" + "nbPoints=" + nbPoints + ", level=" + level + '}';
    }
}
