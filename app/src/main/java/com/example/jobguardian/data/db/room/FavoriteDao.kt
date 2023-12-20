package com.example.jobguardian.data.db.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobguardian.data.db.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoriteentity ORDER BY title ASC")
    fun getFavoritUser(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoritUser(favoritEntity: FavoriteEntity)

    @Delete
    fun deleteFavoritUser(favoritEntity: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteEntity WHERE title = :title )")
    fun isFavoritUser(title: String): LiveData<Boolean>
}