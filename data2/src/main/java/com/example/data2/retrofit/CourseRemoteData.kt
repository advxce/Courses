package com.example.data2.retrofit

import com.example.data2.database.CourseEntity
import com.example.domain2.Course
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CourseRemoteData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
    @SerializedName("price") val price: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("hasLike") val hasLike: Boolean,
    @SerializedName("publishDate") val publishDate: String,
    val isBookmarked: Boolean
){
    fun toDomain(): Course {
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
    fun toEntity(): CourseEntity{
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

}