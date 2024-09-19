package com.example.doan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ThongKeKhachHangFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var pieChart: PieChart
    private lateinit var yearSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.thongkekhachhang_fragment, container, false)
        dbHelper = DatabaseHelper(requireContext())

        pieChart = view.findViewById(R.id.pieChart)
        yearSpinner = view.findViewById(R.id.year_spinner)
        setupYearSpinner()
        setupPieChart()


        return view
    }

    private fun setupYearSpinner() {
        val years = getAvailableYears()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = adapter

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedYear = parent.getItemAtPosition(position) as Int
                updatePieChart(selectedYear)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun getAvailableYears(): List<Int> {
        val hoaDonList = dbHelper.getAllHoaDon()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return hoaDonList.map {
            val date = sdf.parse(it.ngayXuatHD)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.get(Calendar.YEAR)
        }.distinct().sorted()
    }

    private fun setupPieChart() {
        val initialYear = yearSpinner.selectedItem as? Int ?: getAvailableYears().firstOrNull()
        if (initialYear != null) {
            updatePieChart(initialYear)
        }
    }

    private fun updatePieChart(year: Int) {
        val hoaDonList = dbHelper.getAllHoaDon()
        val khachHangTheoThang = mutableMapOf<Int, MutableSet<String>>()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        hoaDonList.filter {
            val date = sdf.parse(it.ngayXuatHD)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.get(Calendar.YEAR) == year
        }.forEach { hoaDon ->
            val date = sdf.parse(hoaDon.ngayXuatHD)
            val calendar = Calendar.getInstance()
            calendar.time = date

            val month = calendar.get(Calendar.MONTH) + 1

            if (!khachHangTheoThang.containsKey(month)) {
                khachHangTheoThang[month] = mutableSetOf()
            }

            khachHangTheoThang[month]?.add(hoaDon.maKH)
        }

        val sortedEntries = khachHangTheoThang.entries.sortedBy { it.key }

        val monthNames = arrayOf(
            "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
            "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        )

        val entries = sortedEntries.map { entry ->
            val month = entry.key
            val count = entry.value.size
            PieEntry(count.toFloat(), "${monthNames[month - 1]}")
        }

        val dataSet = PieDataSet(entries, "Số lượng khách hàng")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 16f

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(true)
        pieChart.legend.isEnabled = true
        pieChart.animateY(1000)

        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: com.github.mikephil.charting.data.Entry?, h: com.github.mikephil.charting.highlight.Highlight?) {
                if (e is PieEntry) {
                    val month = e.label.replace("Tháng ", "").toInt()
                    val soLuongKhachHang = e.value.toInt()
                    Toast.makeText(
                        context,
                        "Tháng: $month\nSố lượng khách hàng: $soLuongKhachHang",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onNothingSelected() {}
        })
    }
}


