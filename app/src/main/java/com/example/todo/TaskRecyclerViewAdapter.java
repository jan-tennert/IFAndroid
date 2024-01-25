package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.data.Task;
import com.example.todo.data.TaskDao;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> tasks;
    private final Context context;
    private final TaskDao dao;

    public TaskRecyclerViewAdapter(Context context, List<Task> tasks, TaskDao dao) {
        this.tasks = tasks;
        this.context = context;
        this.dao = dao;
    }

    @NonNull
    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new TaskRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.done.setOnCheckedChangeListener((view, checked) -> {
            if(checked != task.isDone()) {
                MainActivity.ioExecutor.execute(() -> {
                    dao.updateAll(new Task(task.getId(), task.getContent(), checked, task.getDueTimeMillis()));
                });
                task.setDone(checked);
            }
        });
        holder.done.setChecked(task.isDone());
        holder.content.setText(task.getContent());
       // holder.dueDateTime.setText(task.dueTimeMillis);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox done;
        TextView content, dueDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            done = itemView.findViewById(R.id.is_done);
            content = itemView.findViewById(R.id.content);
            dueDateTime = itemView.findViewById(R.id.due_time_time);
        }

    }
}
