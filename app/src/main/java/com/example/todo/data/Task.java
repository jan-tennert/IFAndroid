package com.example.todo.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private boolean done;
    @ColumnInfo(name = "due_time_millis")
    private long dueTimeMillis;

    @Ignore
    public Task(String content, boolean done, long dueTimeMillis) {
        this.content = content;
        this.done = done;
        this.dueTimeMillis = dueTimeMillis;
    }

    public Task(int id, String content, boolean done, long dueTimeMillis) {
        this.id = id;
        this.content = content;
        this.done = done;
        this.dueTimeMillis = dueTimeMillis;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return done;
    }

    public long getDueTimeMillis() {
        return dueTimeMillis;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDueTimeMillis(long dueTimeMillis) {
        this.dueTimeMillis = dueTimeMillis;
    }
}
