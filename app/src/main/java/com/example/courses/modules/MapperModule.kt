package com.example.courses.modules

import com.example.domain.LoadCoursesResult
import com.example.presentation.data.CourseUI
import com.example.presentation.mapper.CourseState
import com.example.presentation.mapper.CourseUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideMapper() : LoadCoursesResult.Mapper<CourseState> {
        return CourseUiMapper()
    }

}