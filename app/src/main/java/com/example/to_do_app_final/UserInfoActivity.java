package com.example.to_do_app_final;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText; // Make sure this import is present

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.to_do_app_final.databinding.ActivityUserInfoBinding;

public class UserInfoActivity extends AppCompatActivity {

    Dialog dialog_editinfo, dialog_signout;
    ActivityUserInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnSignout.setOnClickListener(view -> {
            dialog_signout = new Dialog(UserInfoActivity.this);
            dialog_signout.setContentView(R.layout.dialog_sign_out);
            dialog_signout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_signout.show();

            Button btnOk = dialog_signout.findViewById(R.id.btn_ok);
            Button btnCancel = dialog_signout.findViewById(R.id.btn_cancel);

            btnOk.setOnClickListener(v -> {
                Intent intent = new Intent(UserInfoActivity.this, SignInActivity.class);
                startActivity(intent);
                finish(); // Close current activity
            });

            btnCancel.setOnClickListener(v -> dialog_signout.dismiss());
        });

        binding.btnEditInfo.setOnClickListener(view -> {
            dialog_editinfo = new Dialog(UserInfoActivity.this);
            dialog_editinfo.setContentView(R.layout.dialog_editinfo);
            dialog_editinfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_editinfo.show();

            EditText etName = dialog_editinfo.findViewById(R.id.et_name); // Ensure this line doesn't have a typo
            EditText etEmail = dialog_editinfo.findViewById(R.id.et_email); // Ensure this line doesn't have a typo
            Button btnOk = dialog_editinfo.findViewById(R.id.btn_ok);
            Button btnCancel = dialog_editinfo.findViewById(R.id.btn_cancel);

            btnOk.setOnClickListener(v -> {
                String newName = etName.getText().toString(); // Ensure this line doesn't have a typo
                String newEmail = etEmail.getText().toString(); // Ensure this line doesn't have a typo

                binding.txtUsrinfo.setText(newName);
                binding.txtEmail.setText(newEmail);
                binding.txtItem.setText(newName);
                binding.txtDate.setText(newEmail);

                dialog_editinfo.dismiss();
            });

            btnCancel.setOnClickListener(v -> dialog_editinfo.dismiss());
        });

        binding.card1.setOnClickListener(view -> {
            Intent intent = new Intent(UserInfoActivity.this, DeveloperInfoActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.imageView2).setOnClickListener(view -> {
            Intent intent = new Intent(UserInfoActivity.this, ToDoActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
