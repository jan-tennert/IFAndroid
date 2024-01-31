package com.example.todo;

public interface TodoListListener {

    void onCheckedChanged(int position, boolean checked);

    void onDelete(int position);

}
