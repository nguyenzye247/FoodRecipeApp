package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["id_recipe"], unique = true)])
@Parcelize
data class Recipe(
    @ColumnInfo(name = "api_id")
    val idApi: String,
    @ColumnInfo(name = "id_recipe")
    val idRecipe: String,
    @ColumnInfo(name = "id_recipe_detail")
    val idRecipeDetail: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "cook_time")
    val cookTime: Int,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "is_liked")
    val isLiked: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) : Parcelable
