package com.example.doan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import android.widget.EditText

class HoaDonFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var hoaDonAdapter: HoaDonAdapter
    private lateinit var btnAdd: ImageButton
    private lateinit var btnBackHD: ImageButton



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.hoadon_fragment, container, false)
        btnAdd = view.findViewById(R.id.btnAddHD)
        btnAdd.setOnClickListener {
            val fragment = ThemHoaDonFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
            openThemHoaDonFragment()
        }
        btnBackHD = view.findViewById(R.id.btnBackHD)
        btnBackHD.setOnClickListener {
            val fragment = HomePageFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        databaseHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.recyclerViewHoaDon)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        loadHoaDonData()
        return view
    }

    private fun loadHoaDonData() {
        val hoaDonList = databaseHelper.getAllHoaDon()
        hoaDonAdapter = HoaDonAdapter(hoaDonList) { hoaDon ->
            openCTHoaDonFragment(hoaDon)
        }
        recyclerView.adapter = hoaDonAdapter
    }

    private fun openCTHoaDonFragment(hoaDon: HoaDon) {
        val fragment = CTHoaDonFragment.newInstance(hoaDon.maHD)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun openThemHoaDonFragment() {
        val fragment = ThemHoaDonFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}