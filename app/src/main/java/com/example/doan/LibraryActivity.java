package com.example.doan;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.databinding.ActivityLibraryActibityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    //view binding
    private ActivityLibraryActibityBinding binding;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    //firebase auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibraryActibityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBT.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );


        recyclerView = findViewById(R.id.LibraryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Truyen");

        myRef.orderByChild("views").addValueEventListener(new ValueEventListener() {
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


        binding.libraryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LibraryActivity.this, LibraryActivity.class));

            }
        });
        binding.homeIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LibraryActivity.this,HomePageUserActivity.class));

            }
        });
        binding.accountIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LibraryActivity.this, AccountActivity.class));

            }
        });
    }
    private void displayStoryList(List<Story> storyList) {
        LibraryAdapter adapter = new LibraryAdapter(storyList);
        recyclerView.setAdapter(adapter);
    }

}