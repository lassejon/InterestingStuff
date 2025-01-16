package com.app.interestingstuff.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.interestingstuff.model.InterestingItem

@Database(entities = [InterestingItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun interestingItemDao(): InterestingItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "interesting_stuff_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}