package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.doan.databinding.ActivityNoveldetailsBinding;
import com.example.doan.databinding.ActivityReadBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NovelDetailsActivity extends AppCompatActivity {
    private ActivityNoveldetailsBinding binding;
    Context context = NovelDetailsActivity.this;
    private String novelTitle;
    private String novelID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoveldetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        novelID= intent.getStringExtra("story_id");

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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Truyen");
        ref.child(novelID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get data from firebase
                novelTitle = "" + snapshot.child("tenTV").getValue();
                String image = "" + snapshot.child("image").getValue();
                String description = "" + snapshot.child("Mota").getValue();
                String timestamp = "" + snapshot.child("timestamp").getValue();


                String categoryId = "" + snapshot.child("maTL").getValue();
                String author = "" + snapshot.child("tacgiaTV").getValue();
                String status = "" + snapshot.child("tinhtrangTV").getValue();
                String chapterNumbers = "" + snapshot.child("sochuongTV").getValue();

                //format date
                String date = MyApplication.formatTimestamp(Long.parseLong(timestamp));


                //set data
                Glide(false, context, image, binding.imageIV);

                binding.tenTV.setText(novelTitle);

                binding.summaryTV.setText(description);

                binding.tacgiaTV.setText(author);
                binding.sochuongTV.setText(chapterNumbers);
                binding.tinhtrangTV.setText(status);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Theloai");
                ref.child(categoryId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String category = "" + snapshot.child("tenTL").getValue();
                        binding.theloaiTV.setText(category);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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