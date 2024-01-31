package com.example.todo.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Entität für die Tabelle "task".
 */
@Entity(tableName = "task")
public class Task {

    //Primärer Schlüssel "id" wird automatisch generiert
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Mehr Spalten
    private String content;
    private boolean done;

    //Spalten können umbenannt werden (um nicht den variablen Namen als Spaltennamen zu verwenden)
    @ColumnInfo(name = "due_time_millis")
    private long dueTimeMillis;

    //Constructor (es gibt zwei, weil wir einen brauchen, der von Room verwendet wird und einen, den wir verwenden. Kann man bestimmt auch sinnvoller lösen)

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

    //Getter und Setter

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

    //Noch ein paar notwendige Methoden für die App

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
