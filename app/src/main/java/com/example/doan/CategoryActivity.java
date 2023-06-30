package com.example.doan;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCategoryBinding.inflate(getLayoutInflater());
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
    }
}