package com.example.presentation.data


import com.example.domain2.entity.Course
import com.example.presentation.R


data class CourseUI(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val date: String,
    val image: Int,
    val isBookmarked: Boolean
)
fun Course.toUI() = CourseUI(
    id = id,
    title = title,
    description = text,
    price = "$price ₽",
    rating = rate,
    date = publishDate,
    image = when (id % 4) {
        0 -> R.drawable.java_course_crop
        1 -> R.drawable.over
        else -> R.drawable.python
    },
    isBookmarked = isBookmarked
)

fun CourseUI.toDomain(): Course {
    return Course(
        id = this.id,
        title = this.title,
        text = this.description,
        price = this.price,
        rate = this.rating,
        startDate = this.date,
        hasLike = false,
        publishDate = date,
        isBookmarked = this.isBookmarked
    )
}