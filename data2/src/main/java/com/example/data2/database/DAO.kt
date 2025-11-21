package com.example.data2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO {
    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE isBookmarked = 1")
    suspend fun getBookmarkedCourses(): List<CourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("UPDATE courses SET isBookmarked = :isBookmarked WHERE id = :courseId")
    suspend fun updateBookmark(courseId: Int, isBookmarked: Boolean)

    @Query("DELETE FROM courses")
    suspend fun clearCourses()
}