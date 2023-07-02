package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.weeklyplan

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail.WeeklyPlanDetailDto

data class ApiWeeklyPlanDetail(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_plan_detail")
    val idWeeklyPlanDetail: String,
    @SerializedName("plan")
    val weeklyPlans: List<ApiWeeklyPlanItem>
)

internal fun ApiWeeklyPlanDetail.toWeeklyPlanDetailDto(): WeeklyPlanDetailDto {
    return WeeklyPlanDetailDto(
        apiId = this.id,
        idWeeklyPlanDetail = this.idWeeklyPlanDetail,
        weeklyPlans = this.weeklyPlans.map { it.toWeeklyPlanItemDto() }
    )
}
