package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["id_dish_preferred"], unique = true)])
@Parcelize
data class PreferredDish(
    @ColumnInfo(name = "api_id")
    val idApi: String,
    @ColumnInfo(name = "id_dish_preferred")
    val idDishPreferred: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
): Parcelable

internal fun PreferredDish.toDishPreferredDto(): DishPreferredDto {
    return DishPreferredDto(
        idApi = this.idApi,
        idDishPreferred = this.idDishPreferred,
        name = this.name,
        imageUrl = this.imageUrl
    )
}
