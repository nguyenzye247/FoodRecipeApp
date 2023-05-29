package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Collection

@Dao
interface CollectionDao {

    @Insert
    suspend fun insert(collection: Collection)

    @Insert
    suspend fun insertAll(collections: List<Collection>)
}
