package com.example.doan;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.databinding.ActivityCategoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private int category = 1; // Đặt giá trị mặc định của category là 1 (Tiên hiệp)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle click backSUp( go-back button)
        binding.backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadStoriesByCategory(category);
        // Thiết lập sự kiện cho các TextView chứa tên thể loại
        binding.c1TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 1;
                loadStoriesByCategory(category);
            }
        });

        binding.c2TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 2;
                loadStoriesByCategory(category);
            }
        });

        binding.c3TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 3;
                loadStoriesByCategory(category);
            }
        });

        binding.c4TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 4;
                loadStoriesByCategory(category);
            }
        });

        binding.c5TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 5;
                loadStoriesByCategory(category);
            }
        });

    }

    // Phương thức loadStoriesByCategory() để lấy danh sách truyện theo thể loại
    private void loadStoriesByCategory(int category) {
        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Truyen");

        // Lọc danh sách truyện theo thể loại được chọn
        myRef.orderByChild("maTL").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Story> storyList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Story story = snapshot.getValue(Story.class);
                    storyList.add(story);
                }
                displayStoryList(storyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    // Phương thức displayStoryList() để hiển thị danh sách truyện lên RecyclerView
    private void displayStoryList(List<Story> storyList) {
        CategoryAdapter adapter = new CategoryAdapter(storyList);
        recyclerView.setAdapter(adapter);
    }
}