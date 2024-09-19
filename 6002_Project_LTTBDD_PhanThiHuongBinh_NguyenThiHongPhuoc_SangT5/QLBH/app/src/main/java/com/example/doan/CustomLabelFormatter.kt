package com.example.doan
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPointInterface

class CustomLabelFormatter(private val labels: Array<String>) : DefaultLabelFormatter() {
    override fun formatLabel(value: Double, isValueX: Boolean): String {
        return if (isValueX && value.toInt() in labels.indices) {
            labels[value.toInt()]
        } else {
            super.formatLabel(value, isValueX)
        }
    }
}