package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.common.DEFAULT_AVATAR
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txt
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.databinding.FragmentHomeBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.*
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter.CollectionAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter.DishTypeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter.SuggestForYouAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter.TopChefAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    private lateinit var dishTypeAdapter: DishTypeAdapter
    private lateinit var suggestForYouAdapter: SuggestForYouAdapter
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var topChefAdapter: TopChefAdapter


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
            observe(liveUserInfo()) { userInfo ->
                bindUserInfoViewData(userInfo)
            }
            observe(livePreferredDishes()) { preferredDishes ->
                bindPreferredDishesViewData(preferredDishes)
            }
            observe(liveSuggestRecipes()) { suggestRecipes ->
                bindingSuggestRecipesViewData(suggestRecipes)
            }
            observe(liveCollections()) { collections ->
                bindingCollectionsViewData(collections)
            }
            observe(liveTopChefs()) { topChefs ->
                bindingTopChefsViewData(topChefs)
            }
            observe(liveIngredients()) { ingredients ->

            }
            observe(liveDailyInspirations()) { randomRecipes ->

            }
        }
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

    private fun bindingSuggestRecipesViewData(suggestRecipes: List<RecipeDto>?) {
        suggestRecipes ?: return

        suggestForYouAdapter = SuggestForYouAdapter(suggestRecipes) { recipe ->
            // set favorite
        }
        binding.rvSuggestForYou.apply {
            adapter = suggestForYouAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun bindingCollectionsViewData(collections: List<CollectionDto>?) {
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

    private fun bindingTopChefsViewData(topChefs: List<AuthorDto>?) {
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
}
