package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Toast;


import com.example.doan.databinding.ActivityReadBinding;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ReadActivity extends AppCompatActivity {
    //view binding
    private ActivityReadBinding binding;
    Context context = ReadActivity.this;

   int novelID;
    int chapterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        novelID= intent.getIntExtra("story_id", 1);
        chapterID= intent.getIntExtra("chapterID",1);

        loadNovelDetails( chapterID);
       // MyApplication.incrementBookCount(novelID);

        binding.backBT.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );
        binding.listIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(ReadActivity.this, ListOChapterActivity.class);
                intent1.putExtra("story_id",(int)novelID );
                startActivity(intent1);
            }
        });
        binding.chatIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ReadActivity.this, CommentActivity.class));

            }
        });
        binding.previousIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //chuyền sang chương liền trước
                loadNovelDetails( chapterID-1);
            }
        });
        binding.nextIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //chuyển sang chương tiếp theo
                loadNovelDetails( chapterID+1);

            }
        });
    }


    public static int MAX_BYTES_PDF = 50000000; // Here Max Size PDF 50MB
    private void loadNovelDetails(int chuongtruyen){
        if (novelID == 0) {
            // handle null novelID
            return;
        }
        DatabaseReference refNovel = FirebaseDatabase.getInstance().getReference("ChuongTruyen");
        refNovel.orderByChild("maC").equalTo(chuongtruyen).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get data from firebase
                if (snapshot.exists()) {

                    for (DataSnapshot novelSnapshot : snapshot.getChildren()) {
                        ChuongTruyen chuong= novelSnapshot.getValue(ChuongTruyen.class);
                        assert chuong != null;
                        if( chuong.getMaT()== novelID ) {
                            String noidung = "" + novelSnapshot.child("ND").getValue();
                            String tenC = "" + novelSnapshot.child("tenC").getValue();

                            //set data

                            binding.chapternameTV.setSingleLine(true);
                            binding.chapternameTV.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                            binding.chapternameTV.setSelected(true);
                            binding.chapternameTV.setText(tenC);

                            binding.readTV.setMovementMethod(new ScrollingMovementMethod());
                            binding.readTV.setText(noidung);
                            break;
                        }

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
        binding.progressBar.setVisibility(View.GONE);
    }

}
