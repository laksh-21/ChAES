package com.example.chaes.di.modules

import android.content.Context
import com.example.chaes.repository.FirebaseAuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepoModule {
    @Singleton
    @Provides
    fun provideAuthRepo(@ApplicationContext app: Context): FirebaseAuthRepo{
        return FirebaseAuthRepo(app)
    }

    @Singleton
    @Provides
    fun provideRandomString(): String{
        return "gejifeg"
    }
}