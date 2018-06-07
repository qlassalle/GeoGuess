package com.example.qlassalle.geoguess;

import java.util.Arrays;

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

    public static String[] getNames() {
        Level[] levels = values();
        String[] names = new String[levels.length];

        for (int i = 0; i < levels.length; i++) {
            names[i] = levels[i].nom;
        }

        return names;
    }
}
