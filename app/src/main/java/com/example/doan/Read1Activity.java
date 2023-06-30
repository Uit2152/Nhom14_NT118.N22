package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.doan.databinding.ActivityHomePageAdminBinding;
import com.example.doan.databinding.ActivityRead1Binding;
import com.google.firebase.auth.FirebaseAuth;

public class Read1Activity extends AppCompatActivity {
    //view binding
    private ActivityRead1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRead1Binding.inflate(getLayoutInflater());
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
        binding.listIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Read1Activity.this, ListOChaptersActivity.class));

            }
        });
        binding.chatIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Read1Activity.this, CommentActivity.class));

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

            }
        });
    }

}
