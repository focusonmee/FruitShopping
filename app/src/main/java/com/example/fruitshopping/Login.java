package com.example.fruitshopping;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import helper.AccountDBHelper;

public class Login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private AccountDBHelper accountDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Khởi tạo AccountDBHelper
        accountDBHelper = new AccountDBHelper(this);

        // Thêm dữ liệu mẫu (nếu chưa có)
        addSampleData();

        // Xử lý sự kiện khi nhấn nút "Login"
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra tài khoản trong cơ sở dữ liệu
                    Log.d("LoginActivity", "Checking account for email: " + email);
                    Cursor cursor = accountDBHelper.getAccountByEmail(email);
                    if (cursor.moveToFirst()) {
                        // Lấy mật khẩu từ DB
                        String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                        int roleId = cursor.getInt(cursor.getColumnIndexOrThrow("role_id"));
                        Log.d("LoginActivity", "Found account with email: " + email + ", roleId: " + roleId);

                        // So sánh mật khẩu nhập với mật khẩu trong DB
                        if (password.equals(storedPassword)) {
                            // Đăng nhập thành công
                            if (roleId == 1) {
                                // Chuyển đến AdminActivity nếu là admin
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else if (roleId == 2) {
                                // Chuyển đến HomeActivity nếu là người dùng thường
                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                            }
                        } else {
                            // Mật khẩu không đúng
                            Toast.makeText(Login.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Tài khoản không tồn tại
                        Toast.makeText(Login.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
            }
        });
    }

    // Thêm dữ liệu mẫu vào SQLite
    private void addSampleData() {
        // Kiểm tra xem đã có dữ liệu hay chưa
        Cursor cursor = accountDBHelper.getAllAccounts();
        if (cursor.getCount() == 0) {
            // Chưa có dữ liệu, thêm tài khoản mẫu
            accountDBHelper.addAccount("Admin User", "0123456789", "Address 1", "admin@example.com", "12345678", 1);
            accountDBHelper.addAccount("Home User", "0987654321", "Address 2", "user@example.com", "12345678", 2);
            Log.d("LoginActivity", "Added sample accounts.");
            Toast.makeText(this, "Đã thêm tài khoản mẫu", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}