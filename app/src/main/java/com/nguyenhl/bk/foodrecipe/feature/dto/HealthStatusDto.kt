package com.nguyenhl.bk.foodrecipe.feature.dto

data class HealthStatusDto(
    var idHealthStatus: String,
    var name: String
) {
    override fun toString(): String {
        return name
    }
}
