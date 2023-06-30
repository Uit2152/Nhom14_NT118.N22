package com.example.doan;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivitySettingacBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SetingACActivity extends AppCompatActivity {
    private ActivitySettingacBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingacBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        binding.backBT.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  onBackPressed();
                                              }
                                          }
        );
    }
}
