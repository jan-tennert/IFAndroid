package com.example.todo;

import com.example.todo.data.Task;

/**
 * Eigenes Interface, um die Kommunikation zwischen CreateTaskFragment und MainActivity zu ermöglichen.
 *
 * Man kann theoretisch auch einfach die nötigen Objekte als Parameter übergeben, aber so ist es etwas übersichtlicher, wenn nur die MainActivity mit der Datenbank interagiert.
 */
public interface CreateTaskListener {

    void onCreateTask(Task task);

}
