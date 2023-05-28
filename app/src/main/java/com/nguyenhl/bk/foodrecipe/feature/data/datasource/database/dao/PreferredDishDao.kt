package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.PreferredDish

@Dao
interface PreferredDishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preferredDish: PreferredDish)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(preferredDishes: List<PreferredDish>)

    @Query("SELECT * FROM preferreddish WHERE user_id = :userId")
    suspend fun getAllPreferredDishesByUserId(userId: String): List<PreferredDish>
}
