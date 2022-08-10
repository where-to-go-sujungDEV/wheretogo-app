package com.example.wheretogo.ui.calendar

import android.graphics.Color
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*
import android.R

import android.app.Activity

import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import kotlin.collections.ArrayList


lateinit var dates : ArrayList<CalendarDay>

class EventDayDecorator (events: ArrayList<CalendarDay>): DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {

        return dates!!.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: DotSpan(Color.RED){})
    }

    init{ dates = ArrayList(events) }
}


