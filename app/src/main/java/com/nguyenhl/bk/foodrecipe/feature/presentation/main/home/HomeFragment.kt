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
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.items.DishTypeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.items.SuggestForYouAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    private lateinit var dishTypeAdapter: DishTypeAdapter
    private lateinit var suggestForYouAdapter: SuggestForYouAdapter


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
        binding.apply {
            rvDishPreferred.apply {
                adapter = dishTypeAdapter
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }
}
