package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus

@Dao
interface HealthStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(healthStatus: HealthStatus): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(healthStatuses: List<HealthStatus>)

    @Update
    suspend fun update(healthStatus: HealthStatus)

    @Delete
    suspend fun delete(healthStatus: HealthStatus)

    @Query("SELECT * FROM healthstatus")
    suspend fun getAllHealthStatus(): List<HealthStatus>
}
