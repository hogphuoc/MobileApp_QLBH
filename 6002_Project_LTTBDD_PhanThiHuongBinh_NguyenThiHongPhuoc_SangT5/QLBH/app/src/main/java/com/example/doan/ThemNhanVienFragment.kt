package com.example.doan

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_NV
import com.example.doan.DatabaseHelper.Companion.COLUMN_HO_TEN_NV
import com.example.doan.DatabaseHelper.Companion.COLUMN_GIOI_TINH_NV
import com.example.doan.DatabaseHelper.Companion.COLUMN_DIA_CHI_NV
import com.example.doan.DatabaseHelper.Companion.COLUMN_NGAY_SINH_NV
import com.example.doan.DatabaseHelper.Companion.COLUMN_SDT_NV
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_BP_FKnv
import com.example.doan.DatabaseHelper.Companion.COLUMN_MA_CH_FKnv
import com.example.doan.DatabaseHelper.Companion.TABLE_NHANVIEN
import java.util.*
import android.content.ContentValues
class ThemNhanVienFragment : Fragment() {

    private lateinit var etMaNV: EditText
    private lateinit var etHoTenNV: EditText
    private lateinit var rgGioiTinh: RadioGroup
    private lateinit var rbNam: RadioButton
    private lateinit var rbNu: RadioButton
    private lateinit var etDiaChiNV: EditText
    private lateinit var etNgaySinh: EditText
    private lateinit var etSDTNV: EditText
    private lateinit var etMaBP: EditText
    private lateinit var etMaCH: EditText
    private lateinit var btnLuu: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.themnhanvien_fragment, container, false)

        etMaNV = view.findViewById(R.id.etMaNV)
        etHoTenNV = view.findViewById(R.id.etHoTenNV)
        rgGioiTinh = view.findViewById(R.id.rgGioiTinh)
        rbNam = view.findViewById(R.id.rbNam)
        rbNu = view.findViewById(R.id.rbNu)
        etDiaChiNV = view.findViewById(R.id.etDiaChiNV)
        etNgaySinh = view.findViewById(R.id.etNgaySinh)
        etSDTNV = view.findViewById(R.id.etSDTNV)
        etMaBP = view.findViewById(R.id.etMaBP)
        etMaCH = view.findViewById(R.id.etMaCH)
        btnLuu = view.findViewById(R.id.btnLuu)

        etNgaySinh.setOnClickListener {
            showDatePickerDialog()
        }

        btnLuu.setOnClickListener {
            addNewNhanVien()
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
                etNgaySinh.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun addNewNhanVien() {
        val maNV = etMaNV.text.toString()
        val hoTenNV = etHoTenNV.text.toString()
        val gioiTinhNV = if (rbNam.isChecked) "Nam" else "Nữ"
        val ngaySinh = etNgaySinh.text.toString()
        val diaChiNV = etDiaChiNV.text.toString()
        val sdtNV = etSDTNV.text.toString()
        val maBP = etMaBP.text.toString()
        val maCH = etMaCH.text.toString()

        if (maNV.isNotEmpty() && hoTenNV.isNotEmpty() && gioiTinhNV.isNotEmpty() && diaChiNV.isNotEmpty() && ngaySinh != null && sdtNV.isNotEmpty() && maBP.isNotEmpty() && maCH.isNotEmpty()) {
            val dbHelper = DatabaseHelper(requireContext())
            val db = dbHelper.writableDatabase
            val contentValues = ContentValues().apply {
                put(COLUMN_MA_NV, maNV)
                put(COLUMN_HO_TEN_NV, hoTenNV)
                put(COLUMN_GIOI_TINH_NV, gioiTinhNV)
                put(COLUMN_DIA_CHI_NV, diaChiNV)
                put(COLUMN_NGAY_SINH_NV, ngaySinh)
                put(COLUMN_SDT_NV, sdtNV)
                put(COLUMN_MA_BP_FKnv, maBP)
                put(COLUMN_MA_CH_FKnv, maCH)
            }

            db.insert(TABLE_NHANVIEN, null, contentValues)
            Toast.makeText(requireContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}
