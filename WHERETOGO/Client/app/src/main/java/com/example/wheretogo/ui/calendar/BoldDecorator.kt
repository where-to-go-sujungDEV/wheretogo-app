package com.example.wheretogo.ui.calendar

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class BoldDecorator(min:CalendarDay, max: CalendarDay): DayViewDecorator {
    val maxDay  = max
    val minDay=  min
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return (day?.month == maxDay.month && day.day <= maxDay.day)
                || (day?.month == minDay.month && day.day >= minDay.day)
                || (minDay.month < day?.month!! && day.month < maxDay.month)
    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: StyleSpan(Typeface.BOLD){})
    }
}