package com.example.bohdan_forostianyi_czw_8_00_lab_3.lib

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class ChartHelper {
     companion object {
        fun <T> setData(chart: LineChart, data: List<T>, getValue: (item:T) -> Float, getLabel: (item: T) -> String): Unit {

            chart.data = LineData(LineDataSet(data.mapIndexed {
                    idx, item -> Entry(idx.toFloat(),
                    getValue(item))

            }, ""));

            chart.xAxis.valueFormatter = object: ValueFormatter() {
                override fun getFormattedValue(idx: Float): String {
                    val item = data.getOrNull(idx.toInt())
                    return if (item !== null) getLabel(item) else ""
                }
            }

            chart.invalidate();
        }

         fun configureCharts(vararg charts: LineChart) {
             charts.forEach {
                 it.legend.isEnabled = false
                 it.description.isEnabled = false
                 it.xAxis.labelRotationAngle = -90f
                 it.xAxis.position = XAxis.XAxisPosition.BOTTOM
                 it.extraBottomOffset = 45f

             }
         }
    }
}