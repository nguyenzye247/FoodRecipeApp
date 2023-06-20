package com.nguyenhl.bk.foodrecipe.feature.presentation.search.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.core.extension.collection.getRandomSubset
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter.toSearchFilterDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toSearchFilterDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.searchfilter.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.toSearchFilterDto
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CategoryRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchFilterRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto

class SearchFilterUseCase constructor(
    private val searchFilterRepository: SearchFilterRepository,
    private val categoryRepository: CategoryRepository
) {
    private val _mealTypeFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveMealTypeFilters(): LiveData<List<SearchFilterItemDto>?> = _mealTypeFilters

    private val _dietFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveDietFilters(): LiveData<List<SearchFilterItemDto>?> = _dietFilters

    private val _difficultyFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveDifficultyFilters(): LiveData<List<SearchFilterItemDto>?> = _difficultyFilters

    private val _cuisineFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveCuisineFilters(): LiveData<List<SearchFilterItemDto>?> = _cuisineFilters

    private val _authorFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveAuthorFilters(): LiveData<List<SearchFilterItemDto>?> = _authorFilters

    private val _ingredientFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveIngredientFilters(): LiveData<List<SearchFilterItemDto>?> = _ingredientFilters

    private val _kcalFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveKcalFilters(): LiveData<List<SearchFilterItemDto>?> = _kcalFilters

    private val _totalTimeFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveTotalTimeFilters(): LiveData<List<SearchFilterItemDto>?> = _totalTimeFilters

    private val _serveFilters: MutableLiveData<List<SearchFilterItemDto>?> = MutableLiveData()
    fun liveServeFilters(): LiveData<List<SearchFilterItemDto>?> = _serveFilters

    suspend fun fetchAllAuthorFilter() {
        searchFilterRepository.fetchAllAuthorFilters().collect { response ->
            when (response) {
                is AuthorFilterResponse -> {
                    _authorFilters.postValue(
                        response.authors.map { it.toSearchFilterDto() }
                            .getRandomSubset(FILTER_AMOUNT)
                    )
                }

                else -> {
                    _authorFilters.postValue(emptyList())
                }
            }
        }
    }

    suspend fun fetchAllIngredientFilter() {
        searchFilterRepository.fetchIngredientFilters().collect { response ->
            when (response) {
                is IngredientFilterResponse -> {
                    _ingredientFilters.postValue(
                        response.ingredients.map { it.toSearchFilterDto() }.getRandomSubset(FILTER_AMOUNT)
                    )
                }

                else -> {
                    _ingredientFilters.postValue(emptyList())
                }
            }
        }
    }

    suspend fun fetchAllKcalFilter() {
        searchFilterRepository.fetchAllKcalFilters().collect { response ->
            when (response) {
                is KcalFilterResponse -> {
                    _kcalFilters.postValue(
                        response.kcals.map { it.toSearchFilterDto() }.getRandomSubset(FILTER_AMOUNT)
                    )
                }

                else -> {
                    _ingredientFilters.postValue(emptyList())
                }
            }
        }
    }

    suspend fun fetchAllServeFilter() {
        searchFilterRepository.fetchAllServeFilters().collect { response ->
            when (response) {
                is ServeFilterResponse -> {
                    _serveFilters.postValue(
                        response.serves.map { it.toSearchFilterDto() }.getRandomSubset(FILTER_AMOUNT)
                    )
                }

                else -> {
                    _serveFilters.postValue(emptyList())
                }
            }
        }
    }

    suspend fun fetchAllTimeFilters() {
        searchFilterRepository.fetchAllTimeFilters().collect { response ->
            when (response) {
                is TimeFilterResponse -> {
                    _totalTimeFilters.postValue(
                        response.times.map { it.toSearchFilterDto() }.getRandomSubset(FILTER_AMOUNT)
                    )
                }

                else -> {
                    _ingredientFilters.postValue(emptyList())
                }
            }
        }
    }

    suspend fun getMealTypeFilters() {
        val mealTypeFilters = categoryRepository.getCategoryDetailsByCategoryId(
            "category_meal",
            FILTER_AMOUNT
        )?.map { it.toSearchFilterDto() }
        _mealTypeFilters.postValue(mealTypeFilters)
    }

    suspend fun getDietFilters() {
        val dietFilters = categoryRepository.getCategoryDetailsByCategoryId(
            "category_diet",
            FILTER_AMOUNT
        )?.map { it.toSearchFilterDto() }
        _dietFilters.postValue(dietFilters)
    }

    suspend fun getDifficultyFilters() {
        val difficultyFilters = categoryRepository.getCategoryDetailsByCategoryId(
            "category_difficulty",
            FILTER_AMOUNT
        )?.map { it.toSearchFilterDto() }
        _difficultyFilters.postValue(difficultyFilters)
    }
    suspend fun getCuisineFilters() {
        val cuisineFilters = categoryRepository.getCategoryDetailsByCategoryId(
            "category_cuisine",
            FILTER_AMOUNT
        )?.map { it.toSearchFilterDto() }
        _cuisineFilters.postValue(cuisineFilters)
    }

    companion object {
        private const val FILTER_AMOUNT = 12
    }
}
