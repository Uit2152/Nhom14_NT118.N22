package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan.databinding.ActivityHomePageAdminBinding;
import com.example.doan.databinding.ActivityHomePageUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageUserActivity extends AppCompatActivity {

    //view binding
    private ActivityHomePageUserBinding binding;
    //firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();
        //create data for reading and updating
        setUpData();
        binding.logoutBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        binding.uploadIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageUserActivity.this, UploadActivity.class));

            }
        });
        binding.categoryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, CategoryActivity.class));

            }
        });

        binding.rewardIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, RewardActivity.class));

            }
        });
        binding.filterIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, FilterActivity.class));

            }
        });

        binding.libraryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, LibraryActivity.class));

            }
        });
        binding.homeIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this,HomePageUserActivity.class));

            }
        });
        binding.accountIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, AccountActivity.class));

            }
        });
        binding.xemThem1TV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, CategoryActivity.class));

            }
        });
        binding.xemThem2TV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, CategoryActivity.class));

            }
        });
        binding.contentsearchTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, SearchActivity.class));

            }
        });
        binding.searchIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, SearchActivity.class));

            }
        });
    }
    private void checkUser()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            startActivity(new Intent(HomePageUserActivity.this, MainActivity.class));
            finish();
        }
        else {
            String email= firebaseUser.getEmail();
            binding.subTitleTv.setText(email);
        }
    }

    private void setUpData()
    {

    }
}