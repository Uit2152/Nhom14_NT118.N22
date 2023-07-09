package com.example.doan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.doan.databinding.ActivityCategoryBinding;
import com.example.doan.databinding.ActivityListOchapterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOChapterActivity extends AppCompatActivity {

    private ActivityListOchapterBinding binding;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private int  novelID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityListOchapterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.rvlistOfchapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        novelID= intent.getIntExtra("story_id", 1);

        loadChapters( novelID);

    }

    private void loadChapters(int novelID) {
        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ChuongTruyen");

        // Lọc danh sách chương theo ID truyện được chọn
        myRef.orderByChild("maT").equalTo(novelID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ChuongTruyen> chaptersList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChuongTruyen chapter = snapshot.getValue(ChuongTruyen.class);
                    chaptersList.add(chapter);
                }
                displayStoryList(chaptersList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    // Phương thức displayStoryList() để hiển thị danh sách truyện lên RecyclerView
    private void displayStoryList(List<ChuongTruyen> chaptersList) {
        listOChapterAdapter adapter = new listOChapterAdapter(chaptersList);
        recyclerView.setAdapter(adapter);
    }
}