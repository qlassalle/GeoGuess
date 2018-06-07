package com.example.qlassalle.geoguess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateSpinner();
    }

    public void startGameButton(View view) {
        // pass level to SplitStreetView
        Level selectedLevel = Level.valueOf(spinner.getSelectedItem().toString());
        Intent intent = new Intent(this, SplitStreetView.class);
        intent.putExtra("level", selectedLevel);
        startActivity(intent);
    }

    private void populateSpinner() {
        spinner = findViewById(R.id.difficultyLevel);
        String[] levels = Level.getNames();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
