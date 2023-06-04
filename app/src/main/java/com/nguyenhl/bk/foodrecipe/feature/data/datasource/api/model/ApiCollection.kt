package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Collection
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto

data class ApiCollection(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_collection")
    val idCollection: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("description")
    val description: String
)

internal fun ApiCollection.toCollectionDto(): CollectionDto {
    return CollectionDto(
        idCollection = this.idCollection,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description
    )
}

internal fun ApiCollection.toCollection(): Collection {
    return Collection(
        idApi = this.id,
        idCollection = this.idCollection,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description
    )
}
