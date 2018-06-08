package com.example.qlassalle.geoguess;

/**
 * Enum containing all the levels of difficulty provided in the app
 */
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

    /**
     * Creates a String array containing all the names of our enumeration
     * @return a String array with the names of our enumeration
     */
    public static String[] getNames() {
        Level[] levels = values();
        String[] names = new String[levels.length];

        for (int i = 0; i < levels.length; i++) {
            names[i] = levels[i].nom;
        }

        return names;
    }
}
