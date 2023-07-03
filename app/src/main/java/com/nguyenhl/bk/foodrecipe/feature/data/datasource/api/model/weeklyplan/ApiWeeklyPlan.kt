package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.weeklyplan

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.WeeklyPlanDto

data class ApiWeeklyPlan(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_plan")
    val idWeeklyPlan: String,
    @SerializedName("id_plan_detail")
    val idWeeklyPlanDetail: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url_image")
    val imageUrl: String
)

internal fun ApiWeeklyPlan.toWeeklyPlanDto(): WeeklyPlanDto {
    return WeeklyPlanDto(
        idApi = this.id,
        idWeeklyPlan = this.idWeeklyPlan,
        idWeeklyPlanDetail = this.idWeeklyPlanDetail,
        name = this.name,
        imageUrl = this.imageUrl
    )
}
