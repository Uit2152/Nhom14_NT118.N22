package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.doan.databinding.ActivityCategoryBinding;
import com.example.doan.databinding.ActivityRegisterBinding;

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