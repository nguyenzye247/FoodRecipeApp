package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<Recipe>)

    @Update
    suspend fun update(recipe: Recipe)

    @Query("SELECT * FROM recipe ORDER BY created_at LIMIT 10")
    fun getAllCategory(): Flow<List<Recipe>>
}
