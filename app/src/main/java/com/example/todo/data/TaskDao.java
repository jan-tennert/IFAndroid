package com.example.todo.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Interaktion mit einer Datenbank wird über Data Access Objects (DAOs) abgewickelt.
 *
 * Normalerweise gibt es immer ein DAO pro Entität/Tabelle.
 */
@Dao
public interface TaskDao {

    /*
     * Hier können wir eigene Methoden definieren, die dann von Room implementiert werden.
     * Update, Insert und Delete sind selbsterklärend.
     * Select Befehle können wir z.T. auch selber definieren, indem wir die SQL-Abfrage als String übergeben.
     */

    //Android Studio gibt uns auch Autocomplete je nach Entitäten wir schon erstellt haben.
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Update
    void updateAll(Task... tasks);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

}
