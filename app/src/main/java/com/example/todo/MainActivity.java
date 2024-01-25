package com.example.todo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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

public class MainActivity extends AppCompatActivity {

    private ScreenBinding binding;
    private AppDatabase db;

    private View createButton;
    private RecyclerView taskList;
    private TaskRecyclerViewAdapter adapter;
    public static Executor ioExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ioExecutor = Executors.newSingleThreadExecutor();
        db = Room.databaseBuilder(this, AppDatabase.class, "todo").build();
        binding = ScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        assignViews();
        loadData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        ioExecutor.execute(() -> {
            List<Task> taskData = db.taskDao().getAll();
            adapter.setTasks(taskData);
            adapter.notifyDataSetChanged();
        });
    }

    private void assignViews() {
        createButton = findViewById(R.id.create_task);
        taskList = findViewById(R.id.tasks);
        adapter = new TaskRecyclerViewAdapter(this, new ArrayList<>(), db.taskDao());
        taskList.setAdapter(adapter);
        taskList.setLayoutManager(new LinearLayoutManager(this));
    }

}