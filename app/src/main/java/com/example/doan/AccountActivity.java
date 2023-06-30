package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivityAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    //firebase auth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();
        binding.backBT.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  onBackPressed();
                                              }
                                          }
        );
        binding.settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AccountActivity.this, SetingACActivity.class));

            }
        });
        binding.NenVaMauChuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AccountActivity.this, ChangeThemeActivity.class));

            }
        });
        binding.DangXuatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                firebaseAuth.signOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                finish();
            }
        });


    }
    private void checkUser()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            startActivity(new Intent(AccountActivity.this, MainActivity.class));
            finish();
        }
        else {
            String email= firebaseUser.getEmail();
            binding.emailAC.setText(email);
            String name= firebaseUser.getDisplayName();
            binding.nameAC.setText(name);

        }
    }

}