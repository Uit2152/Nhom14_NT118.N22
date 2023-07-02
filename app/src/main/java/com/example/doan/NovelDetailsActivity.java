package com.example.doan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.doan.databinding.ActivityNoveldetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NovelDetailsActivity extends AppCompatActivity {
    private ActivityNoveldetailsBinding binding;
    Context context = NovelDetailsActivity.this;
    private String novelTitle;
    private int novelID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoveldetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        novelID= intent.getIntExtra("story_id", 1);

        loadNovelDetails();

        binding.backBT.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View view)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );
        binding.readBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent1 = new Intent(NovelDetailsActivity.this, ReadActivity.class);
                intent1.putExtra("novelID",novelID);
                intent1.putExtra("chapterID",1);
                startActivity(intent1);

            }
        });
    }
    private void loadNovelDetails(){
        if (novelID == 0) {
            // handle null novelID
            return;
        }
        DatabaseReference refNovel = FirebaseDatabase.getInstance().getReference("Truyen");
        refNovel.orderByChild("MaT").equalTo(novelID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get data from firebase
                if (snapshot.exists()) {
                    for (DataSnapshot novelSnapshot : snapshot.getChildren()) {
                        String novelTitle = "" + novelSnapshot.child("tenTV").getValue();
                        String image = "" + novelSnapshot.child("image").getValue();
                        String description = "" + novelSnapshot.child("Mota").getValue();
                        String timestamp = "" + novelSnapshot.child("timestamp").getValue();
                        String categoryId = "" + novelSnapshot.child("maTL").getValue(); // truy cập đến trường dữ liệu maTL
                        String author = "" + novelSnapshot.child("tacgiaTV").getValue();
                        String status = "" + novelSnapshot.child("tinhtrangTV").getValue();
                        String chapterNumbers = "" + novelSnapshot.child("sochuongTV").getValue();

                        //set data
                        Glide(false, context, image, binding.imageIV);
                        binding.tenTV.setText(novelTitle);
                        binding.summaryTV.setText(description);
                        binding.tacgiaTV.setText(author);
                        binding.sochuongTV.setText(chapterNumbers);
                        binding.tinhtrangTV.setText(status);

                        DatabaseReference refCategory = FirebaseDatabase.getInstance().getReference("Theloai");
                        refCategory.child(categoryId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String category = "" + snapshot.child("tenTL").getValue();
                                binding.theloaiTV.setText(category);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // handle error
                            }
                        });
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
    }
    public void Glide(Boolean isUser, Context context, String Url, ImageView Image) {
        try {
            Glide.with(context).load(Url).placeholder(R.color.image_profile).into(Image);

        } catch (Exception e) {
            Image.setImageResource(R.drawable.basic_book);
        }
    }


}