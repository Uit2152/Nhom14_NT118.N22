package com.example.doan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivitySetingAcactivityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SetingACActivity extends AppCompatActivity {
    private ActivitySetingAcactivityBinding binding;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef; // Thêm biến để lưu trữ Firebase Storage
    //firebase auth
    private FirebaseAuth firebaseAuth;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button chooseImageButton;
    private ImageView imageView;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetingAcactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mStorageRef = FirebaseStorage.getInstance().getReference("profile_images"); // Khởi tạo tham chiếu Firebase Storage
        binding.backBT.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  onBackPressed();
                                              }
                                          }
        );
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            binding.nameEditText.setText(name);
            binding.emailEditText.setText(email);
        }
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tên, email, mật khẩu và URL ảnh đại diện của người dùng
                String name = binding.nameEditText.getText().toString();
                String email = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                long timestamp = System.currentTimeMillis();
                String profileImage = ""; // Lấy URL của ảnh đại diện

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Users user = dataSnapshot.getValue(Users.class);
                            String userType = user.getUserType();

                            Users newUser = new Users(uid, email, name, profileImage, userType, timestamp);

                            mDatabase.child(uid).setValue(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SetingACActivity.this, "Thông tin đã được lưu", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SetingACActivity.this, "Lưu thông tin không thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
                // Lấy mật khẩu hiện tại và mật khẩu mới từ các ô EditText
                String currentPassword = binding.currentPasswordEditText.getText().toString();
                final String newPassword = binding.passwordEditText.getText().toString();

                if (TextUtils.isEmpty(currentPassword)) {
                    binding.currentPasswordEditText.setError("Vui lòng nhập mật khẩu hiện tại");
                    return;
                }

                if (TextUtils.isEmpty(newPassword)) {
                    binding.passwordEditText.setError("Vui lòng nhập mật khẩu mới");
                    return;
                }

                if (newPassword.length() < 6) {
                    binding.passwordEditText.setError("Mật khẩu mới phải có ít nhất 6 ký tự");
                    return;
                }

                // Xác thực người dùng với mật khẩu hiện tại
                FirebaseUser user = firebaseAuth.getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
                user.reauthenticate(credential)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Thay đổi mật khẩu của người dùng
                                user.updatePassword(newPassword)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SetingACActivity.this, "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SetingACActivity.this, "Thay đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SetingACActivity.this, "Xác thực người dùng không thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
            }


        });

        // Thêm trình nghe nhấp chuột cho chooseImageButton
        chooseImageButton = findViewById(R.id.changeProfileImageButton);
        imageView = findViewById(R.id.avatarImageView);

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn ảnh đại diện"), PICK_IMAGE_REQUEST);
            }
        });
    }


}