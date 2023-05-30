package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.AuthorDto

data class ApiAuthor(
    @SerializedName("_id")
    val idAuthor: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url_image")
    val imageUrl: String
)

internal fun ApiAuthor.toAuthorDto(): AuthorDto {
    return AuthorDto(
        idAuthor = this.idAuthor,
        name = this.name,
        imageUrl = this.imageUrl
    )
}
