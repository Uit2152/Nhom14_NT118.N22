package com.example.doan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doan.databinding.ActivityUploadNewBinding;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;


public class UploadNewActivity extends AppCompatActivity {
    private ActivityUploadNewBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    EditText txttenTruyen, txttacGia, txtsoChuong, txtmoTa, txtmaTL;
    ImageButton btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txttenTruyen= findViewById(R.id.txttenTruyen);
        txttacGia= findViewById(R.id.txttacGia);
        txtmaTL=findViewById(R.id.txtmaTL);
        txtsoChuong= findViewById(R.id.txtsoChuong);
        txtmoTa= findViewById(R.id.txtmoTa);
        btnSave= findViewById(R.id.btnSave);

        //handle click backSUp( go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   onBackPressed();
                                               }
                                           }
        );

        //handle click save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        Random random = new Random();
        int storyId = random.nextInt(100);

        int maT=storyId;
        String tentruyen= txttenTruyen.getText().toString();
        String tacgia=txttacGia.getText().toString();
        int maTL= Integer.parseInt(txtmaTL.getText().toString());
        int sochuong= Integer.parseInt(txtsoChuong.getText().toString());
        String mota=txtmoTa.getText().toString();

        Story truyen= new Story(maT, tentruyen, tacgia,maTL, sochuong, mota);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Truyen");
        DatabaseReference storyRef = databaseReference.push(); // Tạo một child node mới với storyId tự động tăng

        //String storyId = storyRef.getKey();



        //String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Truyen").child(String.valueOf(storyId)).setValue(truyen)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UploadNewActivity.this, "Save successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadNewActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
