package com.example.wheretogo.ui.calendar

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class WeekendDecorator : DayViewDecorator {

    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        //요일 판단
        //이번 달인지 판단해 이번 달이 아닐 경우 적용X
        val weekDay=calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SUNDAY || weekDay == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#4C00C4")){})
    }
}