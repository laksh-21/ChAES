package com.example.chaes.di.modules

import android.content.Context
import com.example.chaes.repository.DatastoreRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreRepoModule {
    @Singleton
    @Provides
    fun provideDatastoreRepo(@ApplicationContext app: Context): DatastoreRepo {
        return DatastoreRepo(app)
    }
}