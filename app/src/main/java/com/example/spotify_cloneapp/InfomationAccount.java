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

public class InfomationAccount extends AppCompatActivity {
    private Account account;
    private EditText edtEmailProfile, edtNameProfile;
    private Button btnUpdateAccount, btnCancel, btnDeleteAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_account);
        account = (Account) getIntent().getSerializableExtra("account");

        loadComponent();
        loadAction();
    }

    private void loadAction() {
        btnCancel.setOnClickListener(v->{
            finish();
        });

        btnUpdateAccount.setOnClickListener(v->{
            updateAccount();
        });

        btnDeleteAccount.setOnClickListener(v->{
            Service.api.deleteAccount(account.getID_Acc()).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        ApiResponse apiResponse = response.body();
                        if (apiResponse != null) {
                            Toast.makeText(InfomationAccount.this, "Delete success", Toast.LENGTH_SHORT).show();

                            try {
                                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);  // Chế độ ghi đè
                                fos.write(("").getBytes());
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(InfomationAccount.this, "Lỗi khi lưu thông tin vào tệp.", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent(InfomationAccount.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(InfomationAccount.this, "Xóa tài khoản không thành công, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(InfomationAccount.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void updateAccount() {
        String name = edtNameProfile.getText().toString().trim();
        String email = edtEmailProfile.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Không được để trống các trường.", Toast.LENGTH_SHORT).show();
            return;
        }

        account.setName(name);
        account.setEmail(email);

        Service.api.updateAccount(account.getID_Acc(), account).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();

                    Toast.makeText(InfomationAccount.this, "Update success", Toast.LENGTH_SHORT).show();

                    // Save updated account information to a file
                    try {
                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                        fos.write((account.getID_Acc() + "\n").getBytes());
                        fos.write((account.getEmail() + "\n").getBytes());
                        fos.write((account.getName() + "\n").getBytes());
                        fos.write((account.getPassword() + "\n").getBytes());
                        fos.write((account.getThumbnail() + "\n").getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Check for a valid response and display the success message

                     Intent intent = new Intent(InfomationAccount.this, MainActivity.class);
                     startActivity(intent);
                     finish();
                } else {
                    Toast.makeText(InfomationAccount.this, "Không thành công, hãy thử lại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(InfomationAccount.this, "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComponent() {
        edtEmailProfile = findViewById(R.id.edtEmailProfile);
        edtNameProfile = findViewById(R.id.edtNameProfile);

        edtEmailProfile.setText(account.getEmail());
        edtNameProfile.setText(account.getName());

        btnUpdateAccount = findViewById(R.id.btnUpdateAccount);
        btnCancel = findViewById(R.id.btnCancel);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
    }
}