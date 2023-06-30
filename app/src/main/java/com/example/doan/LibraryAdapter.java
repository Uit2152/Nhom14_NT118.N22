package com.example.doan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder>{
    private List<Story> storyList;

    public LibraryAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }


    @NonNull
    @Override
    public LibraryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_library, parent, false);
        return new LibraryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryAdapter.MyViewHolder holder, int position) {
        if (storyList == null || storyList.isEmpty()) {
            return;
        }
        Story story = storyList.get(position);
        if (story != null) {
            String name = story.gettenTV() != null ? story.gettenTV() : "Unknown";
            holder.tenTV.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenTV, tacgiaTV, tinhtrangTV, sochuongTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            tenTV = itemView.findViewById(R.id.tenTV);
        }
    }

}
