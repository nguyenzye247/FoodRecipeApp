package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Collection

@Dao
interface CollectionDao {

    @Insert
    suspend fun insert(collection: Collection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collections: List<Collection>)
}
