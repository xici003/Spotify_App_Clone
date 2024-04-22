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
import com.example.spotify_cloneapp.Models.LoginRequest;
import com.example.spotify_cloneapp.Models.LoginResponse;

import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadComponent();
        loadAction();
    }

    private void loadAction() {
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            login(email, password);
        });
    }

    private void login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);

        Service.api.checkLogin(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Account account = loginResponse.getAccount();

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

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComponent() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }
}
