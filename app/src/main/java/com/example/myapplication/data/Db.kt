package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Node::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class Db : RoomDatabase() {

    abstract fun nodeDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: Db? = null

        private val converterInstance = Converter()

        fun getDatabase(context: Context): Db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    Db::class.java,
                    "node_database"
                )
                    .allowMainThreadQueries()
                    .addTypeConverter(converterInstance)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}