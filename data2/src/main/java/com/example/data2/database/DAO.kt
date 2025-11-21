package com.example.data2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE isBookmarked = 1")
    fun getAllFavoritesCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id:Int): CourseEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Update
    suspend fun updateBookmark(courseEntity:CourseEntity)
}