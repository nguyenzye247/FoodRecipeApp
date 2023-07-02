package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.nguyenhl.bk.foodrecipe.core.extension.getWeekPageTitle
import com.nguyenhl.bk.foodrecipe.core.extension.getYearPageTitle
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.FragmentCalendarBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.calendar.RecipeByDateDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.MealType
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar.weekview.WeekDateBinder
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity.Companion.KEY_DATE
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity.Companion.KEY_IS_MEAL_TYPE_SEARCH
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity.Companion.KEY_MEAL_TYPE
import com.nguyenhl.bk.foodrecipe.feature.presentation.weeklyplan.WeeklyPlanActivity
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import com.nguyenhl.bk.foodrecipe.feature.widget.WrapContentLinearLayoutManager
import java.time.LocalDate
import java.time.YearMonth

class CalendarFragment : BaseFragment<FragmentCalendarBinding, MainViewModel>() {
    private val breakfastRecipes: ArrayList<RecipeDto> = arrayListOf()
    private val lunchRecipes: ArrayList<RecipeDto> = arrayListOf()
    private val dinnerRecipe: ArrayList<RecipeDto> = arrayListOf()
    private lateinit var breakfastRecipeAdapter: RecipeByDateAdapter
    private lateinit var lunchRecipeAdapter: RecipeByDateAdapter
    private lateinit var dinnerRecipeAdapter: RecipeByDateAdapter

    private val searchMealTypeRecipeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val haveRecipeAdded =
                    data?.getBooleanExtra(KEY_HAVE_RECIPE_ADDED, false) ?: false
                if (haveRecipeAdded) {
                    fetchRecipeByDate(viewModel.selectedDate)
                }
            }
        }

    override fun getLazyBinding() = lazy { FragmentCalendarBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        adjustScreenSize(binding.tvWeekdayYearTitle)
        initCalendar()
        fetchRecipeByDate()
        initMealTypeAdapter()
    }

    override fun initListener() {
        binding.apply {
            weekCalendarView.apply {
                weekScrollListener = { weekDays ->
                    binding.tvWeekdayYearTitle.text = getYearPageTitle(weekDays)
                    binding.tvWeekdayTitle.text = getWeekPageTitle(weekDays)
                }
            }

            btnWeeklyPlan.onClick {
                goToWeeklyPlan()
            }

            btnAddBreakfast.onClick {
                goToSearch(MealType.BREAKFAST)
            }
            btnAddLunch.onClick {
                goToSearch(MealType.LUNCH)
            }
            btnAddDinner.onClick {
                goToSearch(MealType.DINNER)
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveRecipesByDate()) { recipeByMealType ->
                setLoading(false)
                recipeByMealType?.let {
                    bindRecipeByMealTypeByDate(recipeByMealType)
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun initCalendar() {
        binding.weekCalendarView.apply {
            dayBinder = WeekDateBinder(viewModel.selectedDate) { oldDate, weekDay ->
                viewModel.selectedDate = weekDay.date
                binding.weekCalendarView.apply {
                    notifyDateChanged(oldDate)
                    notifyDateChanged(weekDay.date)
//                    notifyWeekChanged(weekDay)
                }
                fetchRecipeByDate(weekDay.date)
            }

            val currentMonth = YearMonth.now()
            setup(
                currentMonth.minusMonths(5).atStartOfMonth(),
                currentMonth.plusMonths(5).atEndOfMonth(),
                firstDayOfWeekFromLocale(),
            )
            scrollToDate(LocalDate.now())
        }
    }

    private fun initMealTypeAdapter() {
        binding.apply {
            rvBreakfast.apply {
                breakfastRecipeAdapter = RecipeByDateAdapter(
                    breakfastRecipes,
                    onItemClick = { recipe ->
                        goToRecipeDetail(recipe)
                    },
                    onFavoriteClick = { recipe ->
                        likeRecipe(recipe)
                    }
                )
                adapter = breakfastRecipeAdapter
                layoutManager = WrapContentLinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
            rvLunch.apply {
                lunchRecipeAdapter = RecipeByDateAdapter(
                    lunchRecipes,
                    onItemClick = { recipe ->
                        goToRecipeDetail(recipe)
                    },
                    onFavoriteClick = { recipe ->
                        likeRecipe(recipe)
                    }
                )
                adapter = lunchRecipeAdapter
                layoutManager = WrapContentLinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
            rvDinner.apply {
                dinnerRecipeAdapter = RecipeByDateAdapter(
                    dinnerRecipe,
                    onItemClick = { recipe ->
                        goToRecipeDetail(recipe)
                    },
                    onFavoriteClick = { recipe ->
                        likeRecipe(recipe)
                    }
                )
                adapter = dinnerRecipeAdapter
                layoutManager = WrapContentLinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }

    private fun fetchRecipeByDate(day: LocalDate? = null) {
        viewModel.fetchAllRecipeByDate(
            DateFormatUtil.formatDateForRecipeSearch(day ?: viewModel.selectedDate)
        )
    }

    private fun bindRecipeByMealTypeByDate(recipeByMealType: HashMap<MealType?, List<RecipeByDateDto>>) {
        val tempBreakfastRecipes = breakfastRecipes.toList()
        breakfastRecipes.clear()
        breakfastRecipes.addAll(recipeByMealType[MealType.BREAKFAST]?.map { it.recipe }
            ?: emptyList())
        breakfastRecipeAdapter.notifyChanges(tempBreakfastRecipes)

        val tempLunchRecipes = lunchRecipes.toList()
        lunchRecipes.clear()
        lunchRecipes.addAll(recipeByMealType[MealType.LUNCH]?.map { it.recipe } ?: emptyList())
        lunchRecipeAdapter.notifyChanges(tempLunchRecipes)

        val tempDinnerRecipes = breakfastRecipes.toList()
        dinnerRecipe.clear()
        dinnerRecipe.addAll(recipeByMealType[MealType.DINNER]?.map { it.recipe } ?: emptyList())
        dinnerRecipeAdapter.notifyChanges(tempDinnerRecipes)

    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
            whiteBackgroundView.setVisible(isShow)
        }
    }

    private fun goToSearch(mealType: MealType) {
        Intent(requireContext(), SearchActivity::class.java).apply {
            putExtra(KEY_MEAL_TYPE, mealType)
            putExtra(KEY_DATE, DateFormatUtil.formatDateForRecipeSearch(viewModel.selectedDate))
            putExtra(KEY_IS_MEAL_TYPE_SEARCH, true)
            searchMealTypeRecipeLauncher.launch(this)
        }
    }

    private fun goToRecipeDetail(recipe: RecipeDto) {
        RecipeDetailActivity.startActivity(requireContext()) {
            putExtra(RecipeDetailActivity.KEY_RECIPE_DTO, recipe)
        }
    }

    private fun goToWeeklyPlan() {
        WeeklyPlanActivity.startActivity(requireContext()) {

        }
    }

    private fun likeRecipe(recipe: RecipeDto) {
        viewModel.likeRecipe(recipe)
    }

    companion object {
        const val KEY_HAVE_RECIPE_ADDED = "key_have_recipe_added"
    }
}
