package io.github.alemazzo.sushime.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.alemazzo.sushime.model.database.users.User
import io.github.alemazzo.sushime.model.database.users.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "sashime.db"
        private var instance: AppDatabase? = null

        fun initialize(context: Context) {
            instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                initialize(context)
            }
            return instance!!
        }
    }
}
