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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NovelDetailsActivity extends AppCompatActivity {
    private ActivityNoveldetailsBinding binding;
    Context context = NovelDetailsActivity.this;
    private String novelTitle;
    private int novelID;
    private FirebaseAuth firebaseAuth;
    private DocTruyen docTruyen;
    private  HashMap<String, Object> hashMap;

    private  RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoveldetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        novelID= intent.getIntExtra("story_id", 1);

        firebaseAuth = FirebaseAuth.getInstance();
        docTruyen= new DocTruyen(firebaseAuth.getUid(), (int)novelID,0,0,0,0,0,String.valueOf(System.currentTimeMillis()));
        hashMap= new HashMap<>();

        loadNovelDetails();
        loadDocTruyen();
        recyclerView = findViewById(R.id.rvchapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        binding.backBT.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View view)
                                              {
                                                  DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DocTruyen");
                                                  ref.child(firebaseAuth.getUid()).child(String.valueOf(novelID)).setValue(hashMap);
                                                  onBackPressed();
                                              }
                                          }
        );
        binding.readBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {


                docTruyen.setChuongDD(1);
                hashMap.put("chuongDD", docTruyen.getChuongDD());

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DocTruyen");
                ref.child(firebaseAuth.getUid()).child(String.valueOf(novelID)).setValue(hashMap);

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

                                                  docTruyen.setyt(1);
                                                  binding.likeIB.setVisibility(View.INVISIBLE);
                                                  binding.like2IB.setVisibility(View.VISIBLE);
                                                  hashMap.put("yt", docTruyen.getyt());
                                              }
                                          }
        );
        binding.like2IB.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View view)
                                              {
                                                      docTruyen.setyt(0);
                                                      binding.likeIB.setVisibility(View.VISIBLE);
                                                      binding.like2IB.setVisibility(View.INVISIBLE);
                                                      hashMap.put("yt", docTruyen.getyt());
                                              }
                                          }
        );
        binding.chatIB.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View view)
                                              {
                                                //bỏ
                                              }
                                          }
        );

        binding.exclusiveIB.setOnClickListener(new View.OnClickListener()
                                               {
                                                   @Override
                                                   public void onClick(View view)
                                                   {
                                                       if(docTruyen.getdc()==0)
                                                       {
                                                           docTruyen.setdc(1);
                                                           binding.exclusiveIB.setBackgroundResource(R.color.new_background_color);
                                                       }
                                                       else
                                                       {
                                                           docTruyen.setdc(0);
                                                           binding.exclusiveIB.setBackgroundResource(R.color.transparent);
                                                       }

                                                       hashMap.put("dc", docTruyen.getdc());
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
        refNovel.orderByChild("maT").equalTo(novelID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get data from firebase
                if (snapshot.exists()) {
                    for (DataSnapshot novelSnapshot : snapshot.getChildren()) {
                        String novelTitle = "" + novelSnapshot.child("tenTV").getValue();
                        String image = "" + novelSnapshot.child("image").getValue();
                        String description = "" + novelSnapshot.child("mota").getValue();
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



    private void loadDocTruyen()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DocTruyen");

        // Lọc danh sách chương theo ID truyện được chọn
        myRef.child(firebaseAuth.getUid()).child(String.valueOf(novelID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    docTruyen= dataSnapshot.getValue(DocTruyen.class);
                }
                else {

                }

                if(docTruyen.getyt()==0)
                {
                    binding.likeIB.setVisibility(View.VISIBLE);
                    binding.like2IB.setVisibility(View.INVISIBLE);
                }
                else
                {
                    binding.likeIB.setVisibility(View.INVISIBLE);
                    binding.like2IB.setVisibility(View.VISIBLE);
                }

                if(docTruyen.getdc()==0)
                {
                    binding.exclusiveIB.setBackgroundResource(R.color.transparent);
                }
                else
                {
                    binding.exclusiveIB.setBackgroundResource(R.color.new_background_color);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
        hashMap.put("uid", docTruyen.getUid());
        hashMap.put("maT", docTruyen.getmaT());
        hashMap.put("dc", docTruyen.getdc());
        hashMap.put("yt", docTruyen.getyt());
        hashMap.put("dg", docTruyen.getdg());
        hashMap.put("dc", docTruyen.getdc());
        hashMap.put("chuongDD", docTruyen.getChuongDD());
        hashMap.put("timestamp", docTruyen.getTimestamp());

    }
}