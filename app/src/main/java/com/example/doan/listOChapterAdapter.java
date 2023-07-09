package com.example.doan;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class listOChapterAdapter extends RecyclerView.Adapter<listOChapterAdapter.MyViewHolder>{


        private List<ChuongTruyen>  chaptersList;

        public listOChapterAdapter(List<ChuongTruyen> chapterslist) {
            this.chaptersList = chapterslist;
        }


        @NonNull
        @Override
        public listOChapterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_list_chapter, parent, false);
            return new listOChapterAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull listOChapterAdapter.MyViewHolder holder, int position) {
            if (chaptersList == null || chaptersList.isEmpty()) {
                return;
            }
            ChuongTruyen chapter = chaptersList.get(position);
            if (chapter != null) {

                holder.tenchuongTV.setText(chapter.getTenC());
                holder.sochuongTV.setText(String.valueOf(chapter.getMaC()));

                holder.parentCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Truyền dữ liệu về truyện qua intent
                        Intent intent = new Intent(v.getContext(), ReadActivity.class);
                        intent.putExtra("story_id",(int)chapter.getMaT() );
                        intent.putExtra("chapterID",(int)chapter.getMaC());
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return chaptersList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tenchuongTV, sochuongTV;
            CardView parentCV;
            public MyViewHolder(View itemView) {
                super(itemView);
                parentCV= itemView.findViewById(R.id.recCard1);

                tenchuongTV = itemView.findViewById(R.id.tenchap);
                tenchuongTV.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tenchuongTV.setSelected(true);
                tenchuongTV.setSingleLine();

                sochuongTV = itemView.findViewById(R.id.chuong);

            }
        }
}
