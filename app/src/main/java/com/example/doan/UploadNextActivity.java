package com.example.doan;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan.databinding.ActivityUploadNextBinding;
public class UploadNextActivity extends AppCompatActivity{
    private ActivityUploadNextBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUploadNextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle click backSUp( go-back button)
        binding.backBT.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadNextActivity.this, UploadChapterActivity.class));

            }
        });
    }

}
