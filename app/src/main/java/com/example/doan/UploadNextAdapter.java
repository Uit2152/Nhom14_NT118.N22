package com.example.doan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UploadNextAdapter extends RecyclerView.Adapter<UploadNextViewHolder> {
    private Context context;
    private List<ChuongTruyen> chapList;

    public UploadNextAdapter(Context context, List<ChuongTruyen> chapList) {
        this.context = context;
        this.chapList = chapList;
    }

    @NonNull
    @Override
    public UploadNextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_chapter, parent, false);
        return new UploadNextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadNextViewHolder holder, int position) {
        holder.chuong.setText(String.valueOf(chapList.get(position).getMaC()));
        holder.tenchap.setText(chapList.get(position).getTenC());

        holder.recCard1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, UploadChapterActivity.class);
                intent.putExtra ("Chương",chapList.get(holder.getAdapterPosition()).getMaC());
                intent.putExtra ("Tên chương",chapList.get(holder.getAdapterPosition()).getTenC());

                context.startActivity(intent);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return chapList.size();
    }
}

class UploadNextViewHolder extends RecyclerView.ViewHolder {

    TextView chuong, tenchap;
    CardView recCard1;
    public UploadNextViewHolder(@NonNull View itemView) {
        super(itemView);

        chuong=itemView.findViewById(R.id.chuong);
        tenchap= itemView.findViewById(R.id.tenchap);
        recCard1=itemView.findViewById(R.id.recCard1);
    }
}
