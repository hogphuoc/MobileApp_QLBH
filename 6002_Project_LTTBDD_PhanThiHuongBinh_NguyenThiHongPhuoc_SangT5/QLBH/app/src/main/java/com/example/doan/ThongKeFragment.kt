package com.example.doan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


class ThongKeFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.thongke_fragment, container, false)
        dbHelper = DatabaseHelper(requireContext())

        val btnThongKeDoanhThu = view.findViewById<Button>(R.id.buttondoanhthu)
        btnThongKeDoanhThu.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, DoanhThuFragment() )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        val btnThongKeSLTon = view.findViewById<Button>(R.id.buttonslton)
        btnThongKeSLTon.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, TonKhoFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val btnThongkeKH = view.findViewById<Button>(R.id.buttonkhachhang)
        btnThongkeKH.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, ThongKeKhachHangFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        val btnThoat = view.findViewById<Button>(R.id.btnThoat)
        btnThoat.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, HomePageFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return view
    }

}