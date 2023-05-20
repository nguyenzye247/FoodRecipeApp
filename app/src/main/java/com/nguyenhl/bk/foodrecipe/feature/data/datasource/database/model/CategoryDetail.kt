package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["id_category_detail"], unique = true)])
@Parcelize
data class CategoryDetail(
    @ColumnInfo(name = "id_category")
    val idApiCategory: String,

    @ColumnInfo(name = "api_id")
    val idApi: String,

    @ColumnInfo(name = "id_category_detail")
    val idCategoryDetail: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
): Parcelable
