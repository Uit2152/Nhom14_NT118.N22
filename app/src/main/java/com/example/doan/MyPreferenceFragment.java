package com.example.doan;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ListPreference mListPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Truy xuất đến ListPreference
        mListPreference = (ListPreference) findPreference("background_color");
        if (mListPreference != null) {
            // Kiểm tra giá trị đầu vào
            CharSequence[] entryValues = mListPreference.getEntryValues();
            for (int i = 0; i < entryValues.length; i++) {
                if (entryValues[i] == null) {
                    entryValues[i] = "";
                }
            }
            mListPreference.setEntryValues(entryValues);

            // Thiết lập mô tả ban đầu
            CharSequence defaultValue = mListPreference.getEntry();
            mListPreference.setDefaultValue(defaultValue);
            mListPreference.setSummary(defaultValue);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("background_color")) {
            String value = sharedPreferences.getString(key, "");
            // Lưu giá trị màu được chọn vào SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selected_color", value);
            editor.apply();
        }
    }
}