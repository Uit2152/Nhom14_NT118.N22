package com.example.doan;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan.databinding.ActivityUploadNewBinding;
public class UploadNewActivity extends AppCompatActivity {
    private ActivityUploadNewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUploadNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle click backSUp( go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );
    }
}
