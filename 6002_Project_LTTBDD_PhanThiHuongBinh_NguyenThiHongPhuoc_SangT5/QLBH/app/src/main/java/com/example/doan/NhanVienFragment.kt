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
class NhanVienFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var nhanVienAdapter: NhanVienAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddNV: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnSearchNV: ImageButton
    private lateinit var editTextText: EditText



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.nhanvien_fragment, container, false)

        databaseHelper = DatabaseHelper(requireContext())
        val nhanVienList = databaseHelper.getAllNhanVien()

        recyclerView = view.findViewById(R.id.recyclerViewNhanVien)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        nhanVienAdapter = NhanVienAdapter(nhanVienList, requireActivity())
        recyclerView.adapter = nhanVienAdapter

        btnAddNV = view.findViewById(R.id.btnAddNV)
        btnAddNV.setOnClickListener {
            val fragment = ThemNhanVienFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        btnBack = view.findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val fragment = HomePageFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        btnSearchNV = view.findViewById(R.id.btnSearchNV)
        editTextText = view.findViewById(R.id.editTextText)
        btnSearchNV.setOnClickListener {
            val searchQuery = editTextText.text.toString()
            if (searchQuery.isNotEmpty()) {
                val filteredList = nhanVienList.filter { it.maNV.contains(searchQuery, true) }
                nhanVienAdapter.updateData(filteredList)
            } else {
                nhanVienAdapter.updateData(nhanVienList)
            }
        }

        return view
    }
}

