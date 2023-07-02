package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityIngredientDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter.RecipeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity.Companion.KEY_SEARCH_INGREDIENT
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IngredientDetailActivity :
    BaseActivity<ActivityIngredientDetailBinding, IngredientDetailViewModel>() {
    private lateinit var ingredientRecipesAdapter: RecipeAdapter

    private val ingredientInfo by lazy { intent.parcelableExtra<IngredientDto>(KEY_INGREDIENT_DTO) }

    override fun getLazyBinding() = lazy { ActivityIngredientDetailBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<IngredientDetailViewModel> {
        parametersOf(
            BaseInput.IngredientDetailInput(
                application,
                intent.parcelableExtra(KEY_INGREDIENT_DTO)
            )
        )
    }

    override fun initViews() {
        adjustScreenSize(binding.btnBack)
        showLoadingView(true)
        binding.apply {
            ingredientInfo?.let {

            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
            contentLayout.btnSearch.onClick {
                ingredientInfo?.let { ingredient ->
                    goToSearch(ingredient.idIngredient)
                }
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(getIngredientDetail()) { ingredient ->
                ingredient?.let {
                    bindIngredientDetailDataView(it)
                }
                showLoadingView(false)
            }

            observe(liveIngredientRecipes()) { recipes ->
                recipes?.let {
                    bindIngredientRecipesDataView(it)
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun bindIngredientDetailDataView(ingredientDetailDto: IngredientDetailDto) {
        binding.apply {
            ivIngredientThumbnail.loadImage(ingredientDetailDto.imageUrl)
            tvIngredientName.text = ingredientDetailDto.name
            val spellText = "|${ingredientDetailDto.pronunciation}|"
            tvIngredientSpell.text = spellText

            contentLayout.apply {
                tvIngredientInfo.text = ingredientDetailDto.info
                tvIngredientTitle.text = ingredientDetailDto.title
                tvIngredientDescription.text =
                    ingredientDetailDto.description.joinToString(separator = "\n")
            }
        }
    }

    private fun bindIngredientRecipesDataView(recipes: List<RecipeDto>) {
        binding.apply {
            ingredientRecipesAdapter = RecipeAdapter(
                recipes,
                onItemClick = { recipe ->
                    goToRecipeDetail(recipe)
                },
                onFavoriteClick = { recipe ->
                    viewModel.likeRecipe(recipe)
                }
            )
            contentLayout.rvIngredientRecipe.apply {
                adapter = ingredientRecipesAdapter
                layoutManager = LinearLayoutManager(
                    this@IngredientDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
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

    private fun goToRecipeDetail(recipe: RecipeDto) {
        RecipeDetailActivity.startActivity(this) {
            putExtra(RecipeDetailActivity.KEY_RECIPE_DTO, recipe)
        }
    }

    private fun goToSearch(idIngredient: String) {
        SearchActivity.startActivity(this) {
            putExtra(KEY_SEARCH_INGREDIENT, idIngredient)
        }
        finish()
    }

    companion object {
        const val KEY_INGREDIENT_DTO = "key_ingredient_dto"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<IngredientDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
