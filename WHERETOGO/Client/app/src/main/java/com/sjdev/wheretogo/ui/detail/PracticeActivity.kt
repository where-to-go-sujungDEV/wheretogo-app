package com.sjdev.wheretogo.ui.detail

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

import com.sjdev.wheretogo.databinding.ActivityPracticeBinding
import com.sjdev.wheretogo.ui.BaseActivity


class PracticeActivity : BaseActivity<ActivityPracticeBinding>(ActivityPracticeBinding::inflate){
    lateinit var barList : ArrayList<BarEntry>
    lateinit var barDataSet : BarDataSet
    lateinit var barData : BarData

    override fun initAfterBinding() {
        var barChart : BarChart = binding.barChart // 차트 선언

        barChart.setTouchEnabled(false) // 터치 금지
        barChart.setDrawBarShadow(true) // 그래프 그림자
        barChart.description.isEnabled = false // description label 비활성화
        //값 추가
        barList = ArrayList()
        barList.add(BarEntry(1f, 50.8f))
        barList.add(BarEntry(2f, 28.2f))
        barList.add(BarEntry(3f, 11.3f))
        barList.add(BarEntry(4f, 50.8f))
        barList.add(BarEntry(5f, 28.2f))
        barList.add(BarEntry(6f, 11.3f))
        barList.add(BarEntry(7f, 9.7f))

        barDataSet = BarDataSet(barList, "방문 비율") // 차트 데이터 리스트 삽입

        barData = BarData(barDataSet)
        barDataSet.color = Color.parseColor("#4C00C4") // 그래프 색상 지정
        barChart.data = barData

        // 글자 속성
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 10f


        val xAxis : XAxis = barChart.xAxis

        xAxis.run{
            isEnabled = true // x축 값 표시
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false) // 격자구조
            granularity = 1f // x축 간격
            setDrawAxisLine(false) // 그림
        }

        val yl: YAxis = barChart.axisLeft
        yl.run{
            setDrawAxisLine(false)
            setDrawGridLines(false)
            axisMinimum = 0f // this replaces setStartAtZero(true)
        }

        val yr: YAxis = barChart.axisRight
        yr.run{
            setDrawAxisLine(false)
            setDrawGridLines(false)
            axisMinimum = 0f
        }

//        barChart.setFitBars(true)


    }

}