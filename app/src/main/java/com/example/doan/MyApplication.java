package com.example.doan;

import android.app.Application;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MyApplication extends Application {

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