package com.example.doan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


class CTNhanVienFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etnhanVien: EditText
    private lateinit var etHoTen: EditText
    private lateinit var etGioiTinh: EditText
    private lateinit var etDiaChi: EditText
    private lateinit var etNgaySinh: EditText
    private lateinit var etSDT: EditText
    private lateinit var etMaBP: EditText
    private lateinit var etMaCH: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ctnhanvien_fragment, container, false)

        etnhanVien = view.findViewById(R.id.etMaNV)
        etHoTen = view.findViewById(R.id.etHoTenNV)
        etGioiTinh = view.findViewById(R.id.etGioiTinhKH)
        etDiaChi = view.findViewById(R.id.etDiaChiNV)
        etNgaySinh = view.findViewById(R.id.etNgaySinhNV)
        etSDT = view.findViewById(R.id.etSDTNV)
        etMaBP = view.findViewById(R.id.etMaBP)
        etMaCH = view.findViewById(R.id.etMaCH)
        val btnSua: Button = view.findViewById(R.id.btnSua)
        val btnXoa: Button = view.findViewById(R.id.btnXoa)

        databaseHelper = DatabaseHelper(requireContext())

        val maNV = arguments?.getString("maNV")
        val hoTenNV = arguments?.getString("hoTenNV")
        val gioiTinhNV = arguments?.getString("gioiTinhNV")
        val diaChiNV = arguments?.getString("diaChiNV")
        val ngaySinhNV = arguments?.getString("ngaySinhNV")
        val sdtNV = arguments?.getString("sdtNV")
        val maBP = arguments?.getString("maBP")
        val maCH = arguments?.getString("maCH")

        etnhanVien.setText(maNV)
        etHoTen.setText(hoTenNV)
        etGioiTinh.setText(gioiTinhNV)
        etDiaChi.setText(diaChiNV)
        etNgaySinh.setText(ngaySinhNV)
        etSDT.setText(sdtNV)
        etMaBP.setText(maBP)
        etMaCH.setText(maCH)

        btnSua.setOnClickListener {
            updateNhanVien()
        }

        btnXoa.setOnClickListener {
            deleteNhanVien()
        }

        return view
    }

    private fun updateNhanVien() {
        val maNV = etnhanVien.text.toString()
        val hoTenNV = etHoTen.text.toString()
        val gioiTinhNV = etGioiTinh.text.toString()
        val diaChiNV = etDiaChi.text.toString()
        val ngaySinhNV = etNgaySinh.text.toString()
        val sdtNV = etSDT.text.toString()
        val maBP = etMaBP.text.toString()
        val maCH = etMaCH.text.toString()


        val db = databaseHelper.writableDatabase
        val query =
            """UPDATE ${DatabaseHelper.TABLE_NHANVIEN} SET ${DatabaseHelper.COLUMN_HO_TEN_NV} = ?, 
             ${DatabaseHelper.COLUMN_GIOI_TINH_NV} = ?, ${DatabaseHelper.COLUMN_DIA_CHI_NV} = ?, 
             ${DatabaseHelper.COLUMN_NGAY_SINH_NV} = ?, ${DatabaseHelper.COLUMN_SDT_NV} = ?, ${DatabaseHelper.COLUMN_MA_BP} = ?, 
             ${DatabaseHelper.COLUMN_MA_CH} = ? WHERE ${DatabaseHelper.COLUMN_MA_NV} = ?"""
        db.execSQL(query, arrayOf(hoTenNV, gioiTinhNV, diaChiNV, ngaySinhNV, sdtNV, maBP, maCH ,maNV))

        Toast.makeText(requireContext(), "Nhân viên đã được cập nhật", Toast.LENGTH_SHORT).show()
    }

    private fun deleteNhanVien() {
        val maNV = etnhanVien.text.toString()

        val db = databaseHelper.writableDatabase
        val query = "DELETE FROM ${DatabaseHelper.TABLE_NHANVIEN} WHERE ${DatabaseHelper.COLUMN_MA_NV} = ?"
        db.execSQL(query, arrayOf(maNV))

        Toast.makeText(requireContext(), "Nhân viên đã được xoá", Toast.LENGTH_SHORT).show()

        // Navigate back to previous fragment or activity
        requireActivity().supportFragmentManager.popBackStack()
    }
}


