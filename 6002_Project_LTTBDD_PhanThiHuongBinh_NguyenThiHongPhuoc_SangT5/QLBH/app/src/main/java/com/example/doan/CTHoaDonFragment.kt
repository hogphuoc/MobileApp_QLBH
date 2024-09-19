package com.example.doan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import android.content.ContentValues
import android.widget.Toast
import com.example.doan.DatabaseHelper.Companion.TABLE_CTHOADON
import com.example.doan.DatabaseHelper.Companion.TABLE_HOADON
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_HD_FKhd
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_KH_FKhd
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_NV_FKhd
import com.example.doan.DatabaseHelper.Companion.COLUMN_NGAY_XUAT_HD
import com.example.doan.DatabaseHelper.Companion.COLUMN_PT_TTHD
import com.example.doan.DatabaseHelper.Companion.COLUMN_SO_LUONG_MUA
import com.example.doan.DatabaseHelper.Companion.COLUMN_DON_GIA_BAN
import android.text.Editable
import android.text.TextWatcher

class CTHoaDonFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var maHD: String

    private lateinit var maHDInput: TextInputEditText
    private lateinit var maKHInput: TextInputEditText
    private lateinit var maNVInput: TextInputEditText
    private lateinit var ngayXuatHDInput: TextInputEditText
    private lateinit var ptTTInput: TextInputEditText

    private lateinit var soLuongMuaInput: TextInputEditText
    private lateinit var donGiaBanInput: TextInputEditText
    private lateinit var thanhTienInput: TextInputEditText

    private lateinit var editButton: Button
    private lateinit var deleteButton: Button

    companion object {
        private const val ARG_MA_HD = "maHD"

        fun newInstance(maHD: String): CTHoaDonFragment {
            val fragment = CTHoaDonFragment()
            val args = Bundle()
            args.putString(ARG_MA_HD, maHD)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            maHD = it.getString(ARG_MA_HD).orEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cthoadon_fragment, container, false)
        databaseHelper = DatabaseHelper(requireContext())

        maHDInput = view.findViewById(R.id.maHDInput)
        maKHInput = view.findViewById(R.id.maKHInput)
        maNVInput = view.findViewById(R.id.maNVInput)
        ngayXuatHDInput = view.findViewById(R.id.ngayXuatHDInput)
        ptTTInput = view.findViewById(R.id.ptTTInput)

        soLuongMuaInput = view.findViewById(R.id.soLuongMuaInput)
        donGiaBanInput = view.findViewById(R.id.donGiaBanInput)
        thanhTienInput = view.findViewById(R.id.thanhTienInput)

        editButton = view.findViewById(R.id.btnLuuHD)
        deleteButton = view.findViewById(R.id.deleteButton)

        loadInvoiceDetails()

        soLuongMuaInput.addTextChangedListener(textWatcher)
        donGiaBanInput.addTextChangedListener(textWatcher)

        editButton.setOnClickListener {
            updateInvoiceDetails()
        }

        deleteButton.setOnClickListener {
            deleteInvoiceDetails()
        }

        return view
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            calculateThanhTien()
        }
    }

    private fun calculateThanhTien() {
        val soLuong = soLuongMuaInput.text.toString().toIntOrNull() ?: 0
        val donGia = donGiaBanInput.text.toString().toFloatOrNull() ?: 0f
        val thanhTien = soLuong * donGia
        thanhTienInput.setText(thanhTien.toString())
    }

    private fun loadInvoiceDetails() {
        val db = databaseHelper.readableDatabase

        // Fetch details from HoaDon table
        val hoaDonCursor = db.rawQuery("SELECT * FROM $TABLE_HOADON WHERE $COLUMN_MA_HD_FKhd = ?", arrayOf(maHD))
        if (hoaDonCursor.moveToFirst()) {
            maHDInput.setText(hoaDonCursor.getString(hoaDonCursor.getColumnIndexOrThrow(COLUMN_MA_HD_FKhd)))
            maKHInput.setText(hoaDonCursor.getString(hoaDonCursor.getColumnIndexOrThrow(COLUMN_MA_KH_FKhd)))
            maNVInput.setText(hoaDonCursor.getString(hoaDonCursor.getColumnIndexOrThrow(COLUMN_MA_NV_FKhd)))
            ngayXuatHDInput.setText(hoaDonCursor.getString(hoaDonCursor.getColumnIndexOrThrow(COLUMN_NGAY_XUAT_HD)))
            ptTTInput.setText(hoaDonCursor.getString(hoaDonCursor.getColumnIndexOrThrow(COLUMN_PT_TTHD)))
        }
        hoaDonCursor.close()

        // Fetch details from CTHoaDon table
        val cthoaDonCursor = db.rawQuery("SELECT * FROM $TABLE_CTHOADON WHERE $COLUMN_MA_HD_FKhd = ?", arrayOf(maHD))
        if (cthoaDonCursor.moveToFirst()) {
            soLuongMuaInput.setText(cthoaDonCursor.getString(cthoaDonCursor.getColumnIndexOrThrow(COLUMN_SO_LUONG_MUA)))
            donGiaBanInput.setText(cthoaDonCursor.getString(cthoaDonCursor.getColumnIndexOrThrow(COLUMN_DON_GIA_BAN)))
            calculateThanhTien()
        }
        cthoaDonCursor.close()
    }

    private fun updateInvoiceDetails() {
        val db = databaseHelper.writableDatabase

        // Update HoaDon table
        val hoaDonValues = ContentValues().apply {
            put(COLUMN_MA_HD_FKhd, maHDInput.text.toString())
            put(COLUMN_MA_KH_FKhd, maKHInput.text.toString())
            put(COLUMN_MA_NV_FKhd, maNVInput.text.toString())
            put(COLUMN_NGAY_XUAT_HD, ngayXuatHDInput.text.toString())
            put(COLUMN_PT_TTHD, ptTTInput.text.toString())
        }
        db.update(TABLE_HOADON, hoaDonValues, "$COLUMN_MA_HD_FKhd = ?", arrayOf(maHD))

        // Update CTHoaDon table
        val cthoaDonValues = ContentValues().apply {
            put(COLUMN_SO_LUONG_MUA, soLuongMuaInput.text.toString().toInt())
            put(COLUMN_DON_GIA_BAN, donGiaBanInput.text.toString().toFloat())
        }

        // Assuming you have a list of products to update, adjust accordingly
        db.update(TABLE_CTHOADON, cthoaDonValues, "$COLUMN_MA_HD_FKhd = ? ", arrayOf(maHD)) // Adjust SP001 as needed
        Toast.makeText(requireContext(), "Hoá đơn đã được cập nhật", Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun deleteInvoiceDetails() {
        val db = databaseHelper.writableDatabase
        db.delete(TABLE_HOADON, "$COLUMN_MA_HD_FKhd = ?", arrayOf(maHD))
        db.delete(TABLE_CTHOADON, "$COLUMN_MA_HD_FKhd = ?", arrayOf(maHD))
        Toast.makeText(requireContext(), "Hoá đơn đã được xoá", Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.popBackStack()
    }
}


