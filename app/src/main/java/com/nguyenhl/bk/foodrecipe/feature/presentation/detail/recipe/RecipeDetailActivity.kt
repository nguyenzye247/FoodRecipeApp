package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toastError
import com.nguyenhl.bk.foodrecipe.core.extension.views.*
import com.nguyenhl.bk.foodrecipe.databinding.ActivityRecipeDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.NutrientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingActivity.Companion.KEY_INGREDIENT_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingActivity.Companion.KEY_RECIPE_DETAIL_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient.IngredientDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.adapter.RecipeDirectionPagerAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.adapter.RecipeIngredientsAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.adapter.RecipeNutrientAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.ArrayList

class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding, RecipeDetailViewModel>() {
    private lateinit var recipeNutrientsAdapter: RecipeNutrientAdapter
    private lateinit var recipeIngredientsAdapter: RecipeIngredientsAdapter
    private lateinit var recipeDirectionPagerAdapter: RecipeDirectionPagerAdapter

    override fun getLazyBinding() = lazy { ActivityRecipeDetailBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<RecipeDetailViewModel> {
        parametersOf(
            BaseInput.RecipeDetailInput(
                application,
                intent.parcelableExtra(KEY_RECIPE_DTO)
            )
        )
    }

    override fun initViews() {
        viewModel.setLoading(true)
    }

    override fun initListener() {
        binding.layoutBanner.apply {
            btnBack.onClick {
                onBackPressed()
            }
            btnBookmark.onClick {

            }
            btnHeart.onClick {

            }
        }

        binding.layoutRecipeContent.apply {

        }

        binding.btnStartCooking.onClick {
            goToCooking()
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveRecipeDetail()) { recipeDetail ->
                recipeDetail?.let {
                    bindRecipeDetailDataView(it)
                }
            }

            observe(liveRecipeIngredientDetail()) { recipeIngredients ->
                recipeIngredients?.let {
                    bindRecipeIngredientsDataView(it)
                }
            }

            observe(liveRecipeNutrientDetail()) { recipeNutrients ->
                recipeNutrients?.let {
                    bindRecipeNutrientDataView(it)
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun bindRecipeDetailDataView(recipeDetail: RecipeDetailDto) {
        binding.layoutBanner.apply {
            ivRecipeThumbnail.loadImage(recipeDetail.imageUrl)
            tvRecipeAuthor.text = recipeDetail.author
            tvRecipeName.text = recipeDetail.name
            tvRecipeCookTime.apply {
                recipeDetail.cookTime?.let {
                    text = it
                } ?: run {
                    setVisible(false)
                }
            }
            tvRecipePrepTime.apply {
                recipeDetail.prepTime?.let {
                    text = it
                } ?: run {
                    setVisible(false)
                }
            }

            binding.layoutRecipeContent.tvRecipeDescription.text = recipeDetail.description
        }
    }

    private fun bindRecipeIngredientsDataView(recipeIngredient: List<IngredientDto>) {
        recipeIngredientsAdapter = RecipeIngredientsAdapter(recipeIngredient) { ingredient ->
            goToIngredientDetails(ingredient)
        }
        binding.layoutRecipeContent.rvIngredients.apply {
            adapter = recipeIngredientsAdapter
            layoutManager = GridLayoutManager(
                this@RecipeDetailActivity,
                3
            )
        }

        recipeDirectionPagerAdapter = RecipeDirectionPagerAdapter(this@RecipeDetailActivity)
        binding.layoutRecipeContent.apply {
            val tabTitles =
                arrayListOf(getString(R.string.ingredients), getString(R.string.methods))
            vp2Direction.apply {
                adapter = recipeDirectionPagerAdapter
                recyclerView.enforceSingleScrollDirection()
                TabLayoutMediator(tabLayoutDirections, this) { tab, position ->
                    tab.text = tabTitles[position]
                }.attach()
            }
        }
        initTabLayout()
        selectTabPosition(0)
    }

    private fun bindRecipeNutrientDataView(recipeNutrients: List<NutrientDto>) {
        recipeNutrientsAdapter = RecipeNutrientAdapter(recipeNutrients)

        binding.layoutRecipeContent.layoutNutrients.apply {
            rvNutrients.apply {
                adapter = recipeNutrientsAdapter
                layoutManager = GridLayoutManager(
                    this@RecipeDetailActivity,
                    4
                )
            }
        }
    }

    private fun initTabLayout() {
//        binding.layoutContent.apply {
//            tabLayoutSliding.apply {
//                bindViewPager(
//                    vp2Direction,
//                    object : ViewPager2.OnPageChangeCallback() {
//                        override fun onPageSelected(position: Int) {
//                            super.onPageSelected(position)
////                            tabLayoutSliding.currentTab = position
//                        }
//                    }
//                )
//            }
//        }

        binding.layoutRecipeContent.tabLayoutDirections.apply {
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

    private fun showEmptyView() {

    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
            whiteBackgroundView.setVisible(isShow)
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
        val initSelectedTab = binding.layoutRecipeContent.tabLayoutDirections.getTabAt(pos)
        initSelectedTab?.let { tab ->
            tab.select()
            ResourcesCompat.getFont(initSelectedTab.view.context, R.font.gordita_bold)?.let {
                setTabTypeface(initSelectedTab, it)
            }
        }
    }

    private fun goToCooking() {
        val recipeDetails = viewModel.getRecipeDetailValue()
        val ingredients = viewModel.getIngredientDetailValue()
        if (recipeDetails == null || ingredients == null) {
            toastError(getString(R.string.error_try_again_later))
            return
        }

        CookingActivity.startActivity(this) {
            putExtra(KEY_RECIPE_DETAIL_DTO, recipeDetails)
            putParcelableArrayListExtra(KEY_INGREDIENT_DTO, ArrayList(ingredients))
        }
    }

    private fun goToIngredientDetails(ingredient: IngredientDto) {
        IngredientDetailActivity.startActivity(this) {
            putExtra(IngredientDetailActivity.KEY_INGREDIENT_DTO, ingredient)
        }
    }

    companion object {
        const val KEY_RECIPE_DTO = "key_recipe_dto"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<RecipeDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }

}
