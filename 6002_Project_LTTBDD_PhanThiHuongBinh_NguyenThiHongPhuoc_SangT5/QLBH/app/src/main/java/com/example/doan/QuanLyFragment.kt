package com.example.doan

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class QuanLyFragment() : Fragment(), Parcelable {

    private lateinit var ivNhanVien: ImageView
    private lateinit var ivSanPham: ImageView
    private lateinit var ivHoaDon: ImageView
    private lateinit var ivKhachHang: ImageView
    private lateinit var ivThongKe: ImageView

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuanLyFragment> {
        override fun createFromParcel(parcel: Parcel): QuanLyFragment {
            return QuanLyFragment(parcel)
        }

        override fun newArray(size: Int): Array<QuanLyFragment?> {
            return arrayOfNulls(size)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.quanly_fragment, container, false)

        ivNhanVien = view.findViewById(R.id.ivNhanVien)
        ivSanPham = view.findViewById(R.id.ivSanPham)
        ivKhachHang = view.findViewById(R.id.ivKhachHang)
        ivHoaDon = view.findViewById(R.id.ivHoaDon)
        ivThongKe = view.findViewById(R.id.ivThongKe)

        ivNhanVien.setOnClickListener {
            navigatetoNhanVienFragment()
        }
            ivSanPham.setOnClickListener {
                navigatetoSanPhamFragment()
            }
                ivKhachHang.setOnClickListener {
                    navigatetoKhachHangFragment()
                }
                    ivHoaDon.setOnClickListener {
                        navigatetoHoaDonFragment()
                    }
                        ivThongKe.setOnClickListener {
                            navigatetoThongKeFragment()
                        }
                        return view
                    }

                    private fun navigatetoNhanVienFragment() {
                        val fragmentTransaction =
                            requireActivity().supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.fragment_container, NhanVienFragment())
                        fragmentTransaction.commit()
                    }

                    private fun navigatetoSanPhamFragment() {
                        val fragmentTransaction =
                            requireActivity().supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.fragment_container, SanPhamFragment())
                        fragmentTransaction.commit()
                    }
    private fun navigatetoKhachHangFragment() {
        val fragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, KhachHangFragment())
        fragmentTransaction.commit()
    }
    private fun navigatetoHoaDonFragment() {
        val fragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, HoaDonFragment())
        fragmentTransaction.commit()
    }
    private fun navigatetoThongKeFragment() {
        val fragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, ThongKeFragment())
        fragmentTransaction.commit()
    }
}