package com.example.spotify_cloneapp;

import static com.example.spotify_cloneapp.MainActivity.fileName;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Models.Account;
import com.example.spotify_cloneapp.Models.ApiResponse;

import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    private EditText edtEmailSignUp, edtNameSignUp, edtPasswordSignUp, edtPasswordComfirmSignUp;
    private Button btnSignUp;
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loadComponent();
        loadAction();
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loadAction() {
        btnSignUp.setOnClickListener(v -> {
            String email = edtEmailSignUp.getText().toString().trim();
            String password = edtPasswordSignUp.getText().toString().trim();
            String name = edtNameSignUp.getText().toString().trim();
            String passwordComfirm = edtPasswordComfirmSignUp.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || passwordComfirm.isEmpty()) {
                Toast.makeText(this, "Cần điền đầy đủ các trường.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isEmailValid(email)) {
                Toast.makeText(this, "Email không hợp lệ.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(passwordComfirm)) {
                Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gửi dữ liệu đăng ký đến API
            registerAccount(email, name, password);
        });
    }

    private void registerAccount(String email, String name, String password) {
        account = new Account(1, email, name, "", password);
        Service.api.addAccount(account).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    account = apiResponse.getAccount();
                    try {
                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND);
                        fos.write((account.getID_Acc() + "\n").getBytes());
                        fos.write((account.getEmail() + "\n").getBytes());
                        fos.write((account.getName() + "\n").getBytes());
                        fos.write((account.getPassword() + "\n").getBytes());
                        fos.write((account.getThumbnail() + "\n").getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    // Kiểm tra nếu có lỗi từ API
                    if (apiResponse.getError() != null) {
                        Toast.makeText(Signup.this, "Lỗi: " + apiResponse.getError(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        // Điều hướng hoặc thực hiện hành động khác sau khi đăng ký thành công
                    }
                } else {
                    Toast.makeText(Signup.this, "Lỗi khi đăng ký tài khoản. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                if (t instanceof java.net.UnknownHostException) {
                    Toast.makeText(Signup.this, "Không thể kết nối tới máy chủ. Vui lòng kiểm tra kết nối mạng.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Signup.this, "Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void loadComponent() {
        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtNameSignUp = findViewById(R.id.edtNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtPasswordComfirmSignUp = findViewById(R.id.edtPasswordComfirmSignUp);

        btnSignUp = findViewById(R.id.btnSignUp);
    }
}