package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["id_health_status"], unique = true)])
data class HealthStatus(

    @ColumnInfo(name = "api_id")
    val idApi: String,

    @ColumnInfo(name = "id_health_status")
    val idHealthStatus: String,

    @ColumnInfo(name = "name")
    val name: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) : Parcelable
