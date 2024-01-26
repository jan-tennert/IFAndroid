package com.example.todo;

public interface TodoItemListener {

    void onCheckedChanged(int position, boolean checked);

    void onDelete(int position);

}
