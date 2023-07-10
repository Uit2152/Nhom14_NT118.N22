package com.example.doan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivityUpdateChapterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateChapterActivity extends AppCompatActivity{
    private ActivityUpdateChapterBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    EditText txtTTChuong, txttenChuong, txtnoiDung;
    private int novelID;
    private int chapterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUpdateChapterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        novelID = intent.getIntExtra("novel_id", 0);
        chapterID = intent.getIntExtra("chapter_id", 0);

        txtTTChuong= findViewById(R.id.txtTTChuong);
        txttenChuong= findViewById(R.id.txttenChuong);
        txtnoiDung= findViewById(R.id.txtnoiDung);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chuongRef = database.getReference("ChuongTruyen");
        Query query = chuongRef.orderByChild("maT").equalTo(novelID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chuongSnapshot : snapshot.getChildren()) {
                    ChuongTruyen chuong = chuongSnapshot.getValue(ChuongTruyen.class);
                    if (chuong != null && chuong.getMaC() == chapterID) { // tìm thấy chương có mã chương là maC
                        // hiển thị thông tin của chương
                        txtTTChuong.setText(String.valueOf(chuong.getMaC()));
                        txttenChuong.setText(chuong.getTenC());
                        txtnoiDung.setText(chuong.getnd());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateChapterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        //handle click backSUp( go-back button)
        binding.backBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maCUpdate = Integer.parseInt(txtTTChuong.getText().toString());
                String tenC = txttenChuong.getText().toString();
                String noiDung = txtnoiDung.getText().toString();

                // Tạo DatabaseReference đến nút cần cập nhật
                DatabaseReference chuongRef = database.getReference("ChuongTruyen");
                Query query = chuongRef.orderByKey();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean foundChapter = false;
                        for (DataSnapshot chapterSnapshot : snapshot.getChildren()) {
                            ChuongTruyen ct = chapterSnapshot.getValue(ChuongTruyen.class);
                            int maT = ct.getmaT();
                            int maC = ct.getMaC();
                            if (maT == novelID && maC == chapterID) {
                                foundChapter = true;
                                // Cập nhật thông tin của chương
                                chapterSnapshot.child("tenC").getRef().setValue(tenC)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    chapterSnapshot.child("nd").getRef().setValue(noiDung)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(UpdateChapterActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                                                                        finish();
                                                                    }
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(UpdateChapterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(UpdateChapterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                break;
                            }
                        }
                        if (!foundChapter) {
                            Toast.makeText(UpdateChapterActivity.this, "Chapter not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}