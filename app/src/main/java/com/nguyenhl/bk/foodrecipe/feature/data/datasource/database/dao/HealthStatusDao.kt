package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus

@Dao
interface HealthStatusDao {
    @Insert
    suspend fun insert(healthStatus: HealthStatus)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(healthStatuses: List<HealthStatus>)

    @Query("UPDATE healthstatus SET api_id = :apiId, name = :name, user_id = :userId WHERE id_health_status = :idHealthStatus")
    suspend fun updateById(
        apiId: String,
        idHealthStatus: String,
        name: String,
        userId: String
    )

    @Delete
    suspend fun delete(healthStatus: HealthStatus)

    @Query("SELECT * FROM healthstatus WHERE id_health_status = :healthStatusId")
    suspend fun getHealthStatusById(healthStatusId: String): HealthStatus?

    @Query("SELECT * FROM healthstatus")
    suspend fun getAllHealthStatus(): List<HealthStatus>
}
