package com.example.todo.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.todo.MainActivity;
import com.example.todo.R;
import com.example.todo.TaskRecyclerViewAdapter;
import com.example.todo.data.Task;
import com.example.todo.data.TaskDao;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CreateTaskDialogFragment extends DialogFragment {

    private Toolbar toolbar;
    private TextInputEditText taskContent;
    private final TaskDao dao;
    private TaskRecyclerViewAdapter adapter;

    public CreateTaskDialogFragment(TaskDao dao, TaskRecyclerViewAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_App_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.create_task, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        taskContent = view.findViewById(R.id.task_content);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.inflateMenu(R.menu.create_task_dialog);
        toolbar.setOnMenuItemClickListener(item -> {
            String text = Objects.requireNonNull(taskContent.getText()).toString();
            if(!text.isEmpty()) {
                Task task = new Task(Objects.requireNonNull(text), false, -1);
                MainActivity.ioExecutor.execute(() -> {
                    dao.insertAll(task);
                });
                adapter.addTask(task);
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                dismiss();
            }
            return true;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }

}
