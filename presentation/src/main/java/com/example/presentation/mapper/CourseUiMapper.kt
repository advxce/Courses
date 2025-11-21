package com.example.presentation.mapper

import androidx.core.content.ContextCompat
import com.example.domain.Course
import com.example.domain.CourseList
import com.example.domain.CoursesRepository
import com.example.domain.LoadCoursesResult
import com.example.presentation.R
import com.example.presentation.data.CourseUI
import javax.inject.Inject

class CourseUiMapper : LoadCoursesResult.Mapper<CourseState> {

    override fun mapSuccess(courses: CourseList): CourseState {
        val courseUiList: List<CourseUI> = courses.listCourses.mapIndexed { index, course-> course.toUI(index) }
        return CourseState.Success(courseUiList)
    }


//        courses.listCourses.mapIndexed { index, course ->
//            course.toUI(index)
//        }

    override fun mapError(message: String): CourseState = CourseState.Error("Error")

    private fun Course.toUI(index: Int) = CourseUI(
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
        isBookmarked = false
    )
}

sealed class CourseState{
    object Loading : CourseState()
    data class Success( val courses:List<CourseUI>): CourseState()
    data class Error(val message:String): CourseState()

}