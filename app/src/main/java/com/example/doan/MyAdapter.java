package com.example.doan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<Story> storyList;

    public MyAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Glide.with(context).load(storyList.get(position).getDataImage()).into(holder.recImage);
        holder.rectentruyen.setText(storyList.get(position).gettenTV());
        holder.rectacgia.setText(storyList.get(position).gettacgiaTV());
        holder.rectinhtrang.setText(storyList.get(position).gettinhtrangTV());
        holder.recsochuong.setText(String.valueOf(storyList.get(position).getsochuongTV()));

        holder.recCard.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, UploadNextActivity.class);
                intent.putExtra ("Tên truyện",storyList.get(holder.getAdapterPosition()).gettenTV());
                intent.putExtra ("Tác giả",storyList.get(holder.getAdapterPosition()).gettacgiaTV());
                intent.putExtra ("Tình trạng",storyList.get(holder.getAdapterPosition()).gettinhtrangTV());
                intent.putExtra ("Số chương",storyList.get(holder.getAdapterPosition()).getsochuongTV());

                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView rectentruyen, rectacgia, rectinhtrang, recsochuong;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage=itemView.findViewById(R.id.recImage);
        recCard=itemView.findViewById(R.id.recCard);
        rectentruyen=itemView.findViewById(R.id.rectentruyen);
        rectacgia=itemView.findViewById(R.id.rectacgia);
        rectinhtrang=itemView.findViewById(R.id.rectinhtrang);
        recsochuong=itemView.findViewById(R.id.recsochuong);
    }
}
