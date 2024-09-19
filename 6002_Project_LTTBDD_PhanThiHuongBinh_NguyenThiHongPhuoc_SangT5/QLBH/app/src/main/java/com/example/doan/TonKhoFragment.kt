package com.example.doan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.helper.StaticLabelsFormatter

class TonKhoFragment : Fragment() {

    private lateinit var graph: GraphView
    private lateinit var storeSpinner: Spinner
    private lateinit var dataHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.tonkho_fragment, container, false)

        graph = view.findViewById(R.id.graph)
        storeSpinner = view.findViewById(R.id.storeSpinner)
        dataHelper = DatabaseHelper(requireContext())

        setupSpinner()
        return view
    }

    private fun setupSpinner() {
        val storeList = dataHelper.getStoreList() // Lấy danh sách mã cửa hàng từ DatabaseHelper
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, storeList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        storeSpinner.adapter = adapter

        storeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedStore = storeList[position]
                val tonKhoData = getTonKhoData(selectedStore)
                setupGraph(tonKhoData)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun getTonKhoData(store: String): List<TonKho> {
        return dataHelper.getAllTonKho().filter { it.maCH == store }
    }

    private fun setupGraph(tonKhoData: List<TonKho>) {
        val series = BarGraphSeries<DataPoint>()
        val productLabels = tonKhoData.map { it.maSP }.toTypedArray()

        for ((index, tonKho) in tonKhoData.withIndex()) {
            series.appendData(
                DataPoint(index.toDouble(), tonKho.soLuongTon.toDouble()),
                true,
                tonKhoData.size
            )
        }

        graph.removeAllSeries()
        graph.addSeries(series)

        // Sử dụng CustomLabelFormatter để định dạng nhãn trục x
        graph.gridLabelRenderer.labelFormatter = CustomLabelFormatter(productLabels)

        // Đặt khoảng cách cho các nhãn trên trục x
        graph.gridLabelRenderer.numHorizontalLabels = productLabels.size
        graph.gridLabelRenderer.horizontalAxisTitle = "Mã sản phẩm"
        graph.gridLabelRenderer.verticalAxisTitle = "Số lượng tồn"

        // Đặt khoảng cách trục x và trục y
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(-0.5)
        graph.viewport.setMaxX((tonKhoData.size - 0.5).toDouble())

        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMinY(0.0)
        graph.viewport.setMaxY(series.highestValueY + 10) // Thêm một chút không gian trên cùng

        // Bổ sung khoảng trống giữa các cột để nhãn không bị chồng lên nhau
        series.spacing = 50

        // Tạo khoảng trống dưới nhãn trục x
        graph.gridLabelRenderer.padding = 32 // Đặt padding tùy theo nhu cầu

        // Kích hoạt cuộn và thu phóng
        graph.viewport.isScalable = true // Kích hoạt thu phóng
        graph.viewport.isScrollable = true // Kích hoạt cuộn ngang

        // Thêm sự kiện chạm vào các cột
        series.setOnDataPointTapListener { _, dataPoint ->
            val index = dataPoint.x.toInt()
            if (index in productLabels.indices) {
                val maSP = productLabels[index]
                val soLuongTon = dataPoint.y.toInt()
                // Hiển thị thông tin mã sản phẩm và số lượng tồn
                Toast.makeText(context, "Mã sản phẩm: $maSP\nSố lượng tồn: $soLuongTon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
