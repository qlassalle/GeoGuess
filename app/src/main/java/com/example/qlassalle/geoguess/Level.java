package com.example.qlassalle.geoguess;

public enum Level {

    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    String nom;

    Level(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
