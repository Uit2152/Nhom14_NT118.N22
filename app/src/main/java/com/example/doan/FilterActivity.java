package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.doan.databinding.ActivityCategoryBinding;
import com.example.doan.databinding.ActivityFilterBinding;

public class FilterActivity extends AppCompatActivity {

    private ActivityFilterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBT.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  onBackPressed();
                                              }
                                          }
        );
    }
}