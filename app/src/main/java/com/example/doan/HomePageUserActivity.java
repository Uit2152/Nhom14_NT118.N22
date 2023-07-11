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

import com.example.doan.databinding.ActivityHomePageAdminBinding;
import com.example.doan.databinding.ActivityHomePageUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePageUserActivity extends AppCompatActivity {

    //view binding
    private ActivityHomePageUserBinding binding;
    private RecyclerView recyclerView1,recyclerView2 ;
    //firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();

        recyclerView1 = findViewById(R.id.likeNovelRV);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        loadStoriesByViews(recyclerView1);


        recyclerView2 = findViewById(R.id.completeNovelRV);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        loadStoriesByStatus(recyclerView2,"full");



        binding.categoryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, CategoryActivity.class));

            }
        });

        binding.rewardIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, RewardActivity.class));

            }
        });
        binding.filterIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, FilterActivity.class));

            }
        });

        binding.libraryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, LibraryActivity.class));

            }
        });
        binding.homeIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this,HomePageUserActivity.class));

            }
        });
        binding.accountIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, AccountActivity.class));

            }
        });
        binding.xemThem1TV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this,  RewardActivity.class));

            }
        });
        binding.xemThem2TV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, FilterActivity.class));

            }
        });
        binding.contentsearchTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, SearchActivity.class));

            }
        });
        binding.searchIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomePageUserActivity.this, SearchActivity.class));

            }
        });
    }
    private void checkUser()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            startActivity(new Intent(HomePageUserActivity.this, MainActivity.class));
            finish();
        }
        else {
            String email= firebaseUser.getEmail();
            binding.subTitleTv.setText(email);
        }
    }

    private void loadStoriesByStatus( RecyclerView recyclerView, String status) {
        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Truyen");

        // Lọc danh sách truyện theo thể loại được chọn
        myRef.orderByChild("tinhtrangTV").equalTo(status).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Story> storyList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Story story = snapshot.getValue(Story.class);
                    storyList.add(story);
                }
                displayStoryList(recyclerView,storyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    private void loadStoriesByViews( RecyclerView recyclerView) {
        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Truyen");

        // Lọc danh sách truyện theo thể loại được chọn
        myRef.orderByChild("Views").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Story> storyList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Story story = snapshot.getValue(Story.class);
                    if(story.getViews()>9)
                    {
                        storyList.add(story);
                    }


                }
                displayStoryList(recyclerView,storyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    // Phương thức displayStoryList() để hiển thị danh sách truyện lên RecyclerView
    private void displayStoryList(RecyclerView recyclerView,List<Story> storyList) {
        CategoryAdapter adapter = new CategoryAdapter(storyList);
        recyclerView.setAdapter(adapter);
    }
}