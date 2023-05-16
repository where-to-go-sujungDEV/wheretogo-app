package com.sjdev.wheretogo.ui.calendar

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TodayDecorator(context: Activity): DayViewDecorator {
    private var date = CalendarDay.today()
    private lateinit var drawable: Drawable
    var myContext = context

    // 당일 날짜 표현 커스텀
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view : DayViewFacade?) {
        //drawable = myContext.resources.getDrawable(R.drawable.calendar_today_background)
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#FF1748")))
        //view?.setSelectionDrawable(drawable)
    }
}