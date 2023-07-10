package com.example.doan;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.databinding.ActivityUploadNextBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UploadNextActivity extends AppCompatActivity {
    private ImageView nxtImage;
    private TextView nxttentruyen, nxttacgia, nxttinhtrang, nxtsochuong;
    private int novelID;
    private ActivityUploadNextBinding binding;
    private ArrayList<ChuongTruyen> chapterList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadNextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        novelID = intent.getIntExtra("story_id", 1);
        recyclerView = findViewById(R.id.recyclerChap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNovelDetails();

        // handle click backSUp (go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UploadChapterActivity.class);
                intent.putExtra("novel_id", novelID); // truyền mã truyện
                v.getContext().startActivity(intent);            }
        });
    }

    private void loadNovelDetails() {
        if (novelID == 0) {
            // handle null novelID
            return;
        }

        DatabaseReference refNovel = FirebaseDatabase.getInstance().getReference("Truyen");
        refNovel.orderByChild("maT").equalTo(novelID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // get data from Firebase
                if (snapshot.exists()) {
                    for (DataSnapshot novelSnapshot : snapshot.getChildren()) {
                        String novelTitle = "" + novelSnapshot.child("tenTV").getValue();
                        String author = "" + novelSnapshot.child("tacgiaTV").getValue();
                        String status = "" + novelSnapshot.child("tinhtrangTV").getValue();
                        String chapterNumbers = "" + novelSnapshot.child("sochuongTV").getValue();

                        // update novel details
                        binding.nxttentruyen.setText(novelTitle);
                        binding.nxttacgia.setText(author);
                        binding.nxtsochuong.setText(chapterNumbers);
                        binding.nxttinhtrang.setText(status);


                    }
                } else {
                    // handle not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });

        DatabaseReference refChapter = FirebaseDatabase.getInstance().getReference("ChuongTruyen");
        refChapter.orderByChild("maT").equalTo(novelID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ChuongTruyen> chapterList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChuongTruyen chapter = snapshot.getValue(ChuongTruyen.class);
                    chapterList.add(chapter);
                }
                displaychapterList(chapterList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void displaychapterList(List<ChuongTruyen> chapterList) {
        UploadNextAdapter adapter = new UploadNextAdapter(chapterList);
        recyclerView.setAdapter(adapter);
    }
}