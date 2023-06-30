package com.example.doan;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeThemeActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_NAME = "com.example.doan.theme";
    private static final String KEY_BACKGROUND_COLOR = "background_color";

    private Spinner backgroundSpinner;
    private SharedPreferences sharedPreferences;

    private int[] backgroundColors = {
            Color.WHITE,
            Color.BLACK,
            Color.BLUE,
            Color.RED,
            Color.YELLOW
    };
    private String[] colorNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        backgroundSpinner = findViewById(R.id.backgroundSpinner);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        colorNames = getResources().getStringArray(R.array.background_colors);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, colorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backgroundSpinner.setAdapter(adapter);

        int backgroundColorIndex = sharedPreferences.getInt(KEY_BACKGROUND_COLOR, 0);
        if (isValidIndex(backgroundColorIndex)) {
            backgroundSpinner.setBackgroundColor(backgroundColors[backgroundColorIndex]);
            backgroundSpinner.setSelection(backgroundColorIndex);
        }

        backgroundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isValidIndex(position)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(KEY_BACKGROUND_COLOR, position);
                    editor.apply();

                    backgroundSpinner.setBackgroundColor(backgroundColors[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < backgroundColors.length;
    }
}