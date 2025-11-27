package com.example.di.modules

import com.example.data2.CoursesRepositoryImpl
import com.example.domain2.repository.CoursesRepository

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