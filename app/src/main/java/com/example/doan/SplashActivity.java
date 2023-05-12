package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SplashActivity extends AppCompatActivity
{
    //firebase auth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //start main screen after 2s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, 2000 //2000 means 2seconds
        );
    }

    private void checkUser()
    {
        //get current user, if logged in
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser == null)
        {
            //user not logged in
            //start main screen
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //finish this activity
            finish();
        } else
        {
            //user logged in check user type, same as done in login
            //check in db
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //get user type
                    String userType = ""+snapshot.child("userType").getValue();
                    //check user type
                    if (userType.equals("user"))
                    {
                        //this is simple user, open user home page
                        startActivity(new Intent(SplashActivity.this,HomePageUserActivity.class));
                        finish();
                    } else
                    {
                        if(userType.equals("admin"))
                        {
                            //this is admin, open admin homepage
                            startActivity(new Intent(SplashActivity.this,HomePageAdminActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}