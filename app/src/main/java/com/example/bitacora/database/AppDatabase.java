package com.example.bitacora.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.bitacora.converters.Converter;

@Database(entities = {Dispositivo.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {
    // Declare DAO
    public abstract DispositivoDao dispositivoDao ();

}