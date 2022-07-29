package com.example.wheretogo.ui.calendar

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.wheretogo.R
import com.example.wheretogo.ui.MainActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import kotlin.coroutines.coroutineContext

class TodayDecorator : DayViewDecorator {
    private var date = CalendarDay.today()
    // 당일 날짜 표현 커스텀
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view : DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#4C00C4")))

    }
}