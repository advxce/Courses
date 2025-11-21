package com.example.presentation.adapter

import com.example.presentation.adapter.delegates.courseAdapterDelegate
import com.example.presentation.data.CourseUI
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CoursesAdapter(
    onDetailsClick:(CourseUI)->Unit,
    onBookmarkClick:(CourseUI)->Unit
): ListDelegationAdapter<List<CourseUI>>() {
    init {
        delegatesManager.addDelegate(courseAdapterDelegate (onDetailsClick, onBookmarkClick))
    }
}
