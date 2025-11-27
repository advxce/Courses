package com.example.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data2.database.CourseDatabase
import com.example.data2.database.DAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CourseDatabase =
        Room.databaseBuilder(context= context, klass = CourseDatabase::class.java, name =  "courses.db").fallbackToDestructiveMigration().build()

    @Provides
    fun provideDao(db: CourseDatabase): DAO = db.dao()


}