package com.example.qlassalle.geoguess;

import com.orm.SugarRecord;

public class Score extends SugarRecord<Score> {

    private int score = 0;
    private Level level;

    public Score() {}

    public Score(int score, Level level) {
        this.score = score;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(Double distance) {
        score += distance;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Score{" + "score=" + score + ", level=" + level + '}';
    }
}
