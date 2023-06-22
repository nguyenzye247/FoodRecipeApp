package com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar.RecipeByDateBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar.RecipeDateBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.calendar.toRecipeByDateDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.calendar.*
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CalendarRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.calendar.RecipeByDateDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.MealType
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.flow.collectLatest

class SearchMealUseCase constructor(
    private val calendarRepository: CalendarRepository
) {
    private val _datesHaveRecipe: MutableLiveData<List<String>?> = MutableLiveData()
    fun liveDatesHaveRecipe(): LiveData<List<String>?> = _datesHaveRecipe

    private val _recipesByDate: MutableLiveData<HashMap<MealType?, List<RecipeByDateDto>>?> =
        MutableLiveData()
    fun liveRecipesByDate(): LiveData<HashMap<MealType?, List<RecipeByDateDto>>?> = _recipesByDate

    private val _addRecipeToDate: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveAddRecipeToDate(): LiveData<ApiCommonResponse?> = _addRecipeToDate
    fun setAddRecipeToDate(response: ApiCommonResponse?) {
        _addRecipeToDate.postValue(response)
    }

    private val _removeRecipeFromDate: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveRemoveRecipeFromDate(): LiveData<ApiCommonResponse?> = _removeRecipeFromDate

    suspend fun fetchAllDateHaveRecipe(context: Context) {
        val token = SessionManager.fetchToken(context).ifEmpty {
            context.toast("Empty token")
            return
        }
        calendarRepository.getAllDateHaveRecipe(token)
            .collect { response ->
                when (response) {
                    is DateHaveRecipeResponse -> {
                        _datesHaveRecipe.postValue(response.days)
                    }

                    else -> {
                        _datesHaveRecipe.postValue(emptyList())
                    }
                }
            }
    }

    suspend fun fetchAllRecipeByDate(context: Context, date: String) {
        val token = SessionManager.fetchToken(context).ifEmpty {
            context.toast("Empty token")
            return
        }
        calendarRepository.getRecipeByDate(token, RecipeByDateBody(date))
            .collectLatest { response ->
                when (response) {
                    is RecipeByDateResponse -> {
                        _recipesByDate.postValue(
                            groupRecipesOfDateByMealType(
                                response.data.map { it.toRecipeByDateDto() }
                            )
                        )
                    }

                    else -> {
                        _recipesByDate.postValue(null)
                    }
                }
            }
    }

    suspend fun addRecipeToDate(
        context: Context,
        idRecipe: String,
        date: String,
        mealType: MealType
    ) {
        val token = SessionManager.fetchToken(context).ifEmpty {
            context.toast("Empty token")
            return
        }
        calendarRepository.addRecipeToDate(token, RecipeDateBody(idRecipe, date, mealType.value))
            .collect { response ->
                when (response) {
                    is AddRecipeToDateResponse -> {
                        _addRecipeToDate.postValue(response.toApiCommonResponse())
                    }

                    else -> {
                        _addRecipeToDate.postValue(null)
                    }
                }
            }
    }

    suspend fun removeRecipeFromDate(context: Context, recipeByDateId: String) {
        val token = SessionManager.fetchToken(context).ifEmpty {
            context.toast("Empty token")
            return
        }
        calendarRepository.removeRecipeFromDate(token, recipeByDateId)
            .collect { response ->
                when (response) {
                    is DeleteRecipeFromDateResponse -> {
                        _removeRecipeFromDate.postValue(response.toApiCommonResponse())
                    }

                    else -> {
                        _removeRecipeFromDate.postValue(null)
                    }
                }
            }
    }

    private fun groupRecipesOfDateByMealType(
        recipesByDate: List<RecipeByDateDto>
    ) = recipesByDate.groupBy { recipe -> recipe.mealType }
            as? HashMap<MealType?, List<RecipeByDateDto>>

    companion object {
        private const val MAX_RECIPE_BY_DATE_CACHE = 7
    }
}
