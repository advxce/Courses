package com.example.presentation.data

import com.example.domain.Course
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
fun Course.toUI(index: Int) = CourseUI(
    id = id,
    title = title,
    description = text,
    price = "$price ₽",
    rating = rate,
    date = publishDate,
    image = when (index % 3) {
        0 -> R.drawable.java_course_crop
        1 -> R.drawable.over
        else -> R.drawable.python
    },
    isBookmarked = isBookmarked
)