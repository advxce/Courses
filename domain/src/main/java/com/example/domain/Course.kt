package com.example.domain

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String

)
