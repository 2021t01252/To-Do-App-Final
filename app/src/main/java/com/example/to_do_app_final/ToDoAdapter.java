package com.example.to_do_app_final;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private ArrayList<String> toDoList;
    private Context context;

    public ToDoAdapter(Context context, ArrayList<String> toDoList) {
        this.context = context;
        this.toDoList = toDoList;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_todo, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        String todoItem = toDoList.get(position);
        holder.toDoItem.setText(todoItem);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_todo);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText etTodo = dialog.findViewById(R.id.et_title);
                Button btnOk = dialog.findViewById(R.id.btn_ok);
                Button btnCancel = dialog.findViewById(R.id.btn_cancel);

                etTodo.setText(todoItem);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updatedText = etTodo.getText().toString();
                        if (!updatedText.isEmpty()) {
                            toDoList.set(holder.getAdapterPosition(), updatedText);
                            notifyItemChanged(holder.getAdapterPosition());
                            dialog.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                toDoList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, toDoList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        TextView toDoItem;
        ImageView imgEdit, imgDelete;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoItem = itemView.findViewById(R.id.tv_todo_item);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }
}
