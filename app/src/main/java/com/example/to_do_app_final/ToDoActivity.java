package com.example.to_do_app_final;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.to_do_app_final.databinding.ActivityToDoBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {

    ActivityToDoBinding binding;
    Dialog dialog;
    ToDoAdapter adapter;
    ArrayList<String> toDoList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToDoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("TODO_LIST", MODE_PRIVATE);

        // Restore to-do list from SharedPreferences
        loadToDoList();

        // Setup RecyclerView and Adapter
        adapter = new ToDoAdapter(this, toDoList);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(adapter);

        // Add to-do button click listener
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
                            saveToDoList(); // Save to SharedPreferences
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

        // Developer info button click listener
        binding.btnDeveloperInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoActivity.this, DeveloperInfoActivity.class);
                startActivity(intent);
            }
        });

        // User info button click listener
        binding.btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    // Save to-do list to SharedPreferences
    private void saveToDoList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(toDoList);
        editor.putString("todo_list", json);
        editor.apply();
    }

    // Load to-do list from SharedPreferences
    private void loadToDoList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("todo_list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        toDoList = gson.fromJson(json, type);

        if (toDoList == null) {
            toDoList = new ArrayList<>();
        }
    }
}
