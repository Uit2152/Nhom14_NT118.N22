package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        novelID = intent.getIntExtra("story_id",1);
        chapterID= intent.getIntExtra("chapterID",1);
        // chapterID = intent.getStringExtra("chapterID");
        loadPdfBookDetails(chapterID);
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
                startActivity(new Intent(ReadActivity.this, ListOChapterActivity.class));

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

            }
        });
        binding.nextIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //chuyển sang chương tiếp theo
//call loadPdfBookDetails(chapter)
            }
        });
    }

    private void loadPdfBookDetails(int chapterId) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Truyen");

//ref.chiled(novelID).child(chapterID).addListenerForSingleValueEvent....
        ref.child(getString(novelID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String pdfUrl = "" + snapshot.child("url").getValue();
                loadBookFromUrl(pdfUrl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static int MAX_BYTES_PDF = 50000000; // Here Max Size PDF 50MB
    private void loadBookFromUrl(String pdfUrl) {
        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        reference.getBytes(MAX_BYTES_PDF).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                binding.pdfView.fromBytes(bytes).swipeHorizontal(false).onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                }).onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(ReadActivity.this, "" + t.getMessage(),  Toast.LENGTH_SHORT ).show();
                    }
                }).onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(ReadActivity.this, "Error on page "+page + t.getMessage(),  Toast.LENGTH_SHORT ).show();
                    }
                }).load();

                binding.progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

}
