package com.example.doan;

import android.app.Application;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MyApplication extends Application {

    public static final Users getUser() {
        Users User= new Users();
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        String userID=firebaseAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot novelSnapshot : snapshot.getChildren()) {

                        User.setEmail("" + novelSnapshot.child("email").getValue());
                        User.setUid("" + novelSnapshot.child("uid").getValue());
                        User.setName("" + novelSnapshot.child("name").getValue());
                        User.setProfileImage("" + novelSnapshot.child("profileImage").getValue());
                        User.setUserType("" + novelSnapshot.child("userType").getValue());
                        User.setTimestamp( (Long)novelSnapshot.child("timestamp").getValue());
                    }
                } else {
                    // handle not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return User;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static final String formatTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd/MM/yyyy", calendar).toString();
        return date;
    }
    public static void incrementBookCount(String novelID)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Novels");

        ref.child(novelID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String viewsCount = "" + snapshot.child("viewsCount").getValue();
                if (viewsCount.equals("") || viewsCount.equals("null"))
                {
                    viewsCount="0";
                }
                long newviewCount = Long.parseLong(viewsCount)+1;
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("viewCount",newviewCount);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Novels");

                ref.child(novelID).updateChildren(hashMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}