package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class ChangeThemeActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_NAME = "com.example.doan.theme";
    private static final String KEY_BACKGROUND_COLOR = "background_color";
    private int selectedBackgroundColor = 0;
    private Spinner backgroundSpinner;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        Spinner backgroundSpinner = findViewById(R.id.backgroundSpinner);
        backgroundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBackgroundColor = getResources().getIntArray(R.array.background_colors)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedBackgroundColor = 0;
            }
        });
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu giá trị màu nền được chọn
                sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_BACKGROUND_COLOR, selectedBackgroundColor);
                editor.apply();

                // Thay đổi màu nền cho trang
                getWindow().getDecorView().setBackgroundColor(selectedBackgroundColor);
            }
        });
    }
}