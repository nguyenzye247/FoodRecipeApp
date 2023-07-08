package com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal



data class PhysicalLevelDto(
    val idApi: String,
    val idPhysicalLevel: String,
    val name: String,
    val value: Float
) {
    override fun toString(): String {
        return name
    }
}
