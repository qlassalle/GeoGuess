package com.example.qlassalle.geoguess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerLevel, spinnerNbCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateSpinners();
        showBestScores();
    }

    private void showBestScores() {
        GameLogic gl = new GameLogic();
        Map<Level, Integer> bestScores = gl.getBestScores();
        TextView recordEasy = findViewById(R.id.scoreEasy);
        recordEasy.setText(String.valueOf(bestScores.get(Level.EASY)));
        TextView recordMedium = findViewById(R.id.scoreMedium);
        recordMedium.setText(String.valueOf(bestScores.get(Level.MEDIUM)));
        TextView recordHard = findViewById(R.id.scoreHard);
        recordHard.setText(String.valueOf(bestScores.get(Level.HARD)));
    }

    public void startGameButton(View view) {
        // pass level and number of cities to SplitStreetView
        Level selectedLevel = Level.valueOf(spinnerLevel.getSelectedItem().toString());
        Intent intent = new Intent(this, SplitStreetView.class);
        intent.putExtra("level", selectedLevel);
        intent.putExtra("nbCity", (int) spinnerNbCity.getSelectedItem());
        startActivity(intent);
    }

    private void populateSpinners() {
        spinnerLevel = findViewById(R.id.difficultyLevel);
        String[] levels = Level.getNames();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevel.setAdapter(adapter);

        PossibleLocationList possibleLocationList = new PossibleLocationList();
        Integer[] nbCity = new Integer[possibleLocationList.numberOfPossibleLocations()];
        for (int i = 0; i < nbCity.length; i++) {
            nbCity[i] = i + 1;
        }
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, nbCity);
        integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNbCity = findViewById(R.id.nbCity);
        spinnerNbCity.setAdapter(integerArrayAdapter);
    }
}
