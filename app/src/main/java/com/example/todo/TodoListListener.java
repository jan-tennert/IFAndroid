package com.example.todo;

/**
 * Noch ein eigenes Interface, diesmal für den RecycleViewAdapter.
 *
 * Die Methoden werden dann in der MainActivity implementiert.
 */
public interface TodoListListener {

    void onCheckedChanged(int position, boolean checked);

    void onDelete(int position);

}
