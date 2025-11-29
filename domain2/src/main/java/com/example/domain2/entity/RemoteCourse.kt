package com.example.domain2.entity

data class RemoteCourse(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
    val isBookmarked: Boolean
)

fun RemoteCourse.toCourse(): Course{
    return Course(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        hasLike = hasLike,
        publishDate = publishDate,
        isBookmarked = isBookmarked
    )
}