package com.example.doan;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.databinding.ActivityRewardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RewardActivity extends AppCompatActivity {

    private ActivityRewardBinding binding;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private List<Story> storyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRewardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.rewardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Truyen");

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storyList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Story story = snapshot.getValue(Story.class);
                    storyList.add(story);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        binding.c1TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyList != null) {
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
            }
        });


        binding.c2TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyList != null) {
                    DatabaseReference docRef = database.getReference("DocTruyen");
                    docRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Story> sortedStoryList = new ArrayList<>();
                            for (Story story : storyList) {
                                int views = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    DocTruyen docTruyen = snapshot.getValue(DocTruyen.class);
                                    if (docTruyen.getMaT() == story.getMaT() && docTruyen.getDC()==1) {
                                        views++;
                                    }
                                }
                                story.setViews(views);
                                sortedStoryList.add(story);
                            }

                            Collections.sort(sortedStoryList, new Comparator<Story>() {
                                @Override
                                public int compare(Story s1, Story s2) {
                                    return s2.getViews() - s1.getViews();
                                }
                            });

                            RewardAdapter adapter = new RewardAdapter(sortedStoryList);
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

        binding.c3TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyList != null) {
                    DatabaseReference docRef = database.getReference("DocTruyen");
                    docRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<Story> sortedStoryList = new ArrayList<>();
                            for (Story story : storyList) {
                                int views = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    DocTruyen docTruyen = snapshot.getValue(DocTruyen.class);
                                    if (docTruyen.getMaT() == story.getMaT() && docTruyen.getYT()==1) {
                                        views++;
                                    }
                                }
                                story.setViews(views);
                                sortedStoryList.add(story);
                            }

                            Collections.sort(sortedStoryList, new Comparator<Story>() {
                                @Override
                                public int compare(Story s1, Story s2) {
                                    return s2.getViews() - s1.getViews();
                                }
                            });

                            RewardAdapter adapter = new RewardAdapter(sortedStoryList);
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




    }
    private void displayStoryList(RecyclerView recyclerView,List<Story> storyList) {
        CategoryAdapter adapter = new CategoryAdapter(storyList);
        recyclerView.setAdapter(adapter);
    }
}