package com.example.doan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeThemeActivity extends AppCompatActivity {

    private Spinner backgroundSpinner;
    private Spinner textColorSpinner;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        backgroundSpinner = findViewById(R.id.backgroundSpinner);
        textColorSpinner = findViewById(R.id.textColorSpinner);

        sharedPreferences = getSharedPreferences("theme", Context.MODE_PRIVATE);

        // Lấy màu nền và màu chữ đã lưu trữ
        int backgroundIndex = sharedPreferences.getInt("background", 0);
        int textColorIndex = sharedPreferences.getInt("textColor", 0);

        // Thiết lập màu nền và màu chữ cho các Spinner
        backgroundSpinner.setSelection(backgroundIndex);
        textColorSpinner.setSelection(textColorIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Lưu trữ màu nền và màu chữ được chọn
        int backgroundIndex = backgroundSpinner.getSelectedItemPosition();
        int textColorIndex = textColorSpinner.getSelectedItemPosition();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("background", backgroundIndex);
        editor.putInt("textColor", textColorIndex);
        editor.apply();
    }
}
