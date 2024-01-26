package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.data.Task;
import com.example.todo.data.TaskDao;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> tasks;
    private final Context context;
    private final TodoItemListener listener;

    public TaskRecyclerViewAdapter(Context context, List<Task> tasks, TodoItemListener listener) {
        this.tasks = tasks;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new TaskRecyclerViewAdapter.ViewHolder(
                view,
                listener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.done.setChecked(task.isDone());
        holder.content.setText(task.getContent());
        if (task.getDueTimeMillis() != -1) {
            holder.dueDateTime.setText(String.format("%s", task.getDueTimeMillis()));
        } else {
            holder.dueDateTime.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int position) {
        tasks.remove(position);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox done;
        TextView content, dueDateTime;
        Button delete;

        public ViewHolder(@NonNull View itemView, TodoItemListener listener) {
            super(itemView);

            done = itemView.findViewById(R.id.is_done);
            content = itemView.findViewById(R.id.content);
            dueDateTime = itemView.findViewById(R.id.due_time_time);
            delete = itemView.findViewById(R.id.delete_button);
            delete.setOnClickListener((view) -> {
                listener.onDelete(getAdapterPosition());
            });
            done.setOnCheckedChangeListener((view, checked) -> {
                listener.onCheckedChanged(getAdapterPosition(), checked);
            });
        }

    }
}
