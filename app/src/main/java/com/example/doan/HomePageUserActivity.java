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

        binding.logoutBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                firebaseAuth.signOut();
                checkUser();
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
}