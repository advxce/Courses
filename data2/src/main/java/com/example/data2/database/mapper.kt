package com.example.data2.database

import com.example.domain2.Course

fun Course.toEntity(): CourseEntity {
    return CourseEntity(
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
