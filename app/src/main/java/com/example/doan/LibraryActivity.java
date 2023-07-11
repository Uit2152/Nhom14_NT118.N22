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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    //view binding
    private ActivityLibraryActibityBinding binding;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    List<Story> storyList = new ArrayList<>();
    private boolean isHistoryClicked = false;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        DatabaseReference myRef = database.getReference("Truyen");
        myRef.orderByChild("views").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

        DatabaseReference docRef = database.getReference("DocTruyen");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String uid = currentUser.getUid();
        Query query = docRef.orderByKey().equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Story> readStoryList = new ArrayList<>();
                for (DataSnapshot uidSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot maTSnapshot : uidSnapshot.getChildren()) {
                        int maT = maTSnapshot.child("maT").getValue(Integer.class);
                        // Lấy các thuộc tính khác của maTSnapshot tương tự như trên
                        for (Story story : storyList) {
                            if (story.getmaT() == maT) {
                                readStoryList.add(story);
                                break;
                            }
                        }
                    }
                }
                LibraryAdapter adapter = new LibraryAdapter(readStoryList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });





        binding.historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyList != null) {
                    DatabaseReference docRef = database.getReference("DocTruyen");
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = auth.getCurrentUser();
                    String uid = currentUser.getUid();
                    Query query = docRef.orderByKey().equalTo(uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Story> readStoryList = new ArrayList<>();
                            for (DataSnapshot uidSnapshot : dataSnapshot.getChildren()) {
                                for (DataSnapshot maTSnapshot : uidSnapshot.getChildren()) {
                                    int maT = maTSnapshot.child("maT").getValue(Integer.class);
                                    // Lấy các thuộc tính khác của maTSnapshot tương tự như trên
                                    for (Story story : storyList) {
                                        if (story.getmaT() == maT) {
                                            readStoryList.add(story);
                                            break;
                                        }
                                    }
                                }
                            }
                            LibraryAdapter adapter = new LibraryAdapter(readStoryList);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
            }
        });


        binding.bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyList != null) {
                    DatabaseReference docRef = database.getReference("DocTruyen");
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = auth.getCurrentUser();
                    String uid = currentUser.getUid();
                    Query query = docRef.orderByKey().equalTo(uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Story> readStoryList = new ArrayList<>();
                            for (DataSnapshot uidSnapshot : dataSnapshot.getChildren()) {
                                for (DataSnapshot maTSnapshot : uidSnapshot.getChildren()) {
                                    int maT = maTSnapshot.child("maT").getValue(Integer.class);
                                    int dd = maTSnapshot.child("dd").getValue(Integer.class);
                                    if (dd == 1) {
                                        for (Story story : storyList) {
                                            if (story.getmaT() == maT) {
                                                readStoryList.add(story);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            LibraryAdapter adapter = new LibraryAdapter(readStoryList);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
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