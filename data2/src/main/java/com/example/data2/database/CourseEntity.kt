package com.example.data2.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.Course

@Entity(tableName = "courses")
data class CourseEntity (
    @PrimaryKey
    val id:Int = 0,
    val title:String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
    val image:String = "",
    val isBookmarked: Boolean = false
)

fun CourseEntity.toDomain(): Course {
    return Course(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        hasLike = hasLike,
        publishDate = publishDate
    )
}