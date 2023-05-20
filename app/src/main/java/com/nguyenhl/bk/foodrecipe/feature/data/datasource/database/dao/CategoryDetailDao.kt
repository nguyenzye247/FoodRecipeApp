package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail

@Dao
interface CategoryDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryDetail: CategoryDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categoryDetails: List<CategoryDetail>)


}
