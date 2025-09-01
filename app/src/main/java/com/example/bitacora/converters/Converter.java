package com.example.bitacora.converters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Converter {
    @TypeConverter
    public static String fromHashMap(HashMap<String, String> map) {
        if (map == null) return null;
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    @TypeConverter
    public static HashMap<String, String> toHashMap(String value) {
        if (value == null) return new HashMap<>();
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        return gson.fromJson(value, type);
    }
}
