package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail

@Dao
interface CategoryDetailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(categoryDetail: CategoryDetail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(categoryDetails: List<CategoryDetail>)

    @Query("SELECT * FROM categorydetail ORDER BY RANDOM() LIMIT :amount")
    suspend fun getRandomCategoryDetail(amount: Int): List<CategoryDetail>?

    @Query("SELECT * FROM categorydetail WHERE id_category = :idCategory ORDER BY RANDOM() LIMIT :amount")
    suspend fun getCategoryDetailsByCategoryId(
        idCategory: String,
        amount: Int
    ): List<CategoryDetail>?

    @Query("Select categorydetail.* from healthstatus " +
            "JOIN healthstatuscategorydetailcrossref " +
            "ON healthstatus.id_health_status = healthstatuscategorydetailcrossref.id_health_status " +
            "JOIN categorydetail " +
            "ON categorydetail.id_category_detail = healthstatuscategorydetailcrossref.id_category_detail " +
            "WHERE user_id != ''")
    suspend fun getAllUserHealthCategoryDetails(): List<CategoryDetail>?
}
