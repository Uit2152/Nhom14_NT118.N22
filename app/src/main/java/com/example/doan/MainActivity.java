package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.doan.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //view binding
    private ActivityMainBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;
    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        //setup progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);


        //handle btSignUp click, start SignUp screen;
        binding.btSignUp.setOnClickListener(new View.OnClickListener()
                                             {
                                                 @Override
                                                 public void onClick(View v)
                                                 {
                                                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                                                 }
                                             }
        );

        //handle btSignIn click, begin login
        binding.btSignIn.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick(View v)
                                             {
                                                 validateData();
                                             }
                                         }
        );

        binding.forgotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (MainActivity.this , ForgotActivity.class));
            }
        });
    }

    private String email="", password="";

    private void validateData() {
        // before loggin, lets do data validation
        //get data

        email = binding.emailET.getText().toString().trim();
        password = binding.passET.getText().toString().trim();

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this, "Invalid email pattern !", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            } else {
                //data is validated, begin login
                loginUser();
            }
        }
    }

    private void loginUser()
    {
        //show progress
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        //login user
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //login success, check if user is user or admin
                checkUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //login failed
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUser()
    {
        progressDialog.setMessage("Checking user... ");
        //check if user is user or admin from realtime database
        //get current user
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        //check in db
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressDialog.dismiss();
                //get user type
                String userType = ""+snapshot.child("userType").getValue();
                //check user type
                if (userType.equals("user"))
                {
                    //this is simple user, open user home page
                    startActivity(new Intent(MainActivity.this,HomePageUserActivity.class));
                    finish();
                } else
                {
                    if(userType.equals("admin"))
                    {
                        //this is admin, open admin homepage
                        startActivity(new Intent(MainActivity.this,HomePageAdminActivity.class));
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