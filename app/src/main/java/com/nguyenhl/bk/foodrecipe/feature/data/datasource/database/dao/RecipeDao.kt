package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Recipe

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Recipe>)

    @Query("SELECT * FROM recipe ORDER BY created_at LIMIT 10")
    suspend fun getAllCategory(): List<Recipe>
}
