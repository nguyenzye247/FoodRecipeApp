package com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.views.enforceSingleScrollDirection
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.recyclerView
import com.nguyenhl.bk.foodrecipe.databinding.FragmentProfileBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.view.TabEntity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import com.nguyenhl.bk.foodrecipe.feature.util.bindViewPager

class ProfileFragment : BaseFragment<FragmentProfileBinding, MainViewModel>() {
    private lateinit var profilePagerAdapter: ProfilePagerAdapter

    override fun getLazyBinding() = lazy { FragmentProfileBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()
    override fun initViews() {
        initViewPager()
        bindUserInfoView()
    }

    override fun initListener() {

    }

    override fun initObservers() {
        viewModel.apply {
            fetchLikedRecipe()
        }
    }

    private fun bindUserInfoView() {
        binding.apply {
            ivAvatarBackground.loadImage(R.drawable.img_user_bg)
//            ivUserAvatar.setImageDrawable(lottieDrawable)
            val userInfo = viewModel.getUserInfo() ?: return
            layoutProfile.apply {
                tvUserName.text = userInfo.name
                tvUserDob.text = getDOBFrom(userInfo.dob)
                tvHeightValue.text = userInfo.height.toString()
                tvWeightValue.text = userInfo.weight.toString()
                tvAgeValue.text = DateFormatUtil.getAgeFrom(userInfo.dob).toString()
            }
        }
    }

    private fun initViewPager() {
        profilePagerAdapter = ProfilePagerAdapter(requireActivity())
        binding.layoutProfile.vp2UserProfile.apply {
            adapter = profilePagerAdapter
            offscreenPageLimit = 2
            recyclerView.enforceSingleScrollDirection()
        }
        initTabLayout()
    }

    private fun initTabLayout() {
        binding.layoutProfile.tlUserProfile.apply {
            setTabData(java.util.ArrayList(customTabEntities))
            bindViewPager(binding.layoutProfile.vp2UserProfile)
        }
    }

    private fun getDOBFrom(dob: String): String {
        return DateFormatUtil.convertDateFormat(dob)
    }

    companion object {
        val customTabEntities: ArrayList<TabEntity> = arrayListOf(
            TabEntity(
                "",
                R.drawable.ic_profile_health_status_selected,
                R.drawable.ic_profile_health_status
            ),
            TabEntity(
                "",
                R.drawable.ic_profile_favorite_heart_selected,
                R.drawable.ic_profile_favorite_heart
            )
        )
    }
}