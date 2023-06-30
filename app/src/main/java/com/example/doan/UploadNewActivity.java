package com.example.doan;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan.databinding.ActivityUploadNewBinding;
public class UploadNewActivity extends AppCompatActivity {
    private ActivityUploadNewBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle click backSUp( go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   onBackPressed();
                                               }
                                           }
        );
    }

    private String tenTV = "", tacgiaTV = "", Mota = "";
    int sochuongTV;

    private void validateData() {

        //get data

        tenTV = binding.txttenTruyen.getText().toString().trim();
        tacgiaTV = binding.txttacGia.getText().toString().trim();
        sochuongTV = Integer.parseInt(binding.txtsoChuong.getText().toString().trim());
        Mota = binding.txtnoiDung.getText().toString().trim();
    }
}
