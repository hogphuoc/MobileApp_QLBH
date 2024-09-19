package com.example.doan

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.example.doan.DatabaseHelper.Companion.TABLE_KHACHHANG
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_KH
import com.example.doan.DatabaseHelper.Companion.TABLE_NHANVIEN
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_NV
import com.example.doan.DatabaseHelper.Companion.TABLE_SANPHAM
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_SP
import com.example.doan.DatabaseHelper.Companion.TABLE_HOADON
import com.example.doan.DatabaseHelper.Companion.COLUMN_PT_TTHD
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_HD_FKhd
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_KH_FKhd
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_NV_FKhd
import com.example.doan.DatabaseHelper.Companion.COLUMN_NGAY_XUAT_HD
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_HD
import com.example.doan.DatabaseHelper.Companion.TABLE_CTHOADON
import com.example.doan.DatabaseHelper.Companion.COLUMN_SO_LUONG_MUA
import com.example.doan.DatabaseHelper.Companion.COLUMN_DON_GIA_BAN
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_SP_FKcthd
import android.widget.AdapterView

class ThemHoaDonFragment : Fragment() {

    private lateinit var maHD: TextInputEditText
    private lateinit var maKH: Spinner
    private lateinit var maNV: Spinner
    private lateinit var ngayXuatHD: TextInputEditText
    private lateinit var pttt: TextInputEditText
    private lateinit var maSP: Spinner
    private lateinit var soLuongMua: TextInputEditText
    private lateinit var donGiaBan: TextInputEditText
    private lateinit var btnLuu: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.themhoadon_fragment, container, false)

        maHD = view.findViewById(R.id.edtMaHD)
        maKH = view.findViewById(R.id.spinnerMaKH)
        maNV = view.findViewById(R.id.spinnerMaNV)
        ngayXuatHD = view.findViewById(R.id.edtNgayXuatHD)
        pttt = view.findViewById(R.id.edtPTTT)
        maSP = view.findViewById(R.id.spinnerMaSP)
        soLuongMua = view.findViewById(R.id.edtSoLuongMua)
        donGiaBan = view.findViewById(R.id.edtDonGiaBan)
        btnLuu = view.findViewById(R.id.btnLuu)

        // Set today's date
        ngayXuatHD.setText(java.time.LocalDate.now().toString())

        // Load data into spinners
        loadSpinnerData()

        // Set listener for spinnerMaSP to update donGiaBan
        maSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMaSP = parent?.getItemAtPosition(position).toString()
                updateDonGiaBan(selectedMaSP)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        btnLuu.setOnClickListener {
            // Lấy thông tin từ các trường nhập liệu
            val maHDValue = maHD.text.toString()
            val maKHValue = maKH.selectedItem.toString()
            val maNVValue = maNV.selectedItem.toString()
            val ngayXuatHDValue = ngayXuatHD.text.toString()
            val ptttValue = pttt.text.toString()
            val maSPValue = maSP.selectedItem.toString()
            val soLuongMuaValue = soLuongMua.text.toString().toInt()
            val donGiaBanValue = donGiaBan.text.toString().toDouble()

            // Thêm hoá đơn và chi tiết hoá đơn vào cơ sở dữ liệu
            addHoaDon(maHDValue, maKHValue, maNVValue, ngayXuatHDValue, ptttValue, maSPValue, soLuongMuaValue, donGiaBanValue)
        }
        return view
    }

    private fun loadSpinnerData() {
        val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.readableDatabase

        // Load mã khách hàng
        val khList = ArrayList<String>()
        val cursorKH = db.query(TABLE_KHACHHANG, arrayOf(COLUMN_MA_KH), null, null, null, null, null)
        if (cursorKH.moveToFirst()) {
            do {
                khList.add(cursorKH.getString(cursorKH.getColumnIndexOrThrow(COLUMN_MA_KH)))
            } while (cursorKH.moveToNext())
        }
        cursorKH.close()

        // Load mã nhân viên
        val nvList = ArrayList<String>()
        val cursorNV = db.query(TABLE_NHANVIEN, arrayOf(COLUMN_MA_NV), null, null, null, null, null)
        if (cursorNV.moveToFirst()) {
            do {
                nvList.add(cursorNV.getString(cursorNV.getColumnIndexOrThrow(COLUMN_MA_NV)))
            } while (cursorNV.moveToNext())
        }
        cursorNV.close()

        // Load mã sản phẩm
        val spList = ArrayList<String>()
        val cursorSP = db.query(TABLE_SANPHAM, arrayOf(COLUMN_MA_SP), null, null, null, null, null)
        if (cursorSP.moveToFirst()) {
            do {
                spList.add(cursorSP.getString(cursorSP.getColumnIndexOrThrow(COLUMN_MA_SP)))
            } while (cursorSP.moveToNext())
        }
        cursorSP.close()

        // Set adapters
        val khAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, khList)
        khAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maKH.adapter = khAdapter

        val nvAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nvList)
        nvAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maNV.adapter = nvAdapter

        val spAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spList)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        maSP.adapter = spAdapter

        // Đóng cơ sở dữ liệu
        db.close()
    }

    private fun updateDonGiaBan(maSP: String) {
        val dbHelper = DatabaseHelper(requireContext())
        val giaBan = dbHelper.getGiaBan(maSP)
        donGiaBan.setText(giaBan.toString())
    }

    private fun addHoaDon(
        maHD: String, maKH: String, maNV: String, ngayXuatHD: String,
        pttt: String, maSP: String, soLuongMua: Int, donGiaBan: Double
    ) {
        val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.writableDatabase


        val hoaDonValues = ContentValues().apply {
            put(COLUMN_MA_HD_FKhd, maHD)
            put(COLUMN_MA_KH_FKhd, maKH)
            put(COLUMN_MA_NV_FKhd, maNV)
            put(COLUMN_NGAY_XUAT_HD, ngayXuatHD)
            put(COLUMN_PT_TTHD, pttt)
        }
        db.insert(TABLE_HOADON, null, hoaDonValues)

        // Thêm chi tiết hoá đơn vào bảng CTHoaDon
        val cthoaDonValues = ContentValues().apply {
            put(COLUMN_MA_HD, maHD)
            put(COLUMN_MA_SP_FKcthd, maSP)
            put(COLUMN_SO_LUONG_MUA, soLuongMua)
            put(COLUMN_DON_GIA_BAN, donGiaBan)
        }
        db.insert(TABLE_CTHOADON, null, cthoaDonValues)

        // Đóng cơ sở dữ liệu
        db.close()
        Toast.makeText(requireContext(), "Thêm hoá đơn thành công", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }
}