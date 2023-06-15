package com.example.doan;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivityRewardBinding;

public class RewardActivity extends AppCompatActivity {

    private ActivityRewardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRewardBinding.inflate(getLayoutInflater());
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