package com.sjdev.wheretogo.ui.detail

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
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
        barData.barWidth = 0.7f
        barChart.run {

            setTouchEnabled(false) // 터치 금지
            setDrawBarShadow(true) // 그래프 그림자
            description.isEnabled = false // description label 비활성화
            legend.isEnabled = false // 범례 비활성화


            xAxis.run {
                isEnabled = false // x축 값 표시
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false) // 격자구조
                setDrawAxisLine(false) // 그림

            }

            axisLeft.run { // 왼쪽 y축
                isEnabled = false
                setDrawAxisLine(false)
                setDrawGridLines(false)
                axisMinimum = 0f // this replaces setStartAtZero(true)
                axisMaximum = 100f
            }

            axisRight.run { //오른쪽 y축
                isEnabled = false // y축 오른쪽 값 표시
                setDrawAxisLine(false)
                setDrawGridLines(false)
                axisMinimum = 0f
                axisMaximum = 100f
            }

        }

    }

}