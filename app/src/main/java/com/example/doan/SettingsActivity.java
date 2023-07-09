package com.example.doan;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_container, new MyPreferenceFragment())
                .commit();
        // Áp dụng giá trị màu ban đầu vào giao diện người dùng của ứng dụng
        applySelectedColor();
    }
    private void applySelectedColor() {
        // Lấy giá trị màu được chọn từ SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedColor = sharedPreferences.getString("selected_color", "White");

        // Áp dụng giá trị màu vào giao diện người dùng của ứng dụng
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(Color.parseColor(selectedColor));
    }
    private void showColorChooserDialog() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a background color");
        builder.setMultiChoiceItems(R.array.background_color_names, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    String selectedColorValue = getResources().getStringArray(R.array.background_color_values)[which];
                    editor.putString("background_color", selectedColorValue);
                } else {
                    editor.remove("background_color");
                }
                editor.apply();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applySelectedColor();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_button:
                showColorChooserDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}