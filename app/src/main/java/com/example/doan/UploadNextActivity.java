package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.databinding.ActivityUploadNextBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UploadNextActivity extends AppCompatActivity{

    RecyclerView recyclerChap;
    TextView nxttentruyen, nxttacgia, nxttinhtrang, nxtsochuong;
    ImageView nxtImage;
    List<Chapter> chapList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    private ActivityUploadNextBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUploadNextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerChap=findViewById(R.id.recyclerChap);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(UploadNextActivity.this,1 );
        recyclerChap.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder=new AlertDialog.Builder(UploadNextActivity.this);
        builder.setCancelable(false);
        //builder.setView(R.layout.progress_layout);
        AlertDialog dialog=builder.create();
        dialog.show();

        chapList= new ArrayList<>();

        UploadNextAdapter adapter= new UploadNextAdapter(UploadNextActivity.this,chapList );
        recyclerChap.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("ChuongTruyen");
        dialog.show();

        Story story=new Story();
        eventListener=databaseReference.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chapList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Chapter chuong= itemSnapshot.getValue(Chapter.class);
                    if(chuong.getMaT()== story.getMaT()){
                    chapList.add(chuong);}
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        }));



        nxttentruyen=findViewById(R.id.nxttentruyen);
        nxttacgia=findViewById(R.id.nxttacgia);
        nxttinhtrang=findViewById(R.id.nxttinhtrang);
        nxtsochuong=findViewById(R.id.nxtsochuong);
        nxtImage=findViewById(R.id.nxtImage);

        Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            nxttentruyen.setText(bundle.getString("Tên truyện"));
            nxttacgia.setText(bundle.getString("Tác giả"));
            nxttinhtrang.setText(bundle.getString("Tình trạng"));
            String chapterNumbers = String.valueOf(bundle.getInt("Số chương"));
            nxtsochuong.setText(chapterNumbers);
        }

        //handle click backSUp( go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  onBackPressed();
                                              }
                                          }
        );

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadNextActivity.this, UploadChapterActivity.class));

            }
        });
    }

}
