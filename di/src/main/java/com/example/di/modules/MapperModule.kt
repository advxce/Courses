package com.example.di.modules

import com.example.domain2.mapper.LoadCoursesResult
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