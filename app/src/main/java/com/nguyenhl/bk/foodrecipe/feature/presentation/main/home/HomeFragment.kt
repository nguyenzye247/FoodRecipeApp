package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.common.DEFAULT_AVATAR
import com.nguyenhl.bk.foodrecipe.core.common.KEY_USER_INFO
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txt
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.FragmentHomeBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.*
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef.ChefDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef.ChefDetailActivity.Companion.KEY_CHEF_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.collection.CollectionDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.collection.CollectionDetailActivity.Companion.KEY_COLLECTION_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient.IngredientDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient.IngredientDetailActivity.Companion.KEY_INGREDIENT_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity.Companion.KEY_RECIPE_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter.*
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.chef.VAChefActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.collection.VACollectionActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient.VAIngredientActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.random.VARandomRecipeActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.suggest.VASuggestActivity

class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    private lateinit var dishTypeAdapter: DishTypeAdapter
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var topChefAdapter: TopChefAdapter
    private lateinit var dailyInspirationsAdapter: RecipeAdapter
    private lateinit var ingredientsAdapter: IngredientAdapter
    private lateinit var recentlyViewedRecipeAdapter: RecipeAdapter

    override fun getLazyBinding() = lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        adjustScreenSize(binding.ivAvatar)
        binding.apply {

        }
    }

    override fun initListener() {
        binding.apply {
            btnViewAllCollection.onClick {
                goToViewAllCollection()
            }
            btnViewAllSuggest.onClick {
                goToSuggestRecipe()
            }
            btnViewAllDailyInspiration.onClick {
                goToRandomRecipe()
            }
            btnViewAllTopChef.onClick {
                goToChefs()
            }
            btnViewAllIngredients.onClick {
                goToIngredients()
            }
            btnViewAllRecentlyViewed.onClick {

            }
            btnSearch.onClick {
                goToSearch()
            }
        }
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
            observe(liveRecentlyView()) { recipes ->
                bindRecentlyViewedRecipesViewData(recipes)
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

        recipeAdapter = RecipeAdapter(
            suggestRecipes,
            onItemClick = { recipe ->
                goToRecipeDetail(recipe)
            },
            onFavoriteClick = { recipe ->
                likeRecipe(recipe)
            }
        )
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

        collectionAdapter = CollectionAdapter(collections) { collection ->
            goToCollectionDetail(collection)
        }
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

        topChefAdapter = TopChefAdapter(topChefs) { chef ->
            goToChefDetail(chef)
        }
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

        dailyInspirationsAdapter = RecipeAdapter(
            randomRecipes,
            onItemClick = { recipe ->
                goToRecipeDetail(recipe)
            },
            onFavoriteClick = { recipe ->
                likeRecipe(recipe)
            }
        )
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

        ingredientsAdapter = IngredientAdapter(ingredients) { ingredient ->
            goToIngredientDetail(ingredient)
        }
        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun bindRecentlyViewedRecipesViewData(recipes: List<RecipeDto>?) {
        recipes ?: return

        recentlyViewedRecipeAdapter = RecipeAdapter(
            recipes,
            onItemClick = { recipe ->
                goToRecipeDetail(recipe)
            },
            onFavoriteClick = { recipe ->
                likeRecipe(recipe)
                viewModel.updateRecipe(recipe)
            }
        )
        binding.rvRecentlyViewed.apply {
            adapter = recentlyViewedRecipeAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun likeRecipe(recipe: RecipeDto) {
        viewModel.likeRecipe(recipe)
    }

    private fun goToViewAllCollection() {
        VACollectionActivity.startActivity(context) {

        }
    }

    private fun goToSuggestRecipe() {
        val userInfo = viewModel.getUserInfo()
        VASuggestActivity.startActivity(context) {
            putExtra(KEY_USER_INFO, userInfo)
        }
    }

    private fun goToRandomRecipe() {
        VARandomRecipeActivity.startActivity(context) {

        }
    }

    private fun goToChefs() {
        VAChefActivity.startActivity(context) {

        }
    }

    private fun goToIngredients() {
        VAIngredientActivity.startActivity(context) {

        }
    }

    private fun goToCollectionDetail(collection: CollectionDto) {
        CollectionDetailActivity.startActivity(context) {
            putExtra(KEY_COLLECTION_DTO, collection)
        }
    }

    private fun goToChefDetail(chef: AuthorDto) {
        ChefDetailActivity.startActivity(context) {
            putExtra(KEY_CHEF_DTO, chef)
        }
    }

    private fun goToIngredientDetail(ingredient: IngredientDto) {
        IngredientDetailActivity.startActivity(context) {
            putExtra(KEY_INGREDIENT_DTO, ingredient)
        }
    }

    private fun goToRecipeDetail(recipe: RecipeDto) {
        RecipeDetailActivity.startActivity(context) {
            putExtra(KEY_RECIPE_DTO, recipe)
        }
    }

    private fun goToSearch() {
        SearchActivity.startActivity(context) {

        }
    }
}
