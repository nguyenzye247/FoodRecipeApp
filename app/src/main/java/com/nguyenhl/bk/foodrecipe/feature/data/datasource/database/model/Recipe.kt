package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
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
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe
        return this.idApi == other.idApi &&
                this.idRecipe == other.idRecipe &&
                this.idRecipeDetail == other.idRecipeDetail &&
                this.name == other.name &&
                this.isLiked == other.isLiked
    }

    override fun hashCode(): Int {
        var result = idApi.hashCode()
        result = 31 * result + idRecipe.hashCode()
        result = 31 * result + idRecipeDetail.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + isLiked.hashCode()
        return result
    }
}

internal fun Recipe.toRecipeDto(): RecipeDto {
    return RecipeDto(
        apiId = this.idApi,
        idRecipe = this.idRecipe,
        idRecipeDetail = this.idRecipeDetail,
        name = this.name,
        imageUrl = this.imageUrl,
        totalTime = this.cookTime,
        author = this.author,
        categoryDetails = emptyList(),
        isLiked = this.isLiked,
        createdAt = this.createdAt
    )
}
