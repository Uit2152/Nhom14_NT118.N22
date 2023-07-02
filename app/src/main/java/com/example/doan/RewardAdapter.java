package com.example.doan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {

    private List<Story> storyList;

    public RewardAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_reward, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
            String imageUrl = story.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(holder.itemView.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.default_image)
                        .into(holder.image);
            } else {
                holder.image.setImageResource(R.drawable.default_image);
            }

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Truyền dữ liệu về truyện qua intent
                    Intent intent = new Intent(v.getContext(), NovelDetailsActivity.class);
                    intent.putExtra("story_id", story.getMaT()); // ví dụ truyền ID của truyện
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenTV, tacgiaTV, tinhtrangTV, sochuongTV;
        ImageButton image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tenTV = itemView.findViewById(R.id.tenTV);
            tacgiaTV = itemView.findViewById(R.id.tacgiaTV);
            tinhtrangTV = itemView.findViewById(R.id.tinhtrangTV);
            sochuongTV = itemView.findViewById(R.id.sochuongTV);
            image = itemView.findViewById(R.id.image);

        }
    }
}

