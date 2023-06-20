package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDetailDao
import com.nguyenhl.bk.foodrecipe.feature.dto.CategoryDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto
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
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryDetail
        return this.idApi == other.idApi &&
                this.idApiCategory == other.idApiCategory &&
                this.idCategoryDetail == other.idCategoryDetail &&
                this.name == other.name &&
                this.imageUrl == other.imageUrl
    }

    override fun hashCode(): Int {
        var result = idApiCategory.hashCode()
        result = 31 * result + idApi.hashCode()
        result = 31 * result + idCategoryDetail.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUrl.hashCode()
        return result
    }
}

internal fun CategoryDetail.toCategoryDetailDto(): CategoryDetailDto {
    return CategoryDetailDto(
        idCategoryDetail = this.idCategoryDetail,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

internal fun CategoryDetail.toSearchFilterDto(): SearchFilterItemDto {
    return SearchFilterItemDto(
        idApi = this.idApi,
        idDetail = this.idCategoryDetail,
        name = this.name,
        value = 0
    )
}
