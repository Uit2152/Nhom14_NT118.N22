package com.example.doan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Story> storyList;

    public CategoryAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }


    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);
        return new CategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        if (storyList == null || storyList.isEmpty()) {
            return;
        }
        Story story = storyList.get(position);
        if (story != null) {
            String name = story.gettenTV() != null ? story.gettenTV() : "Unknown";
            holder.tenTV.setText(name);
            holder.tacgiaTV.setText(story.gettacgiaTV());
            holder.tinhtrangTV.setText(story.gettinhtrangTV());

            int sochuong = story.getsochuongTV();
            holder.sochuongTV.setText(String.valueOf(sochuong));
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
            tacgiaTV = itemView.findViewById(R.id.tacgiaTV);
            tinhtrangTV = itemView.findViewById(R.id.tinhtrangTV);
            sochuongTV = itemView.findViewById(R.id.sochuongTV);
        }
    }
}
