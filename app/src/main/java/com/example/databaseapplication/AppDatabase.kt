package com.example.databaseapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var databaseInstance: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            return if (databaseInstance == null) {
                databaseInstance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "AppDatabase"
                ).build()

                databaseInstance!!
            }  else {
                databaseInstance!!
            }
        }
    }

}