package com.nguyenhl.bk.foodrecipe.feature.presentation.search

import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.hideKeyboard
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivitySearchBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.FilterS
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.filter.SearchFilterBottomSheet
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {
    private lateinit var searchFilterBottomSheet: SearchFilterBottomSheet

    override fun getLazyBinding() = lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<SearchViewModel> {
        parametersOf(
            BaseInput.SearchInput(
                application
            )
        )
    }

    override fun initViews() {
        initSearchFilterBottomSheet()
    }

    override fun initListener() {
        binding.apply {
            btnSearchFilter.onClick {
                showSearchFilters()
            }
            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    searchRecipe()
                }
                true
            }
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveMealTypeFilters()) { mealTypeFilters ->
                mealTypeFilters ?: return@observe
                filterHashMap[FilterS.MEAL_TYPE] = mealTypeFilters
            }
            observe(liveDifficultyFilters()) { difficultyFilters ->
                difficultyFilters ?: return@observe
                filterHashMap[FilterS.DIFFICULTY] = difficultyFilters
            }
            observe(liveDietFilters()) { dietFilters ->
                dietFilters ?: return@observe
                filterHashMap[FilterS.DIETS] = dietFilters
            }
            observe(liveServeFilters()) { serveFilters ->
                serveFilters ?: return@observe
                filterHashMap[FilterS.SERVINGS] = serveFilters
            }
            observe(liveTotalTimeFilters()) { totalTimeFilters ->
                totalTimeFilters ?: return@observe
                filterHashMap[FilterS.TOTAL_TIME] = totalTimeFilters
            }
            observe(liveAuthorFilters()) { authorFilters ->
                authorFilters ?: return@observe
                filterHashMap[FilterS.AUTHORS] = authorFilters
            }
            observe(liveKcalFilters()) { kcalFilters ->
                kcalFilters ?: return@observe
                filterHashMap[FilterS.CALORIES] = kcalFilters
            }
            observe(liveCuisineFilters()) { cuisineFilters ->
                cuisineFilters ?: return@observe
                filterHashMap[FilterS.CUISINES] = cuisineFilters
            }
        }
    }

    private fun initSearchFilterBottomSheet() {
        searchFilterBottomSheet = SearchFilterBottomSheet.newInstance()
    }

    private fun showSearchFilters() {
        if (!::searchFilterBottomSheet.isInitialized) return
        if (searchFilterBottomSheet.isAdded) return
        searchFilterBottomSheet.show(supportFragmentManager, SearchFilterBottomSheet.TAG)
    }

    private fun searchRecipe() {
        binding.etSearch.apply {
            hideKeyboard()
            val searchText = text.toString()

//            val searchBody = getSearchBody(searchText)
//            viewModel.loadSearchRecipe(searchBody)
        }
    }

//    private fun getSearchBody(searchText: String): SearchRecipeFilterBody {
//
//    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<SearchActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
