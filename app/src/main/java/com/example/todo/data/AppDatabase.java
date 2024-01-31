package com.example.todo.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Hier wird die Datenbank mithilfe von Annotationen definiert.
 * <p>
 * Alle Entitäten/Tabellenentitäten werden hier aufgelistet.
 * <p>
 * Die Room-Datenbanken werden als abstrakte Klassen definiert und erben von RoomDatabase.
 */
@Database(entities = {Task.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    //Hier werden alle DAOs (=Data Access Objects) aufgelistet, je nach dem welche Tabellen wir haben)
    public abstract TaskDao taskDao();

}
