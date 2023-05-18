package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User (
    @ColumnInfo(name = "user_id")
    val postId: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) : Parcelable
