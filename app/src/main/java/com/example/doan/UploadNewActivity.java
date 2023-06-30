package com.example.doan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.doan.databinding.ActivityUploadNewBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayDeque;
import java.util.HashMap;

public class UploadNewActivity extends AppCompatActivity {
    private ActivityUploadNewBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        //handle click backSUp( go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   onBackPressed();
                                               }
                                           }
        );

        //handle click save
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private String tenTV = "", tacgiaTV = "", Mota = "";
    private int sochuongTV;
    private boolean isSoChuongEmpty = false;

    private void validateData() {

        //get data
        tenTV = binding.txttenTruyen.getText().toString().trim();
        tacgiaTV = binding.txttacGia.getText().toString().trim();
        try {
            sochuongTV = Integer.parseInt(binding.txtsoChuong.getText().toString().trim());
        } catch (NumberFormatException e){
            isSoChuongEmpty=true;
        }
        Mota = binding.txtmoTa.getText().toString().trim();

        //Validate data
        if(TextUtils.isEmpty(tenTV)){
            Toast.makeText(this, "Nhập tên truyện", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(tacgiaTV)){
            Toast.makeText(this, "Nhập tác giả", Toast.LENGTH_SHORT).show();
        }
        else if(isSoChuongEmpty){
            Toast.makeText(this, "Nhập số chương", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Mota)){
            Toast.makeText(this, "Nhập giới thiệu", Toast.LENGTH_SHORT).show();
        }
        else{
            saveStorage();
        }
    }

    private void saveStorage() {
        long timestamp = System.currentTimeMillis();

        String uid= firebaseAuth.getUid();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Truyen");
        int MaT= databaseRef.push().hashCode();
        // Tạo đối tượng Truyen với các giá trị từ các trường nhập liệu
        HashMap<String, Object> hashMap= new HashMap<>();
        hashMap.put("uid",""+uid);
        hashMap.put("MaT", ""+MaT);
        hashMap.put("tenTV",""+tenTV);
        hashMap.put("tacgiaTV",""+tacgiaTV);
        hashMap.put("sochuongTV",""+sochuongTV);
        hashMap.put("Mota", ""+Mota);
        hashMap.put("timestamp",timestamp);

        //Story truyen = new Story(tenTV, tacgiaTV, sochuongTV, Mota);

        databaseReference.child(""+timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Truyện đã được lưu thành công vào Firebase Realtime Database
                        progressDialog.dismiss();
                        Toast.makeText(UploadNewActivity.this, "Truyện đã được đăng thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadNewActivity.this, UploadActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Xử lý khi lưu truyện thất bại
                        progressDialog.dismiss();
                        Toast.makeText(UploadNewActivity.this, "Lỗi khi đăng truyện: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
