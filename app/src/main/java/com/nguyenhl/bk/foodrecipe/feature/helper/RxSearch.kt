package com.nguyenhl.bk.foodrecipe.feature.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

object RxSearch {

    fun fromView(edt: EditText): Observable<String> {
        val subject = BehaviorSubject.create<String>()

        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, af: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                subject
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .doOnNext {
                        s.toString()
                    }
            }
        })

        return subject
    }
}
