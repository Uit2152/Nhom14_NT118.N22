package com.example.doan;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doan.databinding.ActivityUploadChapterBinding;
import com.example.doan.databinding.ActivityUploadNextBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadChapterActivity extends AppCompatActivity{
    private ActivityUploadChapterBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    EditText txtTTChuong, txttenChuong, txtnoiDung;
    ImageButton btnSave1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUploadChapterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txtTTChuong= findViewById(R.id.txtTTChuong);
        txttenChuong= findViewById(R.id.txttenChuong);
        txtnoiDung= findViewById(R.id.txtnoiDung);
        btnSave1= findViewById(R.id.btnSave1);

        //handle click backSUp( go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );

        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {


        int MaC= Integer.parseInt(txtTTChuong.getText().toString());
        String tenC=txttenChuong.getText().toString();
        String ND=txtnoiDung.getText().toString();

        Chapter chuong= new Chapter(MaC, tenC, ND);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("ChuongTruyen").child(currentDate).setValue(chuong)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UploadChapterActivity.this, "Save successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadChapterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
