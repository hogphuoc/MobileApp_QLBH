package com.example.doan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import android.widget.EditText

class KhachHangFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var khachHangAdapter: KhachHangAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: ImageButton
    private lateinit var btnBackKH: ImageButton
    private lateinit var btnSearchKH: ImageButton
    private lateinit var editTextText: EditText



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.khachhang_fragment, container, false)

        databaseHelper = DatabaseHelper(requireContext())
        val khachHangList = databaseHelper.getAllKhachHang()

        recyclerView = view.findViewById(R.id.recyclerViewKhachHang)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        khachHangAdapter = KhachHangAdapter(khachHangList, requireActivity())
        recyclerView.adapter = khachHangAdapter

        btnAdd = view.findViewById(R.id.btnAddKH)
        btnAdd.setOnClickListener {
            val fragment = ThemKhachHangFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        btnBackKH = view.findViewById(R.id.btnBackKH)
        btnBackKH.setOnClickListener {
            val fragment = HomePageFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        btnSearchKH = view.findViewById(R.id.btnSearchKH)
        editTextText = view.findViewById(R.id.editTextText)
        btnSearchKH.setOnClickListener {
            val searchQuery = editTextText.text.toString()
            if (searchQuery.isNotEmpty()) {
                val filteredList = khachHangList.filter { it.maKH.contains(searchQuery, true) }
                khachHangAdapter.updateData(filteredList)
            } else {
                khachHangAdapter.updateData(khachHangList)
            }
        }
        return view
    }
}












