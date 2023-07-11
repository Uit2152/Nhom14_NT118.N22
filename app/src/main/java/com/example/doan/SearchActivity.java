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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doan.databinding.ActivityHomePageAdminBinding;
import com.example.doan.databinding.ActivitySearchBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;
    private DatabaseReference mUserDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


            mUserDatabase = FirebaseDatabase.getInstance().getReference("Truyen");
            mSearchField = findViewById(R.id.contentsearchTV);
            mSearchBtn = findViewById(R.id.searchIB);

            mResultList = findViewById(R.id.searchRecyclerView);
            mResultList.setHasFixedSize(true);
            mResultList.setLayoutManager(new LinearLayoutManager(this));

            mSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String searchText = mSearchField.getText().toString();

                    firebaseUserSearch(searchText);

                }
            });

        binding.backBT.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    onBackPressed();
                }


        });

        }

        private void firebaseUserSearch(String searchText) {

            Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();




            Query firebaseSearchQuery = mUserDatabase.orderByChild("tenTV").startAt(searchText).endAt(searchText + "\uf8ff");

           firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Story> storyList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Story story = snapshot.getValue(Story.class);
                        storyList.add(story);
                    }
                    CategoryAdapter adapter = new CategoryAdapter(storyList);
                    mResultList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
           });



        }



}


