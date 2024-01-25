package com.example.todo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Update
    void updateAll(Task... tasks);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

}
