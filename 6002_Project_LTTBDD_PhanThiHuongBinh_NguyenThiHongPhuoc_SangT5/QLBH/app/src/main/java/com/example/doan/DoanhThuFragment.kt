package com.example.doan

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DoanhThuFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var graph: GraphView
    private lateinit var yearSpinner: Spinner
    private lateinit var hoaDonListView: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.doanhthu_fragment, container, false)
        dbHelper = DatabaseHelper(requireContext())

        yearSpinner = view.findViewById(R.id.year_spinner)
        graph = view.findViewById(R.id.graph)
        hoaDonListView = view.findViewById(R.id.hoaDonListView)

        setupYearSpinner()
        setupGraph()

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
                updateGraph(selectedYear)
                updateListView(selectedYear)  // Update the list view based on the selected year
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun getAvailableYears(): List<Int> {
        val hoaDonList = dbHelper.getAllHoaDon()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val years = hoaDonList.map {
            val date = sdf.parse(it.ngayXuatHD)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.get(Calendar.YEAR)
        }.distinct().sorted()
        return years
    }

    private fun setupGraph() {
        // Initially display data for the first available year
        val initialYear = yearSpinner.selectedItem as? Int ?: getAvailableYears().firstOrNull()
        if (initialYear != null) {
            updateGraph(initialYear)
            updateListView(initialYear)  // Update the list view for the initial year
        }
    }

    private fun updateGraph(year: Int) {
        val hoaDonList = dbHelper.getAllHoaDon()
        val cthoaDonList = dbHelper.getAllCTHoaDon()
        val doanhThuTheoThang = mutableMapOf<Int, Double>()

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

            val doanhThu = cthoaDonList
                .filter { it.maHD == hoaDon.maHD }
                .sumOf { it.soLuongMua * it.donGiaBan }

            doanhThuTheoThang[month] = doanhThuTheoThang.getOrDefault(month, 0.0) + doanhThu
        }

        val sortedEntries = doanhThuTheoThang.entries.sortedBy { it.key }

        val dataPoints = sortedEntries.map { entry ->
            DataPoint(entry.key.toDouble(), entry.value)
        }.toTypedArray()

        val barSeries = BarGraphSeries(dataPoints)
        barSeries.color = Color.GRAY
        barSeries.spacing = 50

        val lineSeries = LineGraphSeries(dataPoints)
        lineSeries.color = Color.RED
        lineSeries.thickness = 8
        lineSeries.isDrawDataPoints = true
        lineSeries.dataPointsRadius = 10f
        lineSeries.setAnimated(true)

        graph.removeAllSeries()
        graph.addSeries(barSeries)
        graph.addSeries(lineSeries)

        graph.gridLabelRenderer.horizontalAxisTitle = "Tháng"
        graph.gridLabelRenderer.verticalAxisTitle = "Doanh thu"
        graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    "Tháng ${value.toInt()}"
                } else {
                    super.formatLabel(value, isValueX)
                }
            }
        }

        graph.viewport.isScrollable = true
        graph.viewport.isScalable = true
        graph.viewport.setMinX(1.0)
        graph.viewport.setMaxX(12.0)
        graph.viewport.isXAxisBoundsManual = true

        // Add grid lines and background color
        graph.gridLabelRenderer.isHorizontalLabelsVisible = true
        graph.gridLabelRenderer.isVerticalLabelsVisible = true
        graph.gridLabelRenderer.gridColor = Color.LTGRAY
        graph.gridLabelRenderer.setHumanRounding(false)

        graph.viewport.setBackgroundColor(Color.parseColor("#EFEFEF"))

        barSeries.setOnDataPointTapListener { _, dataPoint ->
            val month = dataPoint.x.toInt()
            val hoaDons = hoaDonList.filter {
                val date = sdf.parse(it.ngayXuatHD)
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.get(Calendar.MONTH) + 1 == month &&
                        calendar.get(Calendar.YEAR) == year  // Filter by selected year
            }
            updateListView(month, hoaDons)
            val doanhThu = dataPoint.y
            Toast.makeText(context, "Tháng: $month\nDoanh thu: $doanhThu", Toast.LENGTH_SHORT)
                .show()
        }

        lineSeries.setOnDataPointTapListener { _, dataPoint ->
            val month = dataPoint.x.toInt()
            val hoaDons = hoaDonList.filter {
                val date = sdf.parse(it.ngayXuatHD)
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.get(Calendar.MONTH) + 1 == month &&
                        calendar.get(Calendar.YEAR) == year  // Filter by selected year
            }
            updateListView(month, hoaDons)
            val doanhThu = dataPoint.y
            Toast.makeText(context, "Tháng: $month\nDoanh thu: $doanhThu", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateListView(year: Int) {
        val hoaDonList = dbHelper.getAllHoaDon()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val filteredHoaDons = hoaDonList.filter {
            val date = sdf.parse(it.ngayXuatHD)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.get(Calendar.YEAR) == year
        }

        val hoaDonInfo = filteredHoaDons.map { "Mã HD: ${it.maHD}, Ngày xuất: ${it.ngayXuatHD}" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, hoaDonInfo)
        hoaDonListView.adapter = adapter
    }

    private fun updateListView(month: Int, hoaDons: List<HoaDon>) {
        val hoaDonInfo = hoaDons.map { "Mã HD: ${it.maHD}, Ngày xuất: ${it.ngayXuatHD}" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, hoaDonInfo)
        hoaDonListView.adapter = adapter
    }
}
