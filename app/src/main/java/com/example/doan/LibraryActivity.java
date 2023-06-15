package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivityLibraryActibityBinding;

public class LibraryActivity extends AppCompatActivity {

    //view binding
    private ActivityLibraryActibityBinding binding;
    //firebase auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibraryActibityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBT.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );

        binding.libraryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LibraryActivity.this, LibraryActivity.class));

            }
        });
        binding.homeIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LibraryActivity.this,HomePageUserActivity.class));

            }
        });
        binding.accountIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LibraryActivity.this, AccountActivity.class));

            }
        });
    }

}