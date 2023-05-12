package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan.databinding.ActivityHomePageAdminBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageAdminActivity extends AppCompatActivity {

    //view binding
    private ActivityHomePageAdminBinding binding;
    //firebase auth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();

        binding.logoutBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                firebaseAuth.signOut();

            }
        });

        binding.categoryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageAdminActivity.this, CategoryActivity.class));

            }
        });

        binding.rewardIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageAdminActivity.this, RewardActivity.class));

            }
        });
        binding.filterIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageAdminActivity.this, FilterActivity.class));

            }
        });

        binding.libraryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageAdminActivity.this, LibraryActibity.class));

            }
        });
        binding.homeIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageAdminActivity.this,HomePageAdminActivity.class));

            }
        });
        binding.accountIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageAdminActivity.this, AccountActivity.class));

            }
        });
    }

    private void checkUser()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            startActivity(new Intent(HomePageAdminActivity.this, MainActivity.class));
            finish();
        }
        else {
            String email= firebaseUser.getEmail();
            binding.subTitleTv.setText(email);
        }
    }

}