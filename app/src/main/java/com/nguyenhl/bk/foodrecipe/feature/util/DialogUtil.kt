package com.nguyenhl.bk.foodrecipe.feature.util

import android.content.Context
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import java.util.*

object DialogUtil {

    fun <T> buildHealthStatusPicker(
        context: Context,
        onApply: () -> Unit,
        onCancel: () -> Unit,
        onOptionSelectListener: (index: Int) -> Unit
    ): OptionsPickerView<T>? {
        return OptionsPickerBuilder(context) { oldIndex, oldItem, newIndex, newText ->
            onOptionSelectListener.invoke(oldIndex)
        }.apply {
            setLayoutRes(R.layout.dialog_health_status_picker) { parentView ->
                val btnApply: TextView = parentView.findViewById(R.id.btn_apply)
                val btnCancel: TextView = parentView.findViewById(R.id.btn_cancel)
                btnApply.onClick {
                    onApply()
                }
                btnCancel.onClick {
                    onCancel()
                }
            }
            isDialog(true)
            setSelectOptions(0)
            setContentTextSize(16)
            isRestoreItem(true)
            setOutSideCancelable(false)
        }.build()
    }

    fun buildDatePicker(
        context: Context,
        onApply: (tv: TextView) -> Unit,
        onCancel: () -> Unit,
        onTimeSelectListener: (date: Date) -> Unit
    ): TimePickerView {
        return TimePickerBuilder(context) { date, v -> //Callback
            onTimeSelectListener(date)
        }.apply {
            setLayoutRes(R.layout.dialog_date_picker) { parentView ->
                val btnApply: TextView = parentView.findViewById(R.id.btn_apply)
                val btnCancel: TextView = parentView.findViewById(R.id.btn_cancel)
                btnApply.onClick {
                    onApply(btnApply)
                }
                btnCancel.onClick {
                    onCancel()
                }
            }
            isDialog(true)
            setContentTextSize(16)
            setOutSideCancelable(false)
        }
            .setContentTextSize(16)
            .isDialog(true)
            .build()
    }
}
