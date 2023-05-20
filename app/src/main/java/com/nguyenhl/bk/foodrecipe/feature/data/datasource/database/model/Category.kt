package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["id_category"], unique = true)])
@Parcelize
data class Category(
    @ColumnInfo(name = "api_id")
    val idApi: String,

    @ColumnInfo(name = "id_category")
    val idCategory: String,

    @ColumnInfo(name = "name")
    val name: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) : Parcelable
