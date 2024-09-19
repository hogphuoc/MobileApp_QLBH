package com.example.doan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

class CTSanPhamFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etMaSP: EditText
    private lateinit var etTenSP: EditText
    private lateinit var etGiaBanSP: EditText
    private lateinit var etDVT: EditText
    private lateinit var etMoTaSP: EditText
    private lateinit var etMaLoaiSP: EditText
    private lateinit var btnUpdateSP: Button
    private lateinit var btnDeleteSP: Button
    private lateinit var imageViewProduct: ImageView

    private val IMAGE_REQUEST_CODE = 1001
    private var imageUri: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ctsanpham_fragment, container, false)

        etMaSP = view.findViewById(R.id.etMaSP)
        etTenSP = view.findViewById(R.id.etTenSP)
        etGiaBanSP = view.findViewById(R.id.etGiaBanSP)
        etDVT = view.findViewById(R.id.etDVT)
        etMoTaSP = view.findViewById(R.id.etMoTaSP)
        etMaLoaiSP = view.findViewById(R.id.etMaLoaiSP)
        btnUpdateSP = view.findViewById(R.id.btnUpdateSP)
        btnDeleteSP = view.findViewById(R.id.btnDeleteSP)
        imageViewProduct = view.findViewById(R.id.imageViewctsp)

        databaseHelper = DatabaseHelper(requireContext())

        val maSP = arguments?.getString("maSP")
        val tenSP = arguments?.getString("tenSP")
        val giaBanSP = arguments?.getFloat("giaBanSP")
        val dvt = arguments?.getString("dvt")
        val moTaSP = arguments?.getString("moTaSP")
        val maLoaiSP = arguments?.getString("maLoaiSP")
        val hinhAnhSP = arguments?.getString("hinhAnhSP") // Lấy URI hình ảnh

        etMaSP.setText(maSP)
        etTenSP.setText(tenSP)
        etGiaBanSP.setText(giaBanSP?.toString())
        etDVT.setText(dvt)
        etMoTaSP.setText(moTaSP)
        etMaLoaiSP.setText(maLoaiSP)

        if (hinhAnhSP != null) {
            imageUri = Uri.parse(hinhAnhSP)
            imageViewProduct.setImageURI(imageUri) // Hiển thị hình ảnh
        }

        imageViewProduct.setOnClickListener {
            openImagePicker()
        }

        btnUpdateSP.setOnClickListener {
            updateSanPham()
        }

        btnDeleteSP.setOnClickListener {
            deleteSanPham()
        }

        return view
    }

    private fun updateSanPham() {
        val maSP = etMaSP.text.toString()
        val tenSP = etTenSP.text.toString()
        val giaBanSP = etGiaBanSP.text.toString().toFloatOrNull()
        val dvt = etDVT.text.toString()
        val moTaSP = etMoTaSP.text.toString()
        val maLoaiSP = etMaLoaiSP.text.toString()
        val hinhAnh = imageUri.toString()

        if (giaBanSP == null) {
            Toast.makeText(requireContext(), "Giá bán không hợp lệ", Toast.LENGTH_SHORT).show()
            return
        }

        val db = databaseHelper.writableDatabase
        val query = "UPDATE ${DatabaseHelper.TABLE_SANPHAM} SET ${DatabaseHelper.COLUMN_TEN_SP} = ?, " +
                "${DatabaseHelper.COLUMN_GIA_BAN} = ?, ${DatabaseHelper.COLUMN_DVT} = ?, " +
                "${DatabaseHelper.COLUMN_MO_TA_SP} = ?, ${DatabaseHelper.COLUMN_MA_LOAI_SP} = ?, " +
                "${DatabaseHelper.COLUMN_HINH_ANH} = ? " +
                "WHERE ${DatabaseHelper.COLUMN_MA_SP} = ?"
        db.execSQL(query, arrayOf(tenSP, giaBanSP, dvt, moTaSP, maLoaiSP, hinhAnh, maSP))

        Toast.makeText(requireContext(), "Sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show()
    }

    private fun deleteSanPham() {
        val maSP = etMaSP.text.toString()

        val db = databaseHelper.writableDatabase
        val query = "DELETE FROM ${DatabaseHelper.TABLE_SANPHAM} WHERE ${DatabaseHelper.COLUMN_MA_SP} = ?"
        db.execSQL(query, arrayOf(maSP))

        Toast.makeText(requireContext(), "Sản phẩm đã được xoá", Toast.LENGTH_SHORT).show()

        // Navigate back to previous fragment or activity
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                imageUri = uri
                imageViewProduct.setImageURI(uri)
            }
        }
    }
}
