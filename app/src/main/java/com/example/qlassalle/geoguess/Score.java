package com.example.qlassalle.geoguess;

public class Score {

    private int score = 0;

    public Score() {}

    public int getScore() {
        return score;
    }

    public void updateScore(Double distance) {
        score += distance;
    }
}
