package com.nguyenhl.bk.foodrecipe.feature.presentation.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.TypedValue
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.*
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.views.*
import com.nguyenhl.bk.foodrecipe.core.extension.views.enforceSingleScrollDirection
import com.nguyenhl.bk.foodrecipe.databinding.ActivitySearchBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.FilterS
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.MealType
import com.nguyenhl.bk.foodrecipe.feature.helper.RxEvent
import com.nguyenhl.bk.foodrecipe.feature.helper.listenRxEventOnUI
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar.CalendarFragment.Companion.KEY_HAVE_RECIPE_ADDED
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.adapter.SearchPagerAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.adapter.SearchRecipePagingAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.filter.SearchFilterBottomSheet
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(), SearchInteractionListener {
    private lateinit var searchFilterBottomSheet: SearchFilterBottomSheet
    private lateinit var searchPagerAdapter: SearchPagerAdapter

    private val isMealTypeSearch by lazy { intent.getBooleanExtra(KEY_IS_MEAL_TYPE_SEARCH, false) }

    override fun getLazyBinding() = lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<SearchViewModel> {
        parametersOf(
            BaseInput.SearchInput(
                application,
                intent.getBooleanExtra(KEY_IS_MEAL_TYPE_SEARCH, false),
                intent.getStringExtra(KEY_DATE) ?: "",
                intent.serializable(KEY_MEAL_TYPE)
            )
        )
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(KEY_HAVE_RECIPE_ADDED, viewModel.haveAddedRecipe)
        })
        super.onBackPressed()
    }

    override fun initViews() {
        initSearchFilterBottomSheet()

        searchPagerAdapter = SearchPagerAdapter(this)
        binding.apply {
            val tabTitles =
                arrayListOf(getString(R.string.recipes), getString(R.string.suggest_recipes))
            vp2Search.apply {
                adapter = searchPagerAdapter
                recyclerView.enforceSingleScrollDirection()
                TabLayoutMediator(tlSearch, this) { tab, position ->
                    tab.text = tabTitles[position]
                }.attach()
            }

            fabAiSearch.setVisible(!isMealTypeSearch)
        }
        initTabLayout()
        selectTabPosition(0)
    }

    override fun initListener() {
        binding.apply {
            btnSearchFilter.onClick {
                showSearchFilters()
            }
            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
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

            observe(liveAddRecipeToDate()) { response ->
                response?.let {
                    if (response.status) {
                        toastSuccess(response.data.value)
                    } else {
                        toastError("Fail to add recipe")
                    }
                }
            }

            listenRxEventOnUI<RxEvent.EventApplySearchFilter> {
                searchRecipe()
            }
        }
    }

    private fun initTabLayout() {
        binding.tlSearch.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    ResourcesCompat.getFont(tab.view.context, R.font.gordita_bold)?.let {
                        setTabTypeface(tab, it)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    ResourcesCompat.getFont(tab.view.context, R.font.gordita_medium)?.let {
                        setTabTypeface(tab, it)
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    ResourcesCompat.getFont(tab.view.context, R.font.gordita_bold)?.let {
                        setTabTypeface(tab, it)
                    }
                }
            })
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

            val searchBody = viewModel.getSearchBodyFromFilters(searchText)
            viewModel.loadSearchRecipe(searchBody)
        }
    }

    private fun setTabTypeface(tab: TabLayout.Tab, typeface: Typeface) {
        for (i in 0 until tab.view.childCount) {
            val tabViewChild = tab.view.getChildAt(i)
            if (tabViewChild is TextView) {
                tabViewChild.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                tabViewChild.typeface = typeface
            }
        }
    }

    private fun selectTabPosition(pos: Int = 0) {
        val initSelectedTab = binding.tlSearch.getTabAt(pos)
        initSelectedTab?.let { tab ->
            tab.select()
            ResourcesCompat.getFont(tab.view.context, R.font.gordita_bold)?.let {
                setTabTypeface(tab, it)
            }
        }
    }

    override fun onSelectRecipe(recipe: RecipeDto) {
        goToRecipeDetail(recipe)
    }

    override fun onFavoriteRecipe(recipe: RecipeDto) {
        likeRecipe(recipe)
    }

    override fun onRecipeAddTo(recipe: RecipeDto) {
        viewModel.addRecipeToDate(recipe.idRecipe)
    }

    private fun goToRecipeDetail(recipe: RecipeDto) {
        RecipeDetailActivity.startActivity(this) {
            putExtra(RecipeDetailActivity.KEY_RECIPE_DTO, recipe)
        }
    }

    private fun likeRecipe(recipe: RecipeDto) {
        viewModel.likeRecipe(recipe)
    }

    companion object {
        const val KEY_MEAL_TYPE = "key_meal_type"
        const val KEY_DATE = "key_date"
        const val KEY_IS_MEAL_TYPE_SEARCH = "key_is_meal_type_search"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<SearchActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
