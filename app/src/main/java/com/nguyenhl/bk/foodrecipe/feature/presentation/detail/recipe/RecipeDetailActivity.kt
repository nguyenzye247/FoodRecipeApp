package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityRecipeDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.NutrientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding, RecipeDetailViewModel>() {
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
            btnHear.onClick {

            }
        }

        binding.layoutContent.apply {

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

            binding.layoutContent.tvRecipeDescription.text = recipeDetail.description
        }
    }

    private fun bindRecipeIngredientsDataView(recipeIngredient: List<IngredientDto>) {
        binding.layoutContent.apply {
            tabLayoutDirections
            vp2Direction
        }
    }

    private fun bindRecipeNutrientDataView(recipeNutrients: List<NutrientDto>) {
        val calories = recipeNutrients[0]
        val fibre = recipeNutrients[1]
        val sugar = recipeNutrients[2]
        val saturates = recipeNutrients[3]
        val protein = recipeNutrients[4]
        val carbs = recipeNutrients[5]
        val fat = recipeNutrients[6]
        val salt = recipeNutrients[7]

        binding.layoutContent.layoutNutrients.apply {
            ivCalories.loadImage(calories.nutrientDetail.imageUrl)
            ivFibre.loadImage(fibre.nutrientDetail.imageUrl)
            ivSugar.loadImage(sugar.nutrientDetail.imageUrl)
            ivSaturates.loadImage(saturates.nutrientDetail.imageUrl)
            ivProtein.loadImage(protein.nutrientDetail.imageUrl)
            ivCarb.loadImage(carbs.nutrientDetail.imageUrl)
            ivFat.loadImage(fat.nutrientDetail.imageUrl)
            ivSalt.loadImage(salt.nutrientDetail.imageUrl)
        }
    }

    private fun showEmptyView() {

    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
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
