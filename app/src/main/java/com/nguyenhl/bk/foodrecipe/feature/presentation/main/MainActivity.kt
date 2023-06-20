package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import android.content.Context
import android.content.Intent
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivityMainBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var mainAdapter: MainBottomBarAdapter

    override fun getLazyBinding() = lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<MainViewModel> {
        parametersOf(BaseInput.MainInput(application))
    }

    override fun initViews() {
        initLottieFab()
        initBottomNavigation()
        initViewpager()
    }

    override fun initListener() {
        binding.apply {
            btnFabSearch.onClick {
                goToSearch()
            }
        }
    }

    override fun initObservers() {
        viewModel.init()
    }

    private fun initLottieFab() {
        val lottieDrawable = LottieDrawable()
        LottieCompositionFactory.fromRawRes(this, R.raw.anim_search)
            .addListener { lottieComposition ->
                lottieDrawable.composition = lottieComposition
                lottieDrawable.maintainOriginalImageBounds = true
                lottieDrawable.repeatCount = LottieDrawable.INFINITE
                lottieDrawable.playAnimation()
            }
        binding.btnFabSearch.setImageDrawable(lottieDrawable)
    }

    private fun initViewpager() {
        binding.vp2Main.apply {
            mainAdapter = MainBottomBarAdapter(supportFragmentManager, lifecycle)
            adapter = mainAdapter
            currentItem = MainBottomBarAdapter.HOME_POS
            offscreenPageLimit = 2
            isUserInputEnabled = false
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavMain.apply {
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_home -> {
                        binding.vp2Main.setCurrentItem(MainBottomBarAdapter.HOME_POS, false)
                    }
                    R.id.menu_ingredient -> {
                        binding.vp2Main.setCurrentItem(MainBottomBarAdapter.INGREDIENTS_POS, false)
                    }
                    R.id.menu_calendar -> {
                        binding.vp2Main.setCurrentItem(MainBottomBarAdapter.CALENDAR_POS, false)
                    }
                    R.id.menu_user_profile -> {
                        binding.vp2Main.setCurrentItem(MainBottomBarAdapter.PROFILE_POS, false)
                    }
                }
                true
            }
        }
    }

    override fun onBackPressed() {

    }

    private fun goToSearch() {
        SearchActivity.startActivity(this) {

        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<MainActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
