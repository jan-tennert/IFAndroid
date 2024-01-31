package com.example.todo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.todo.data.AppDatabase;
import com.example.todo.data.Task;
import com.example.todo.databinding.ScreenBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements TodoListListener, CreateTaskListener {

    private AppDatabase db;

    private View createButton;
    private TaskRecyclerViewAdapter adapter;
    public static Executor ioExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ioExecutor = Executors.newSingleThreadExecutor();
        db = Room.databaseBuilder(this, AppDatabase.class, "todo").build();
        ScreenBinding binding = ScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        assignViews();
        addActionHandlers();
        loadData();
    }

    private void addActionHandlers() {
        createButton.setOnClickListener(v -> {
            showCreateTaskDialog();
        });
    }

    private void showCreateTaskDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CreateTaskDialogFragment fragment = new CreateTaskDialogFragment(this);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, fragment)
                .addToBackStack(null).commit();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        ioExecutor.execute(() -> {
            List<Task> taskData = db.taskDao().getAll();
            adapter.setTasks(taskData);
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void assignViews() {
        createButton = findViewById(R.id.create_task);
        RecyclerView taskList = findViewById(R.id.tasks);
        adapter = new TaskRecyclerViewAdapter(this, new ArrayList<>(), this);
        taskList.setAdapter(adapter);
        taskList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onCheckedChanged(int position, boolean checked) {
        Task task = adapter.getTasks().get(position);
        if (checked != task.isDone()) {
            MainActivity.ioExecutor.execute(() -> {
                db.taskDao().updateAll(new Task(task.getId(), task.getContent(), checked, task.getDueTimeMillis()));
            });
            task.setDone(checked);
        }
    }

    @Override
    public void onDelete(int position) {
        Task task = adapter.getTasks().get(position);
        MainActivity.ioExecutor.execute(() -> {
            db.taskDao().delete(task);
        });
        adapter.removeTask(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onCreateTask(Task task) {
        MainActivity.ioExecutor.execute(() -> {
            db.taskDao().insertAll(task);
        });
        adapter.addTask(task);
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
    }
}