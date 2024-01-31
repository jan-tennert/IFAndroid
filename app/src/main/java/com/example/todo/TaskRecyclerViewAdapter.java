package com.example.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.data.Task;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Adapter für die RecyclerView, die die Aufgaben anzeigt.
 * <p>
 * Warum das so kompliziert ist weiß ich auch nicht. Ich will Jetpack Compose zurück.
 * <p>
 * Im Grunde ist der dafür da, die Daten anzuzeigen und möglicherweise auch zu verändern.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> tasks;
    private final Context context;
    private final TodoListListener listener;

    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    private final DateTimeFormatter dateTimeFormatter;

    @SuppressLint("NewApi") //...
    public TaskRecyclerViewAdapter(Context context, List<Task> tasks, TodoListListener listener) {
        this.tasks = tasks;
        this.context = context;
        this.listener = listener;
        dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
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

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.done.setChecked(task.isDone());
        holder.content.setText(task.getContent());
        if (task.getDueTimeMillis() != -1) {
            holder.dueDateTime.setText(String.format("Until %s", dateTimeFormatter.format(Instant.ofEpochMilli(task.getDueTimeMillis()).atZone(ZoneId.systemDefault()))));
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

        public ViewHolder(@NonNull View itemView, TodoListListener listener) {
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
