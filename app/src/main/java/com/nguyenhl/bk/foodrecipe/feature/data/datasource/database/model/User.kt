package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.Collate
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["user_id"], unique = true)])
@Parcelize
data class User (
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String,
    @ColumnInfo(name = "gender")
    val gender: Int,
    @ColumnInfo(name = "height")
    val height: Float,
    @ColumnInfo(name = "weight")
    val weight: Float,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) : Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User
        return this.userId == other.userId &&
                this.name == other.name &&
                this.dateOfBirth == other.dateOfBirth &&
                this.gender == other.gender &&
                this.height == other.height &&
                this.weight == other.weight
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + dateOfBirth.hashCode()
        result = 31 * result + gender
        result = 31 * result + height.hashCode()
        result = 31 * result + weight.hashCode()
        return result
    }
}
