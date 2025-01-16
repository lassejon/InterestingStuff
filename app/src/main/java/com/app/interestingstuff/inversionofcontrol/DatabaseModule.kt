package com.app.interestingstuff.inversionofcontrol

import android.content.Context
import com.app.interestingstuff.data.AppDatabase
import com.app.interestingstuff.data.InterestingItemDao
import com.app.interestingstuff.data.InterestingItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideInterestingItemDao(database: AppDatabase): InterestingItemDao {
        return database.interestingItemDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: InterestingItemDao): InterestingItemRepository {
        return InterestingItemRepository(dao)
    }
}