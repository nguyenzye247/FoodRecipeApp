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
) : Parcelable
