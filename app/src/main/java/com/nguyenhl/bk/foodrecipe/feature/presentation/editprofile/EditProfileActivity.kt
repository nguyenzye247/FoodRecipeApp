package com.nguyenhl.bk.foodrecipe.feature.presentation.editprofile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toastError
import com.nguyenhl.bk.foodrecipe.core.extension.toastSuccess
import com.nguyenhl.bk.foodrecipe.core.extension.views.*
import com.nguyenhl.bk.foodrecipe.databinding.ActivityEditProfileBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.dto.HealthStatusDto
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.Gender
import com.nguyenhl.bk.foodrecipe.feature.dto.toUserInfoPutBody
import com.nguyenhl.bk.foodrecipe.feature.util.*
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil.DATE_DEFAULT_PATTERN
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil.formatApiDate
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.skydoves.powerspinner.OnSpinnerDismissListener
import com.skydoves.powerspinner.PowerSpinnerView
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>() {
    private var datePicker: TimePickerView? = null
    private var healthStatusPicker: OptionsPickerView<HealthStatusDto>? = null
    private val healthStatuses: ArrayList<HealthStatusDto> = arrayListOf()
    private val genders = enumValues<Gender>()

    override fun getLazyBinding() = lazy { ActivityEditProfileBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<EditProfileViewModel> {
        parametersOf(BaseInput.EditProfileInput(application))
    }

    override fun initViews() {
        binding.apply {
            layoutUserInfo.apply {
                btnContinue.setVisible(false)
                btnSave.setVisible(true)

                etHealthInput.setText(HealthStatusDto.noneHealthStatus.name)
                tipGenderInput.apply {
                    setSpinnerAdapter(IconSpinnerAdapter(this))
                    setItems(genders.map { IconSpinnerItem(it.title) })
                    lifecycleOwner = this@EditProfileActivity
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
            layoutUserInfo.apply {
                btnSave.onClick {
                    validateInputs { userInfoDto ->
                        viewModel.setLoading(true)
                        viewModel.updateUserInfo(userInfoDto)
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
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val healthStatuses = viewModel.getAllDbHealthStatuses()
                loadHealthStatusesToUI(healthStatuses)
                viewModel.initUserInfo()
            }
        }
        viewModel.apply {
            observe(liveUserInfo()) { userInfo ->
                userInfo?.let {
                    bindUserInfoDataView(it)
                }
            }

            observe(liveUpdateInfoStatus()) { updateStatus ->
                viewModel.setLoading(false)
                if (updateStatus == null) {
                    return@observe
                }
                val status = updateStatus.status
                if (status) {
                    // Check user info if user is logged in
                    toastSuccess(updateStatus.data.value)
                } else {
                    toastError(updateStatus.data.value)
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun bindUserInfoDataView(userInfo: UserInfoDto) {
        binding.layoutUserInfo.apply {
            etNameInput.setText(userInfo.name)
            etDobInput.setText(formatApiDate(userInfo.dob, DATE_DEFAULT_PATTERN))
            tipGenderInput.text = Gender.getGenderFrom(userInfo.gender)?.title ?: Gender.MALE.title
            etHeightInput.setText(userInfo.height.toString())
            etWeightInput.setText(userInfo.weight.toString())
            etHealthInput.setText(healthStatuses
                .first { it.idHealthStatus == userInfo.idHeathStatus }.name
            )
            viewModel.selectedHealthStatus = healthStatuses.first {
                it.idHealthStatus == userInfo.idHeathStatus
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

    private fun validateInputs(
        onValid: (userInfo: UserInfoDto) -> Unit
    ) {
        var isValid = true
        binding.layoutUserInfo.apply {
            val nameInput = etNameInput.textString
            val dobInput = etDobInput.textString
            val genderInput = if (tipGenderInput.selectedIndex > 0) {
                tipGenderInput.text
            } else {
                Gender.MALE.title
            }
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
                        viewModel.userId,
                        nameInput,
                        dobInput,
                        if (tipGenderInput.selectedIndex > 0) {
                            tipGenderInput.selectedIndex
                        } else {
                            Gender.MALE.value
                        },
                        heightInput.toFloat(),
                        weightInput.toFloat(),
                        viewModel.selectedHealthStatus.idHealthStatus,
                        emptyList()
                    )
                )
            }
        }
    }

    private fun setAllInputValid() {
        binding.layoutUserInfo.apply {
            tipNameInput.setError(false, null)
            tipDobInput.setError(false, null)
            tipHealthInput.setError(false, null)
            tipWeightInput.setError(false, null)
        }
    }

    private fun showDatePicker() {
        //DatePicker
        datePicker = DialogUtil.buildDatePicker(
            this@EditProfileActivity,
            onApply = {
                datePicker?.returnData()
                datePicker?.dismiss()
            },
            onCancel = {
                datePicker?.dismiss()
            }
        ) { date ->
            val dateText = DateFormatUtil.formatSimpleDate(date)
            binding.layoutUserInfo.etDobInput.setText(dateText)
        }
        datePicker?.show()
    }

    private fun showHealthStatusPicker() {
        healthStatusPicker = DialogUtil.buildHealthStatusPicker(
            this@EditProfileActivity,
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

    private fun setHeathStatusView(healthStatusName: String) {
        binding.layoutUserInfo.etHealthInput.setText(healthStatusName)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun PowerSpinnerView.setInputBg() {
        bg = getDrawable(R.drawable.bg_app_input)
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<EditProfileActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
