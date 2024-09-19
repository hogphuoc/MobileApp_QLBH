package com.example.doan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SanPhamFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sanPhamAdapter: SanPhamAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnBackSP: ImageButton
    private lateinit var editTextSearch: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sanpham_fragment, container, false)

        databaseHelper = DatabaseHelper(requireContext())
        val sanPhamList = databaseHelper.getAllSanPham()

        recyclerView = view.findViewById(R.id.recyclerViewSanPham)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        sanPhamAdapter = SanPhamAdapter(sanPhamList) { sanPham ->
            // Mở chi tiết sản phẩm
            val fragment = CTSanPhamFragment()
            val bundle = Bundle().apply {
                putString("maSP", sanPham.maSP)
                putString("tenSP", sanPham.tenSP)
                putFloat("giaBanSP", sanPham.giaban)
                putString("dvt", sanPham.DVT)
                putString("hinhAnhSP", sanPham.hinhanh) // Thêm hình ảnh
                putString("moTaSP", sanPham.mota)
                putString("maLoaiSP", sanPham.maLoai)
            }
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = sanPhamAdapter


        btnAdd = view.findViewById(R.id.btnAddSP)
        btnAdd.setOnClickListener {
            val fragment = ThemSanPhamFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        btnSearch = view.findViewById(R.id.btnSearch)
        editTextSearch = view.findViewById(R.id.editTextText)
        btnSearch.setOnClickListener {
            val searchQuery = editTextSearch.text.toString()
            if (searchQuery.isNotEmpty()) {
                val filteredList = sanPhamList.filter { it.maSP.contains(searchQuery, true) }
                sanPhamAdapter.updateData(filteredList)
            } else {
                sanPhamAdapter.updateData(sanPhamList)
            }
        }
        btnBackSP = view.findViewById(R.id.btnBackSanPham)
        btnBackSP.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomePageFragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        refreshSanPhamList()
    }

    private fun refreshSanPhamList() {
        val sanPhamList = databaseHelper.getAllSanPham()
        sanPhamAdapter.updateData(sanPhamList)
    }
}
