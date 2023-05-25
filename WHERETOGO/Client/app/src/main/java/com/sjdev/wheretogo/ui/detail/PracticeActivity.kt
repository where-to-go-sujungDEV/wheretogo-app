package com.sjdev.wheretogo.ui.detail

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

import com.sjdev.wheretogo.databinding.ActivityPracticeBinding

import com.sjdev.wheretogo.ui.BaseActivity
import java.util.*


class PracticeActivity : BaseActivity<ActivityPracticeBinding>(ActivityPracticeBinding::inflate){
    lateinit var barData : BarData

    override fun initAfterBinding() {


        // 값 추가
        val values = mutableListOf<BarEntry>()
        val type = ArrayList<String>()
        type.add("친구와")
        type.add("연인과")
        type.add("동생")
        type.add("친구와")
        type.add("동생")
        type.add("동생")
        type.add("친구와")
        for (i in 0 .. 6){
            val index = i.toFloat()
            val random = Random().nextInt(101).toFloat()
            values.add(BarEntry(index, random))
        }

        val set = BarDataSet(values, "방문 비율")         // 차트 데이터 리스트 삽입
            .apply{
                color = Color.parseColor("#4C00C4") // 그래프 색상 지정
                setDrawValues(true) // 그래프 값 표시
                valueFormatter = IndexAxisValueFormatter()
                valueTextColor = Color.BLACK
                valueTextSize = 10f
            }

        val barChart : BarChart = binding.barChart // 차트 선언
        barData = BarData(set)
            .apply{
                barWidth = 0.7f
            }
        barData = BarData(set)

        barChart.data = barData

        barChart.run {

            setTouchEnabled(false) // 터치 금지
            setDrawBarShadow(true) // 그래프 그림자
//            setVisibleXRange(1f,14f) // x좌표 기준 보여줄 그래프 개수 조정

            description.isEnabled = false // description label 비활성화
            legend.isEnabled = false // 범례 비활성화


            xAxis.run {
                isEnabled = true // x축 값 표시
                valueFormatter = IndexAxisValueFormatter(type)
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