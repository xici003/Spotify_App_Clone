package com.example.spotify_cloneapp.Fragments;

import static com.example.spotify_cloneapp.MainActivity.fileName;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.spotify_cloneapp.InfomationAccount;
import com.example.spotify_cloneapp.MainActivity;
import com.example.spotify_cloneapp.Models.Account;
import com.example.spotify_cloneapp.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout btnProfile, btnLogout;
    private TextView tvUserName;
    private static Account account;

    public AccountFragment(Account account) {
        this.account = account;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment(account);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        loadComponent(view);
        loadAction();
        return view;
    }

    private void loadAction() {
        btnLogout.setOnClickListener(v->{
            try {
                // Ghi một chuỗi rỗng vào file để làm rỗng nội dung
                FileOutputStream fos = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write("".getBytes());  // Ghi đè với chuỗi trống
                fos.close();

                Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Có lỗi xảy ra khi đăng xuất", Toast.LENGTH_SHORT).show();
            }
        });

        btnProfile.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), InfomationAccount.class);
            intent.putExtra("account", (Serializable) account);

            // Thêm cờ để xóa hoạt động trước đó khỏi ngăn xếp
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    private void loadComponent(View view) {
        btnProfile = view.findViewById(R.id.btnProfile);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvUserName = view.findViewById(R.id.tv_username);

        tvUserName.setText(account.getName());
    }
}