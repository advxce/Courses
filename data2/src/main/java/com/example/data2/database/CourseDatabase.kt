package com.example.data2.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
abstract class CourseDatabase: RoomDatabase() {
    abstract fun dao(): DAO
}