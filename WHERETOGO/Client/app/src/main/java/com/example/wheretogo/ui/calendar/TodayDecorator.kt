package com.example.wheretogo.ui.calendar

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
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

class TodayDecorator(context: Activity): DayViewDecorator {
    private var date = CalendarDay.today()
    private lateinit var drawable: Drawable
    var myContext = context

    // 당일 날짜 표현 커스텀
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view : DayViewFacade?) {
        drawable = myContext.resources.getDrawable(R.drawable.calendar_today_background)
        view?.addSpan(ForegroundColorSpan(Color.WHITE))
        view?.setSelectionDrawable(drawable)
    }
}