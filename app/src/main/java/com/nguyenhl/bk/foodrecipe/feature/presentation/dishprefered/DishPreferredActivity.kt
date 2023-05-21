package com.nguyenhl.bk.foodrecipe.feature.presentation.dishprefered

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.hide
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.core.extension.views.show
import com.nguyenhl.bk.foodrecipe.databinding.ActivityDishPreferredBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.helper.GridLayoutManagerWithSmoothScroller
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login.LoginActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.dishprefered.items.DishPreferredAdapter

class DishPreferredActivity : BaseActivity<ActivityDishPreferredBinding, DishPreferredViewModel>() {
    private val selectedDishes: MutableSet<DishPreferredDto> = mutableSetOf()

    override fun getLazyBinding() = lazy { ActivityDishPreferredBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<DishPreferredViewModel> {
        ViewModelProviderFactory(BaseInput.DishPreferredInput(application))
    }

    override fun initViews() {
        binding.apply {
            rvDishPreferred.addOnScrollListener( object: OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0) {
                        // Scrolled down
                        btnContinue.show()
                    } else if (dy > 0) {
                        // Scrolled up
                        btnContinue.hide()
                    }
                }
            })
        }
    }

    override fun initListener() {
        binding.apply {
            btnContinue.onClick {

            }
            tvSkip.onClick {

            }
            btnBack.onClick {

            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        observe(viewModel.liveIsLoading()) { isLoading ->
            showLoadingView(isLoading ?: false)
        }
        observe(viewModel.livePreferredDishes()) { dishes ->
            viewModel.setLoading(false)
            showEmptyView(false)
            if (dishes.isNullOrEmpty()) {
                showEmptyView(true)
                return@observe
            }
            initPreferredDishesList(dishes)
        }
    }

    private fun initPreferredDishesList(dishes: List<DishPreferredDto>) {
        binding.rvDishPreferred.apply {
            adapter = DishPreferredAdapter(
                dishes,
                onDishSelected = { dish ->
                    if (selectedDishes.contains(dish)) {
                        selectedDishes.remove(dish)
                    } else {
                        selectedDishes.add(dish)
                    }
                }
            )
            layoutManager = GridLayoutManagerWithSmoothScroller(
                this@DishPreferredActivity,
                2
            )
        }
    }

    private fun showEmptyView(isShowEmpty: Boolean) {
        binding.apply {
            empty.emptyView.setVisible(isShowEmpty)
            rvDishPreferred.setVisible(!isShowEmpty)
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    private fun showContinueButton(isShow: Boolean) {
        binding.btnContinue.setVisible(isShow)
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<DishPreferredActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
