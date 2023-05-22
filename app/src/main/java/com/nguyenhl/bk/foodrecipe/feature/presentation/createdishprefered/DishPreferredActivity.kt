package com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.hide
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.core.extension.views.show
import com.nguyenhl.bk.foodrecipe.databinding.ActivityDishPreferredBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.helper.GridLayoutManagerWithSmoothScroller
import com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.items.DishPreferredAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.createinfo.CreateInfoActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DishPreferredActivity : BaseActivity<ActivityDishPreferredBinding, DishPreferredViewModel>() {
    private val selectedDishes: ArrayList<DishPreferredDto> = arrayListOf()

    override fun getLazyBinding() = lazy { ActivityDishPreferredBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<DishPreferredViewModel> {
        parametersOf(BaseInput.DishPreferredInput(application))
    }

    override fun initViews() {
        binding.apply {
            rvDishPreferred.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!viewModel.hasDishSelectedValue()) return
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
                goToCreateInfo()
            }
            tvSkip.onClick {
                goToCreateInfo()
            }
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        observeDistinct(viewModel.liveIsLoading()) { isLoading ->
            showLoadingView(isLoading ?: false)
        }
        observeDistinct(viewModel.liveHasDishSelected()) { hasSelected ->
            showContinueButton(hasSelected ?: false)
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
                    viewModel.hasDishSelected(selectedDishes.isNotEmpty())
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

    private fun goToCreateInfo() {
        CreateInfoActivity.startActivity(this@DishPreferredActivity) {
            // put stuffs
            putParcelableArrayListExtra(KEY_PREFERRED_DISHES, selectedDishes)
        }
    }

    companion object {
        const val KEY_PREFERRED_DISHES = "key_preferred_dishes"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<DishPreferredActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
