package com.example.doan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.databinding.ActivityAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    //firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();


        binding.settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AccountActivity.this, SetingACActivity.class));

            }
        });
        binding.NenVaMauChuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi chạy một hoạt động mới
                Intent intent = new Intent(AccountActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        binding.ChinhSachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Chính sách");
                builder.setMessage("Chính sách bảo mật và sử dụng thông tin\n" +
                        "\n" +
                        "Chào mừng bạn đến với ứng dụng đọc truyện của chúng tôi. Chính sách này giải thích cách chúng tôi thu thập, sử dụng và bảo vệ thông tin cá nhân của bạn khi sử dụng ứng dụng của chúng tôi.\n" +
                        "\n" +
                        "Thu thập thông tin\n" +
                        "\n" +
                        "Chúng tôi thu thập thông tin cá nhân của bạn khi bạn đăng ký tài khoản, đăng nhập hoặc sử dụng các tính năng ứng dụng của chúng tôi, bao gồm tên, địa chỉ email, số điện thoại và địa chỉ IP. Chúng tôi cũng thu thập thông tin về cách bạn sử dụng ứng dụng của chúng tôi, bao gồm các truyện bạn đọc, thời gian sử dụng và các tính năng được sử dụng.\n" +
                        "\n" +
                        "Sử dụng thông tin\n" +
                        "\n" +
                        "Chúng tôi sử dụng thông tin cá nhân của bạn để cung cấp dịch vụ đọc truyện cho bạn và cải thiện sản phẩm của chúng tôi. Chúng tôi cũng sử dụng thông tin của bạn để liên lạc với bạn về các cập nhật và thông tin khác liên quan đến sản phẩm của chúng tôi.\n" +
                        "\n" +
                        "Bảo mật thông tin\n" +
                        "\n" +
                        "Chúng tôi cam kết bảo vệ thông tin cá nhân của bạn và sử dụng các biện pháp bảo mật hợp lý để ngăn chặn truy cập trái phép và lộ thông tin cá nhân của bạn. Chúng tôi không bán hoặc chia sẻ thông tin cá nhân của bạn với bên thứ ba trừ khi được yêu cầu bởi pháp luật hoặc theo yêu cầu của bạn.\n" +
                        "\n" +
                        "Thông tin liên hệ\n" +
                        "\n" +
                        "Nếu bạn có câu hỏi hoặc phản hồi về chính sách này hoặc sản phẩm của chúng tôi, vui lòng liên hệ với chúng tôi theo địa chỉ email sau: 20521718@gm.uit.edu.vn hoặc cick vào nút 'Email góp ý' và gửi thông tin cho chúng tôi.\n" +
                        "\n" +
                        "Cảm ơn bạn đã sử dụng ứng dụng đọc truyện của chúng tôi!");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        binding.VeChungToiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Thông tin về chúng tôi");
                builder.setMessage("Chúng tôi là một đội ngũ lập trình viên trẻ tuổi với đam mê và còn thiếu kinh nghiệm trong lĩnh vực phát triển ứng dụng di động. \n" +
                        "\n" +
                        "Nhóm gồm 3 thành viên: \n" +
                        "\n" +
                        "1. Tôn Nữ Thảo Nhi - HTTT - UIT \n" +
                        "\n" +
                        "2. Đinh Thị Ánh Nguyệt - HTTT - UIT \n" +
                        "\n" +
                        "3. Nguyễn Lê Thảo Ngọc - UIT \n" +
                        "\n" +
                        "Cảm ơn bạn đã sử dụng ứng dụng đọc truyện của chúng tôi!");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        binding.EmailGopYButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "20521718@gm.uit.edu.vn", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Góp ý về ứng dụng của bạn");
                startActivity(Intent.createChooser(emailIntent, "Gửi email"));
            }
        });
        binding.DangXuatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                firebaseAuth.signOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.libraryIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AccountActivity.this, LibraryActivity.class));

            }
        });
        binding.homeIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AccountActivity.this,HomePageUserActivity.class));

            }
        });
        binding.accountIB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AccountActivity.this, AccountActivity.class));

            }
        });



    }
    private void checkUser()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            startActivity(new Intent(AccountActivity.this, MainActivity.class));
            finish();
        }
        else {
            String email= firebaseUser.getEmail();
            binding.emailAC.setText(email);
            String name= firebaseUser.getDisplayName();
            binding.nameAC.setText(name);

        }
    }

}