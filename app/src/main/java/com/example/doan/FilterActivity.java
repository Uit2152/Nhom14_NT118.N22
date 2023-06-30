package com.example.doan;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.databinding.ActivityFilterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private ActivityFilterBinding binding;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FilterAdapter adapter;
    private List<Story> storyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.filterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo kết nối tới Firebase và lấy dữ liệu truyện theo yêu cầu
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Truyen");

        myRef.orderByChild("views").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storyList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Story story = snapshot.getValue(Story.class);
                    storyList.add(story);
                }

                // Hiển thị danh sách truyện ban đầu
                displayStoryList(storyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút "Lọc"
        binding.Xong.setOnClickListener(v -> {
            String tinhTrang = "";
            RadioGroup radioGroup = findViewById(R.id.TinhTrang);
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId != -1) {
                RadioButton radioButton = findViewById(checkedRadioButtonId);
                String text = radioButton.getText().toString();
                if (text.equals("Hoàn thành")) {
                    tinhTrang = "full";
                } else if (text.equals("Đang ra")) {
                    tinhTrang = "not full";
                }
            }

            RadioButton radioButton1 = findViewById(R.id.TH);
            radioButton1.setTag(1); // thể loại "Hành động"
            RadioButton radioButton2 = findViewById(R.id.KH);
            radioButton2.setTag(2); // thể loại "Tình cảm"
            RadioButton radioButton3 = findViewById(R.id.NT);
            radioButton3.setTag(3); // thể loại "Kinh dị"
            RadioButton radioButton4 = findViewById(R.id.DT);
            radioButton4.setTag(4); // thể loại "Viễn tưởng"
            RadioButton radioButton5 = findViewById(R.id.TT);
            radioButton5.setTag(5); // thể loại "Thần thoại"

            RadioGroup radioGroup1 = findViewById(R.id.TheLoai);
            int selectedId = radioGroup1.getCheckedRadioButtonId();
            int maTL = 0;
            if (selectedId != -1) {
                RadioButton radioButton = findViewById(selectedId);
                maTL = Integer.parseInt(radioButton.getTag().toString());
            }

            int minChapter = 0;
            int maxChapter = 0;

            // Lấy giá trị của RadioGroup SoChuong
            RadioGroup chapterGroup = findViewById(R.id.SoChuong);
            int selectedChapterId = chapterGroup.getCheckedRadioButtonId();

            // Kiểm tra giá trị của RadioGroup SoChuong và gán giá trị tương ứng cho minChapter và maxChapter
            switch (selectedChapterId) {
                case R.id.Duoi100:
                    maxChapter = 100;
                    break;
                case R.id.Duoi500:
                    minChapter = 100;
                    maxChapter = 500;
                    break;
                case R.id.Duoi1000:
                    minChapter = 500;
                    maxChapter = 1000;
                    break;
                case R.id.Tren1000:
                    minChapter = 1000;
                    maxChapter = Integer.MAX_VALUE;
                    break;
            }

            // Lọc danh sách truyện dựa trên các giá trị được chọn
            List<Story> filteredList = filterData(tinhTrang, maTL, minChapter, maxChapter);

            // Cập nhật danh sách truyện được hiển thị trong Adapter
            adapter.updateData(filteredList);
        });

    }

    // Hàm hiển thị danh sách truyện
    private void displayStoryList(List<Story> storyList) {
        adapter = new FilterAdapter(storyList);
        recyclerView.setAdapter(adapter);
    }

    // Hàm lọc danh sách truyện dựa trên các thuộc tính được chọn
    private List<Story> filterData(String tinhTrang, int maTL, int minChapter, int maxChapter) {
        List<Story> filteredList = new ArrayList<>();
        for (Story story : storyList) {
            // Kiểm tra trạng thái
            if (!tinhTrang.isEmpty() && !story.gettinhtrangTV().equals(tinhTrang)) {
                continue;
            }

            // Kiểm tra thể loại
            if (maTL != 0 && story.getMaTL() != maTL) {
                continue;
            }
            // Kiểm tra số lượng chương
            int soChuong = story.getsochuongTV();
            if (soChuong < minChapter || soChuong > maxChapter) {
                continue;
            }
            filteredList.add(story);
        }
        return filteredList;
    }
}