package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatusCategoryDetailCrossRef

@Dao
interface HealthStatusCategoryDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(healthStatusCategoryDetail: HealthStatusCategoryDetailCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(healthStatusCategoryDetails: List<HealthStatusCategoryDetailCrossRef>)
}
