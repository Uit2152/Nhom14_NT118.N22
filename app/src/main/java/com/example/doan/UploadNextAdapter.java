package com.example.doan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UploadNextAdapter extends RecyclerView.Adapter<UploadNextAdapter.MyViewHolder> {
    private List<ChuongTruyen> chapterList;

    public UploadNextAdapter(List<ChuongTruyen> chapterList) {
        this.chapterList = chapterList;
    }


    @NonNull
    @Override
    public UploadNextAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_chapter, parent, false);
        return new UploadNextAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadNextAdapter.MyViewHolder holder, int position) {
        if (chapterList == null || chapterList.isEmpty()) {
            return;
        }
        ChuongTruyen chapter = chapterList.get(position);
        if (chapter != null) {
            String tenC = chapter.getTenC() != null ? chapter.getTenC() : "Unknown";
            holder.maC.setText(String.valueOf(chapter.getMaC()));
            holder.tenC.setText(tenC);

            holder.tenC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UpdateChapterActivity.class);
                    intent.putExtra("chapter_id", chapter.getMaC()); // truyền mã chương
                    intent.putExtra("novel_id", chapter.getmaT()); // truyền mã truyện
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView maC, tenC;
        public MyViewHolder(View itemView) {
            super(itemView);
            maC = itemView.findViewById(R.id.maC);
            tenC = itemView.findViewById(R.id.tenC);


        }
    }
}
