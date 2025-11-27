package com.example.data2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data2.database.entity.CourseEntity

@Database(entities = [CourseEntity::class], version = 3, exportSchema = false)
abstract class CourseDatabase: RoomDatabase() {
    abstract fun dao(): DAO
}