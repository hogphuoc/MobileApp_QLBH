// ThemSanPhamFragment.kt
package com.example.doan

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.io.InputStream

class ThemSanPhamFragment : Fragment() {

    private lateinit var etMaSP: EditText
    private lateinit var etTenSP: EditText
    private lateinit var etGiaBanSP: EditText
    private lateinit var etDVT: EditText
    private lateinit var etMoTaSP: EditText
    private lateinit var spinnerMaLoaiSP: Spinner
    private lateinit var btnLuuSP: Button
    private lateinit var imageView: ImageView
    private lateinit var buttonSelectImage: Button
    private val IMAGE_PICK_CODE = 1000
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.themsanpham_fragment, container, false)

        etMaSP = view.findViewById(R.id.etMaSP)
        etTenSP = view.findViewById(R.id.etTenSP)
        etGiaBanSP = view.findViewById(R.id.etGiaBanSP)
        etDVT = view.findViewById(R.id.etDVT)
        etMoTaSP = view.findViewById(R.id.etMoTaSP)
        spinnerMaLoaiSP = view.findViewById(R.id.spinnerMaLoaiSP)
        btnLuuSP = view.findViewById(R.id.btnLuuSP)
        imageView = view.findViewById(R.id.imageView)
        buttonSelectImage = view.findViewById(R.id.buttonSelectImage)

        buttonSelectImage.setOnClickListener {
            pickImageFromGallery()
        }

        btnLuuSP.setOnClickListener {
            addNewSanPham()
        }

        loadSpinnerData()

        return view
    }

    private fun loadSpinnerData() {
        val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_LOAISP, arrayOf(DatabaseHelper.COLUMN_MA_LOAI_SP), null, null, null, null, null)
        val categories = mutableListOf<String>()

        while (cursor.moveToNext()) {
            categories.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MA_LOAI_SP)))
        }

        cursor.close()
        db.close()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMaLoaiSP.adapter = adapter
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            val inputStream: InputStream? = context?.contentResolver?.openInputStream(selectedImageUri!!)
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun addNewSanPham() {
        val maSP = etMaSP.text.toString()
        val tenSP = etTenSP.text.toString()
        val giaBanSP = etGiaBanSP.text.toString().toFloatOrNull()
        val DVT = etDVT.text.toString()
        val moTaSP = etMoTaSP.text.toString()
        val maLoaiSP = spinnerMaLoaiSP.selectedItem.toString()

        if (maSP.isNotEmpty() && tenSP.isNotEmpty() && giaBanSP != null && DVT.isNotEmpty() && selectedImageUri != null && moTaSP.isNotEmpty() && maLoaiSP.isNotEmpty()) {
            val dbHelper = DatabaseHelper(requireContext())
            val db = dbHelper.writableDatabase
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.COLUMN_MA_SP, maSP)
                put(DatabaseHelper.COLUMN_TEN_SP, tenSP)
                put(DatabaseHelper.COLUMN_GIA_BAN, giaBanSP)
                put(DatabaseHelper.COLUMN_DVT, DVT)
                put(DatabaseHelper.COLUMN_HINH_ANH, selectedImageUri.toString()) // Lưu đường dẫn hình ảnh
                put(DatabaseHelper.COLUMN_MO_TA_SP, moTaSP)
                put(DatabaseHelper.COLUMN_MA_LOAI_SP, maLoaiSP)
            }

            db.insert(DatabaseHelper.TABLE_SANPHAM, null, contentValues)
            Toast.makeText(requireContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}
