package com.nguyenhl.bk.foodrecipe.feature.presentation.cooking

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableArrayListExtra
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.enforceSingleScrollDirection
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.recyclerView
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCookingBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CookingActivity : BaseActivity<ActivityCookingBinding, CookingViewModel>() {
    private lateinit var cookingPagerAdapter: CookingPagerAdapter

    override fun getLazyBinding() = lazy { ActivityCookingBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<CookingViewModel> {
        parametersOf(
            BaseInput.CookingInput(
                application,
                intent.parcelableExtra(KEY_RECIPE_DETAIL_DTO),
                intent.parcelableArrayListExtra(KEY_INGREDIENT_DTO)
            )
        )
    }

    override fun initViews() {
        binding.apply {
            cookingPagerAdapter = CookingPagerAdapter(this@CookingActivity)
            vp2Cooking.apply {
                adapter = cookingPagerAdapter
                recyclerView.enforceSingleScrollDirection()
            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveRecipeDetail()) { recipeDetail ->
                recipeDetail?.let {
                    bindRecipeDetailDataView(recipeDetail)
                }
            }
        }
    }

    private fun bindRecipeDetailDataView(recipeDetail: RecipeDetailDto) {
        binding.apply {
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
        }
    }

    companion object {
        const val KEY_INGREDIENT_DTO = "key_ingredient_dto"
        const val KEY_RECIPE_DETAIL_DTO = "key_recipe_detail_dto"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<CookingActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
