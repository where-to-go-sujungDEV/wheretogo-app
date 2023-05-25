package com.sjdev.wheretogo.ui.detail

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.sjdev.wheretogo.databinding.ActivityPracticeBinding
import com.sjdev.wheretogo.ui.BaseActivity
import java.util.*


class PracticeActivity : BaseActivity<ActivityPracticeBinding>(ActivityPracticeBinding::inflate){
    lateinit var barData : BarData

    override fun initAfterBinding() {


        // 값 추가
        val values = mutableListOf<BarEntry>()
        val type = ArrayList<String>()
        val percent = ArrayList<Float>()

        type.add("친구와")
        type.add("연인과")
        type.add("동생과")
        type.add("가족과")
        type.add("반려동물과")
        type.add("아이와")
        type.add("혼자")

        percent.add(100f)
        percent.add(40f)
        percent.add(30.5f)
        percent.add(20f)
        percent.add(10f)
        percent.add(20f)
        percent.add(20f)


        for (i in 0..6) {
            val index = i.toFloat()
            values.add(BarEntry(index, percent[i]))
        }

        val set = BarDataSet(values, "방문 비율")         // 차트 데이터 리스트 삽입
            .apply {
                color = Color.parseColor("#4C00C4") // 그래프 색상 지정
                setDrawValues(true) // 그래프 값 표시
                valueTextColor = Color.BLACK
                valueTextSize = 10f
            }

        barData = BarData(set)
            .apply {
                barWidth = 0.6f // 막대 너비
                setValueFormatter(PercentFormatter()) // '%' 붙이기
            }

        val barChart: BarChart = binding.barChart // 차트 선언
        barChart.data = barData

        barChart.run {
            setTouchEnabled(false) // 터치 금지
            setDrawBarShadow(true) // 그래프 그림자

            description.isEnabled = false // description label 비활성화
            legend.isEnabled = false // 범례 비활성화
            extraRightOffset = 40f; // 수치값 잘리지 않도록 오른쪽에 공간 부여

            xAxis.run { // x 축
                isEnabled = true // x축 값 표시
                valueFormatter = IndexAxisValueFormatter(type) // 동행자 라벨 붙이기
                textColor = Color.parseColor("#4C00C4") //라벨 색깔
                position = XAxis.XAxisPosition.BOTTOM // 라벨 위치
                setDrawGridLines(false) // 격자구조
                setDrawAxisLine(false)

            }

            axisLeft.run { // 왼쪽 y축
                isEnabled = false
                axisMinimum = 0f // this replaces setStartAtZero(true)
                axisMaximum = 100f

            }
            axisRight.run { //오른쪽 y축
                isEnabled = false // y축 오른쪽 값 표시
                axisMinimum = 0f
                axisMaximum = 100f
            }

        }
    }
}
