package com.example.courses.modules

import com.example.data.CoursesRepositoryImpl
import com.example.domain.CoursesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    abstract fun provideRepository(impl: CoursesRepositoryImpl): CoursesRepository

}