// ToDoActivity.java
package com.example.to_do_app_final;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.to_do_app_final.databinding.ActivityToDoBinding;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {

    ActivityToDoBinding binding;
    Dialog dialog;
    ToDoAdapter adapter;
    ArrayList<String> toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToDoBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toDoList = new ArrayList<>();
        adapter = new ToDoAdapter(this, toDoList);

        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(adapter);

        binding.btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(ToDoActivity.this);
                dialog.setContentView(R.layout.dialog_add_todo);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText etTodo = dialog.findViewById(R.id.et_title);
                Button btnOk = dialog.findViewById(R.id.btn_ok);
                Button btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String todoText = etTodo.getText().toString();
                        if (!todoText.isEmpty()) {
                            toDoList.add(todoText);
                            adapter.notifyItemInserted(toDoList.size() - 1);
                            dialog.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        binding.btnDeveloperInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoActivity.this, DeveloperInfoActivity.class);
                startActivity(intent);
            }
        });

        binding.btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
