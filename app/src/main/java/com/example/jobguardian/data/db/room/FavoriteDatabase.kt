package com.example.jobguardian.data.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobguardian.data.db.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoritDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteDatabase? = null
        fun getInstance(context: Context): FavoriteDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java, "favorite.db"
                ).build().also {
                    instance = it
                }
            }
    }
}