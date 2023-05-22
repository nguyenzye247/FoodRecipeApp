package com.nguyenhl.bk.foodrecipe.feature.presentation.createinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.longToast
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableArrayListExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.views.*
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCreateInfoBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.dto.HealthStatusDto
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.Gender
import com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.DishPreferredActivity.Companion.KEY_PREFERRED_DISHES
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainActivity
import com.nguyenhl.bk.foodrecipe.feature.util.*
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.skydoves.powerspinner.OnSpinnerDismissListener
import com.skydoves.powerspinner.PowerSpinnerView
import kotlinx.coroutines.launch


class CreateInfoActivity : BaseActivity<ActivityCreateInfoBinding, CreateInfoViewModel>() {
    private var datePicker: TimePickerView? = null
    private var healthStatusPicker: OptionsPickerView<HealthStatusDto>? = null

    private val healthStatuses: ArrayList<HealthStatusDto> = arrayListOf()
    private val genders = enumValues<Gender>()
    private val preferredDishes: ArrayList<DishPreferredDto> by lazy {
        intent.parcelableArrayListExtra(KEY_PREFERRED_DISHES) ?: arrayListOf()
    }

    override fun getLazyBinding() = lazy { ActivityCreateInfoBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<CreateInfoViewModel> {
        ViewModelProviderFactory(BaseInput.CreateInfoInput(application))
    }

    override fun initViews() {
        adjustScreenSize(binding.btnBack)
        binding.apply {
            etHealthInput.setText(HealthStatusDto.noneHealthStatus.name)
            tipGenderInput.apply {
                setSpinnerAdapter(IconSpinnerAdapter(this))
                setItems(genders.map { IconSpinnerItem(it.title) })
                lifecycleOwner = this@CreateInfoActivity
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initListener() {
        binding.apply {
            btnContinue.onClick {
                validateInputs { userInfoDto ->
                    viewModel.setLoading(true)
                    viewModel.createUserInfo(userInfoDto)
                }
            }
            etDobInput.onClick {
                showDatePicker()
            }
            tipGenderInput.apply {
                setOnSpinnerItemSelectedListener<IconSpinnerItem> { oldIndex, oldItem, newIndex, newText ->
                    tipGenderInput.setInputBg()
                }
                onSpinnerDismissListener = OnSpinnerDismissListener {
                    tipGenderInput.setInputBg()
                }
            }
            etHealthInput.onClick {
                showHealthStatusPicker()
            }
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val healthStatuses = viewModel.getAllDbHealthStatuses()
                loadHealthStatusesToUI(healthStatuses)
            }
        }
        observeDistinct(viewModel.liveIsLoading()) {
            binding.loading.progressBar.setVisible(it ?: false)
        }
        observe(viewModel.liveCreateInfoStatus()) { createUserInfoStatus ->
            viewModel.setLoading(false)
            if (createUserInfoStatus == null) {
                return@observe
            }
            val status = createUserInfoStatus.status
            val message = createUserInfoStatus.data.value

            longToast(message)
            if (status) {
                goToMain()
                return@observe
            }
        }
    }

    private fun loadHealthStatusesToUI(healthStatuses: ArrayList<HealthStatus>) {
        this.healthStatuses.clear()
        this.healthStatuses.add(HealthStatusDto.noneHealthStatus)
        this.healthStatuses.addAll(healthStatuses.map {
            HealthStatusDto(
                it.idHealthStatus,
                it.name
            )
        })
    }

    private fun showDatePicker() {
        //DatePicker
        datePicker = DialogUtil.buildDatePicker(
            this@CreateInfoActivity,
            onApply = {
                datePicker?.returnData()
                datePicker?.dismiss()
            },
            onCancel = {
                datePicker?.dismiss()
            }
        ) { date ->
            val dateText = DateFormatUtil.formatSimpleDate(date)
            binding.etDobInput.setText(dateText)
        }
        datePicker?.show()
    }

    private fun showHealthStatusPicker() {
        healthStatusPicker = DialogUtil.buildHealthStatusPicker(
            this@CreateInfoActivity,
            onApply = {
                healthStatusPicker?.returnData()
                healthStatusPicker?.dismiss()
            },
            onCancel = {
                healthStatusPicker?.dismiss()
            }
        ) { index ->
            if (healthStatuses.size > index) {
                viewModel.selectedHealthStatus = healthStatuses[index]
                setHeathStatusView(healthStatuses[index].name)
            }
        }
        healthStatusPicker?.apply {
            setPicker(healthStatuses)
        }?.show()
    }

    private fun validateInputs(
        onValid: (userInfo: UserInfoDto) -> Unit
    ) {
        var isValid = true
        binding.apply {
            val nameInput = etNameInput.textString
            val dobInput = etDobInput.textString
            val genderInput = genders[tipGenderInput.selectedIndex].title
            val heightInput = etHeightInput.textString
            val weightInput = etWeightInput.textString
            val healthStatusInput = viewModel.selectedHealthStatus.name

            nameInput.checkEmpty { errorMessage ->
                isValid = false
                etNameInput.setError(true)
                tipNameInput.setError(true, errorMessage)
            }
            dobInput.checkDate { errorMessage ->
                isValid = false
                etDobInput.setError(true)
                tipDobInput.setError(true, errorMessage)
            }
            heightInput.checkNumber { errorMessage ->
                isValid = false
                etHeightInput.setError(true)
                tipHealthInput.setError(true, errorMessage)
            }
            weightInput.checkNumber { errorMessage ->
                isValid = false
                etWeightInput.setError(true)
                tipWeightInput.setError(true, errorMessage)
            }
            if (isValid) {
                setAllInputValid()
                onValid(
                    UserInfoDto(
                        nameInput,
                        dobInput,
                        genders[tipGenderInput.selectedIndex].value,
                        heightInput.toFloat(),
                        weightInput.toFloat(),
                        viewModel.selectedHealthStatus.idHealthStatus,
                        preferredDishes.map { it.idDishPreferred }
                    )
                )
            }
        }
    }

    private fun setAllInputValid() {
        binding.apply {
            tipNameInput.setError(false, null)
            tipDobInput.setError(false, null)
            tipHealthInput.setError(false, null)
            tipWeightInput.setError(false, null)
        }
    }

    private fun setHeathStatusView(healthStatusName: String) {
        binding.etHealthInput.setText(healthStatusName)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun PowerSpinnerView.setInputBg() {
        bg = getDrawable(R.drawable.bg_app_input)
    }

    private fun goToMain() {
        MainActivity.startActivity(this@CreateInfoActivity) {
            // put stuffs
        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<CreateInfoActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
