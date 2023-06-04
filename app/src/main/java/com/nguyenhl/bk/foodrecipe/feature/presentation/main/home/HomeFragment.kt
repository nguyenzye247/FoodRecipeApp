package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.common.DEFAULT_AVATAR
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txt
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.FragmentHomeBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.*
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter.*

class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    private lateinit var dishTypeAdapter: DishTypeAdapter
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var topChefAdapter: TopChefAdapter
    private lateinit var dailyInspirationsAdapter: RecipeAdapter
    private lateinit var ingredientsAdapter: IngredientAdapter

    override fun getLazyBinding() = lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        adjustScreenSize(binding.ivAvatar)
        binding.apply {

        }
    }

    override fun initListener() {

    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
            observe(liveUserInfo()) { userInfo ->
                bindUserInfoViewData(userInfo)
            }
            observe(livePreferredDishes()) { preferredDishes ->
                bindPreferredDishesViewData(preferredDishes)
            }
            observe(liveSuggestRecipes()) { suggestRecipes ->
                bindSuggestRecipesViewData(suggestRecipes)
            }
            observe(liveCollections()) { collections ->
                bindCollectionsViewData(collections)
            }
            observe(liveTopChefs()) { topChefs ->
                bindTopChefsViewData(topChefs)
            }
            observe(liveDailyInspirations()) { randomRecipes ->
                bindDailyInspirationsViewData(randomRecipes)
            }
            observe(liveIngredients()) { ingredients ->
                bindIngredientsViewData(ingredients)
            }
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.progressBar.setVisible(isShow)
    }

    private fun bindUserInfoViewData(userInfo: UserInfoDto?) {
        userInfo ?: return

        binding.apply {
            ivAvatar.loadImage(DEFAULT_AVATAR)
            val userNameText = "${txt(R.string.greeting)} ${userInfo.name}"
            tvUserName.text = userNameText
        }
    }

    private fun bindPreferredDishesViewData(preferredDishes: List<DishPreferredDto>?) {
        preferredDishes ?: return

        dishTypeAdapter = DishTypeAdapter(preferredDishes)
        binding.rvDishPreferred.apply {
            adapter = dishTypeAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun bindSuggestRecipesViewData(suggestRecipes: List<RecipeDto>?) {
        suggestRecipes ?: return

        recipeAdapter = RecipeAdapter(suggestRecipes) { recipe ->
            // set favorite
        }
        binding.rvSuggestForYou.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun bindCollectionsViewData(collections: List<CollectionDto>?) {
        collections ?: return

        collectionAdapter = CollectionAdapter(collections)
        binding.rvCollection.apply {
            adapter = collectionAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun bindTopChefsViewData(topChefs: List<AuthorDto>?) {
        topChefs ?: return

        topChefAdapter = TopChefAdapter(topChefs)
        binding.rvTopChef.apply {
            adapter = topChefAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun bindDailyInspirationsViewData(randomRecipes: List<RecipeDto>?) {
        randomRecipes ?: return

        dailyInspirationsAdapter = RecipeAdapter(randomRecipes) { favoriteRecipe ->

        }
        binding.rvDailyInspiration.apply {
            adapter = dailyInspirationsAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun bindIngredientsViewData(ingredients: List<IngredientDto>?) {
        ingredients ?: return

        ingredientsAdapter = IngredientAdapter(ingredients)
        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }
}
