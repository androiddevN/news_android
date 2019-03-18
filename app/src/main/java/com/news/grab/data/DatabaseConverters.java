package com.news.grab.data;


import java.sql.Timestamp;

import androidx.room.TypeConverter;

public class DatabaseConverters {
    @TypeConverter
    public static Timestamp toJavaTimestamp(Long timestamp) {
        return timestamp == null ? null : new Timestamp(timestamp);
    }

    @TypeConverter
    public static Long toDatabaseTimestamp(Timestamp timestamp) {
        return timestamp == null ? new Timestamp(System.currentTimeMillis()).getTime() : timestamp.getTime();
    }
}
