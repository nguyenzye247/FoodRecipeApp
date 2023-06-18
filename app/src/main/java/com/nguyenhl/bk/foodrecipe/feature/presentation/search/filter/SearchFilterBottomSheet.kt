package com.nguyenhl.bk.foodrecipe.feature.presentation.search.filter

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.BottomSheetSearchFilterBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.FilterS
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchViewModel

class SearchFilterBottomSheet : BottomSheetDialogFragment() {
    private val binding by lazy { BottomSheetSearchFilterBinding.inflate(layoutInflater) }
    private val viewModel by lazy { activityViewModels<SearchViewModel>().value }
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root.let {
            if (it.parent != null) {
                (it.parent as ViewGroup).removeView(it)
            }
            it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
        initObservers()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return bottomSheetDialog
    }

    private fun initViews(parentView: View) {
        val bottomSheetBehavior = BottomSheetBehavior.from(parentView.parent as View)
        bottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        dialog?.apply {
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
                (binding.root.parent as View).apply {
//                    minimumHeight = Resources.getSystem().displayMetrics.heightPixels
                    setBackgroundColor(Color.TRANSPARENT)
                }
            }
            window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                )
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            btnApplyFilter.onClick {

                dismiss()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    private fun initObservers() {
        viewModel.apply {
            observe(liveMealTypeFilters()) { mealTypeFilters ->
                mealTypeFilters ?: return@observe

                filterHashMap[FilterS.MEAL_TYPE]?.let { filters ->
                    binding.rvMealTypeFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(filters) { mealFilterItem ->

                        }
                    )
                }
            }
            observe(liveDifficultyFilters()) { difficultyFilters ->
                difficultyFilters ?: return@observe

                filterHashMap[FilterS.DIFFICULTY]?.let { filters ->
                    binding.rvDifficultyFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(filters) { difficultyFilterItem ->

                        }
                    )
                }
            }
            observe(liveDietFilters()) { dietFilters ->
                dietFilters ?: return@observe

                filterHashMap[FilterS.DIETS]?.let {
                    binding.rvDietsFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(dietFilters) { dietFilterItem ->

                        }
                    )
                }
            }
            observe(liveServeFilters()) { serveFilters ->
                serveFilters ?: return@observe

                filterHashMap[FilterS.SERVINGS]?.let {
                    binding.rvServingsFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(serveFilters) { servingFilterItem ->

                        }
                    )
                }
            }
            observe(liveTotalTimeFilters()) { totalTimeFilters ->
                totalTimeFilters ?: return@observe

                filterHashMap[FilterS.TOTAL_TIME]?.let {
                    binding.rvTotalTimeFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(totalTimeFilters) { timeFilterItem ->

                        }
                    )
                }
            }
            observe(liveAuthorFilters()) { authorFilters ->
                authorFilters ?: return@observe

                filterHashMap[FilterS.AUTHORS]?.let {
                    binding.rvAuthorFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(authorFilters) { authorFilterItem ->

                        }
                    )
                }
            }
            observe(liveKcalFilters()) { kcalFilters ->
                kcalFilters ?: return@observe

                filterHashMap[FilterS.CALORIES]?.let {
                    binding.rvCaloriesFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(kcalFilters) { kcalFilterItem ->

                        }
                    )
                }
            }
            observe(liveCuisineFilters()) { cuisineFilters ->
                cuisineFilters ?: return@observe

                filterHashMap[FilterS.CUISINES]?.let {
                    binding.rvCuisineFilters.bindCategoryDetailFiltersData(
                        SearchFilterItemAdapter(cuisineFilters) { cuisineFilterItem ->

                        }
                    )
                }
            }
        }
    }

    private fun RecyclerView.bindCategoryDetailFiltersData(filterAdapter: SearchFilterItemAdapter) {
        this.apply {
            adapter = filterAdapter
            layoutManager = GridLayoutManager(
                context,
                3,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(this)
        fragmentTransaction.commitAllowingStateLoss()
    }

    companion object {
        const val TAG = "SEARCH_FILTER_TAG"
        fun newInstance(): SearchFilterBottomSheet {
            return SearchFilterBottomSheet()
        }
    }
}
