package io.github.alemazzo.sushime.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import io.github.alemazzo.sushime.model.database.categoria.Categoria
import io.github.alemazzo.sushime.model.database.categoria.CategorieDao
import io.github.alemazzo.sushime.model.database.piatto.PiattiDao
import io.github.alemazzo.sushime.model.database.piatto.Piatto


@Database(entities = [Categoria::class, Piatto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categorieDao(): CategorieDao
    abstract fun piattiDao(): PiattiDao

    companion object {
        const val DATABASE_NAME = "sushi-me.db"
        private var instance: AppDatabase? = null

        fun initialize(context: Context) {
            instance = databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .createFromAsset("template.db")
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
