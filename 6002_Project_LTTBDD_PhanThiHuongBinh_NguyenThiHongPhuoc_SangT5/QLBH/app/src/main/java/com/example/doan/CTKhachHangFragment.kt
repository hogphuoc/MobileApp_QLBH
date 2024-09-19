package com.example.doan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class CTKhachHangFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etMaKH: EditText
    private lateinit var etHoTenKH: EditText
    private lateinit var etGioiTinhKH: EditText
    private lateinit var etDiaChiKH: EditText
    private lateinit var etNgaySinhKH: EditText
    private lateinit var etSdtKH: EditText
    private lateinit var btnUpdateKH: Button
    private lateinit var btnDeleteKH: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ctkhachhang_fragment, container, false)

        etMaKH = view.findViewById(R.id.etMaKH)
        etHoTenKH = view.findViewById(R.id.etHoTenKH)
        etGioiTinhKH = view.findViewById(R.id.etGioiTinhKH)
        etDiaChiKH = view.findViewById(R.id.etDiaChiKH)
        etNgaySinhKH = view.findViewById(R.id.etNgaySinh)
        etSdtKH = view.findViewById(R.id.etSDTKH)
        btnUpdateKH = view.findViewById(R.id.btnUpdateKH)
        btnDeleteKH = view.findViewById(R.id.btnDeleteKH)

        databaseHelper = DatabaseHelper(requireContext())

        val maKH = arguments?.getString("maKH")
        val hoTenKH = arguments?.getString("hoTenKH")
        val gioiTinhKH = arguments?.getString("gioiTinhKH")
        val diaChiKH = arguments?.getString("diaChiKH")
        val ngaySinhKH = arguments?.getString("ngaySinhKH")
        val sdtKH = arguments?.getString("sdtKH")

        etMaKH.setText(maKH)
        etHoTenKH.setText(hoTenKH)
        etGioiTinhKH.setText(gioiTinhKH)
        etDiaChiKH.setText(diaChiKH)
        etNgaySinhKH.setText(ngaySinhKH)
        etSdtKH.setText(sdtKH)

        btnUpdateKH.setOnClickListener {
            updateKhachHang()
        }

        btnDeleteKH.setOnClickListener {
            deleteKhachHang()
        }

        return view
    }

    private fun updateKhachHang() {
        val maKH = etMaKH.text.toString()
        val hoTenKH = etHoTenKH.text.toString()
        val gioiTinhKH = etGioiTinhKH.text.toString()
        val diaChiKH = etDiaChiKH.text.toString()
        val ngaySinhKH = etNgaySinhKH.text.toString()
        val sdtKH = etSdtKH.text.toString()

        val db = databaseHelper.writableDatabase
        val query = "UPDATE ${DatabaseHelper.TABLE_KHACHHANG} SET ${DatabaseHelper.COLUMN_HO_TEN_KH} = ?, " +
                "${DatabaseHelper.COLUMN_GIOI_TINH_KH} = ?, ${DatabaseHelper.COLUMN_DIA_CHI_KH} = ?, " +
                "${DatabaseHelper.COLUMN_NGAY_SINH_KH} = ?, ${DatabaseHelper.COLUMN_SDT_KH} = ? " +
                "WHERE ${DatabaseHelper.COLUMN_MA_KH} = ?"
        db.execSQL(query, arrayOf(hoTenKH, gioiTinhKH, diaChiKH, ngaySinhKH, sdtKH, maKH))

        Toast.makeText(requireContext(), "Khách hàng đã được cập nhật", Toast.LENGTH_SHORT).show()
    }

    private fun deleteKhachHang() {
        val maKH = etMaKH.text.toString()

        val db = databaseHelper.writableDatabase
        val query = "DELETE FROM ${DatabaseHelper.TABLE_KHACHHANG} WHERE ${DatabaseHelper.COLUMN_MA_KH} = ?"
        db.execSQL(query, arrayOf(maKH))

        Toast.makeText(requireContext(), "Khách hàng đã được xoá", Toast.LENGTH_SHORT).show()

        // Navigate back to previous fragment or activity
        requireActivity().supportFragmentManager.popBackStack()
    }
}
