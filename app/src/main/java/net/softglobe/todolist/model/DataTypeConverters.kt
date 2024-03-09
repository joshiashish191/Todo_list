package net.softglobe.todolist.model

import androidx.room.TypeConverter
import java.util.Date

/**
 * Room database cannot store Data data type directly, so we need type converters for that.
 * We are converting data value to long and storing then in database. While retrieving data,
 * we do the opposite.
 */
class DataTypeConverters {

    //Takes Date type as an input param and outputs equivalent long
    @TypeConverter
    fun fromDateToLongConverter(date : Date) : Long {
        return date.time
    }

    //Takes Long type as an input param and outputs equivalent Date
    @TypeConverter
    fun fromLongToDateConverter(date : Long) : Date {
        return Date(date)
    }
}