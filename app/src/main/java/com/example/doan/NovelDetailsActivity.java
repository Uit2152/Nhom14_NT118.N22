package com.example.doan;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.databinding.ActivityNoveldetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntToLongFunction;

public class NovelDetailsActivity extends AppCompatActivity {
    private ActivityNoveldetailsBinding binding;
    Context context = NovelDetailsActivity.this;
    private String novelTitle;
    private int novelID;
    DocTruyen docTruyen;
    Users User;
    private  RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoveldetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        novelID= intent.getIntExtra("story_id", 1);

        loadNovelDetails();
        recyclerView = findViewById(R.id.rvchapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        User=MyApplication.getUser();
        docTruyen= new DocTruyen(User.getUid(), (int)novelID,0,0,0,0,0,String.valueOf(System.currentTimeMillis()));
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
                docTruyen.setChuongDD(1);
                Intent intent1 = new Intent(NovelDetailsActivity.this, ReadActivity.class);
                intent1.putExtra("story_id",(int)novelID );
                intent1.putExtra("chapterID",(int)1);
                startActivity(intent1);

            }
        });
        binding.likeIB.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View view)
                                              {
                                                  docTruyen.setYT(1);
                                              }
                                          }
        );
        binding.chatIB.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View view)
                                              {
                                                  docTruyen.setDG(1);
                                              }
                                          }
        );

        binding.exclusiveIB.setOnClickListener(new View.OnClickListener()
                                               {
                                                   @Override
                                                   public void onClick(View view)
                                                   {
                                                       docTruyen.setDC(1);
                                                   }
                                               }
        );

        binding.introduceBt.setOnClickListener(new View.OnClickListener()
                                               {
                                                   @Override
                                                   public void onClick(View view)
                                                   {
                                                       binding.summaryTV.setVisibility(View.VISIBLE);
                                                       binding.rvchapter.setVisibility(View.GONE);
                                                   }
                                               }
        );
        binding.listchaptersBt.setOnClickListener(new View.OnClickListener()
                                               {
                                                   @Override
                                                   public void onClick(View view)
                                                   {
                                                       loadChapters( novelID);
                                                       binding.summaryTV.setVisibility(View.GONE);
                                                       binding.rvchapter.setVisibility(View.VISIBLE);
                                                   }
                                               }
        );
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

                        binding.summaryTV.setMovementMethod(new ScrollingMovementMethod());
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