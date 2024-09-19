package com.example.doan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import android.content.ContentValues
import com.example.doan.DatabaseHelper.Companion.TABLE_KHACHHANG
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_KH
import com.example.doan.DatabaseHelper.Companion.COLUMN_HO_TEN_KH
import com.example.doan.DatabaseHelper.Companion.COLUMN_GIOI_TINH_KH
import com.example.doan.DatabaseHelper.Companion.COLUMN_DIA_CHI_KH
import com.example.doan.DatabaseHelper.Companion.COLUMN_NGAY_SINH_KH
import com.example.doan.DatabaseHelper.Companion.COLUMN_SDT_KH
import java.util.*

class ThemKhachHangFragment : Fragment() {

    private lateinit var etMaKH: EditText
    private lateinit var etHoTenKH: EditText
    private lateinit var rgGioiTinhKH: RadioGroup
    private lateinit var etDiaChiKH: EditText
    private lateinit var etNgaySinhKH: EditText
    private lateinit var etSDTKH: EditText

    private lateinit var btnLuu: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.themkhachhang_fragment, container, false)

        etMaKH = view.findViewById(R.id.etMaKH)
        etHoTenKH = view.findViewById(R.id.etHoTenKH)
        rgGioiTinhKH = view.findViewById(R.id.rgGioiTinhKH)
        etDiaChiKH = view.findViewById(R.id.etDiaChiKH)
        etNgaySinhKH = view.findViewById(R.id.etNgaySinh)
        etSDTKH = view.findViewById(R.id.etSDTKH)

        btnLuu = view.findViewById(R.id.btnLuu)

        etNgaySinhKH.setOnClickListener {
            showDatePickerDialog()
        }

        btnLuu.setOnClickListener {
            addNewKhachHang()
        }

        return view
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                etNgaySinhKH.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun addNewKhachHang() {
        val maKH = etMaKH.text.toString()
        val hoTenKH = etHoTenKH.text.toString()
        val selectedGenderId = rgGioiTinhKH.checkedRadioButtonId
        val gioiTinhKH = view?.findViewById<RadioButton>(selectedGenderId)?.text.toString()
        val diaChiKH = etDiaChiKH.text.toString()
        val ngaySinhKH = etNgaySinhKH.text.toString()
        val sdtKH = etSDTKH.text.toString()

        if (maKH.isNotEmpty() && hoTenKH.isNotEmpty() && gioiTinhKH.isNotEmpty() && diaChiKH.isNotEmpty() && ngaySinhKH.isNotEmpty() && sdtKH.isNotEmpty()) {
            val dbHelper = DatabaseHelper(requireContext())
            val db = dbHelper.writableDatabase
            val contentValues = ContentValues().apply {
                put(COLUMN_MA_KH, maKH)
                put(COLUMN_HO_TEN_KH, hoTenKH)
                put(COLUMN_GIOI_TINH_KH, gioiTinhKH)
                put(COLUMN_DIA_CHI_KH, diaChiKH)
                put(COLUMN_NGAY_SINH_KH, ngaySinhKH)
                put(COLUMN_SDT_KH, sdtKH)
            }

            db.insert(TABLE_KHACHHANG, null, contentValues)
            Toast.makeText(requireContext(), "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}
