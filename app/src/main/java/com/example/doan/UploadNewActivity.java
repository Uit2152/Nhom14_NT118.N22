package com.example.doan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivityUploadNewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UploadNewActivity extends AppCompatActivity {
    private ActivityUploadNewBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    EditText txtTenTruyen, txtTacGia, txtSoChuong, txtMoTa, txtMaTL, txtTinhTrang;
    ImageButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txtTenTruyen = findViewById(R.id.txttenTruyen);
        txtTacGia = findViewById(R.id.txttacGia);
        txtMaTL = findViewById(R.id.txtmaTL);
        txtSoChuong = findViewById(R.id.txtsoChuong);
        txtTinhTrang = findViewById(R.id.txttinhtrang);
        txtMoTa = findViewById(R.id.txtmoTa);
        btnSave = findViewById(R.id.btnSave);

        //handle click backSUp( go-back button)
        binding.backBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //handle click save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        // Lấy giá trị từ các trường EditText
        String tenTV = txtTenTruyen.getText().toString().trim();
        String tacgiaTV = txtTacGia.getText().toString().trim();
        String maTLStr = txtMaTL.getText().toString().trim();
        String sochuongStr = txtSoChuong.getText().toString().trim();
        String tinhtrangTV = txtTinhTrang.getText().toString().trim();
        String Mota = txtMoTa.getText().toString().trim();

        // Kiểm tra các trường dữ liệu có bị bỏ trống không
        if (tenTV.isEmpty()) {
            txtTenTruyen.setError("Vui lòng nhập tên truyện");
            txtTenTruyen.requestFocus();
            return;
        }
        if (tacgiaTV.isEmpty()) {
            txtTacGia.setError("Vui lòng nhập tác giả");
            txtTacGia.requestFocus();
            return;
        }
        if (maTLStr.isEmpty()) {
            txtMaTL.setError("Vui lòng nhập mã thể loại");
            txtMaTL.requestFocus();
            return;
        }
        if (sochuongStr.isEmpty()) {
            txtSoChuong.setError("Vui lòng nhập số chương");
            txtSoChuong.requestFocus();
            return;
        }

        // Chuyển đổi giá trị dạng chuỗi sang giá trị dạng số nguyên
        final int maTL;
        try {
            maTL = Integer.parseInt(maTLStr);
        } catch (NumberFormatException e) {
            txtMaTL.setError("Mã thể loại phải là số nguyên");
            txtMaTL.requestFocus();
            return;
        }
        final int sochuongTV;
        try {
            sochuongTV = Integer.parseInt(sochuongStr);
        } catch (NumberFormatException e) {
            txtSoChuong.setError("Số chương phải là số nguyên");
            txtSoChuong.requestFocus();
            return;
        }

        // Truy vấn cơ sở dữ liệu Firebase Realtime Database để lấy danh sách các truyện hiện có trong bảng Truyen
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Truyen");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Story> stories = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Story story = snapshot.getValue(Story.class);
                    stories.add(story);
                }

                // Tìm ra MaT lớn nhất trong danh sách các truyện hiện có
                int maxMaT = 0;
                for (Story story : stories) {
                    if (story.getmaT() > maxMaT) {
                        maxMaT = story.getmaT();
                    }
                }

                // Tăng giá trị của maxMaT lên 1 để lấy MaT tiếp theo
                int maT = maxMaT + 1;

                String image="";
                String uid="";
                String timestamp="";

                Story truyen= new Story(maT, tenTV, tacgiaTV, tinhtrangTV, sochuongTV, Mota,timestamp, maTL, image, uid);
                // Thêm truyện mới vào bảng "Truyen" trên Firebase
                DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("Truyen");
                newRef.push().setValue(truyen);

                // Hiển thị ProgressDialog
                progressDialog = new ProgressDialog(UploadNewActivity.this);
                progressDialog.setMessage("Đang tải lên...");
                progressDialog.show();

                // Ẩn ProgressDialog sau khi tải lên thành công
                newRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        // Đóng UploadNewActivity sau khi tải lên thành công
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }
}