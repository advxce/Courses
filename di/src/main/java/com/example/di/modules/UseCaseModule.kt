package com.example.di.modules

import com.example.domain2.repository.CoursesRepository
import com.example.domain2.useCase.GetAllCoursesUseCase
import com.example.domain2.useCase.GetAllCoursesUseCaseImpl
import com.example.domain2.useCase.GetAllFavoritesCoursesUseCase
import com.example.domain2.useCase.GetAllFavoritesCoursesUseCaseImpl
import com.example.domain2.useCase.GetCoursesBySortPublishDateUseCase
import com.example.domain2.useCase.GetCoursesBySortPublishDateUseCaseImpl
import com.example.domain2.useCase.ToggleCourseBookmarkUseCase
import com.example.domain2.useCase.ToggleCourseBookmarkUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAllCoursesUseCase(repository: CoursesRepository): GetAllCoursesUseCase =
        GetAllCoursesUseCaseImpl(repository)

    @Provides
    fun provideGetAllFavoriteCourses(repository: CoursesRepository): GetAllFavoritesCoursesUseCase =
        GetAllFavoritesCoursesUseCaseImpl(repository)

    @Provides
    fun provideToggleCourseBookmarkUseCase(repository: CoursesRepository): ToggleCourseBookmarkUseCase =
        ToggleCourseBookmarkUseCaseImpl(repository)

    @Provides
    fun provideGetCoursesByPublishDateUseCase(repository: CoursesRepository): GetCoursesBySortPublishDateUseCase =
        GetCoursesBySortPublishDateUseCaseImpl(repository)

}