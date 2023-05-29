package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["id_collection"], unique = true)])
@Parcelize
data class Collection(
    @ColumnInfo(name = "api_id")
    val idApi: String,

    @ColumnInfo(name = "id_collection")
    val idCollection: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "description")
    val description: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) : Parcelable

internal fun Collection.toCollectionDto(): CollectionDto {
    return CollectionDto(
        idCollection = this.idCollection,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description
    )
}
