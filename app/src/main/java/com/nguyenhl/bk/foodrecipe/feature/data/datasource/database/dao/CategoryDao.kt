package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Category
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryWithCategoryDetail

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Query("SELECT * FROM category")
    suspend fun getAllCategory(): List<Category>

    @Query("SELECT * FROM category")
    suspend fun getAllCategoryWithDetails(): List<CategoryWithCategoryDetail>
}
