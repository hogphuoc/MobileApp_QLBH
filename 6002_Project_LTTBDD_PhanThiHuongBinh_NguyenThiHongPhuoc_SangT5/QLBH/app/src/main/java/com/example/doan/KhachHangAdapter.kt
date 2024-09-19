package com.example.doan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import android.media.Image

class KhachHangAdapter(private var khachHangList: List<KhachHang>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder>() {

    class KhachHangViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMaKH: TextView = view.findViewById(R.id.tvMaKH)
        val tvHoTenKH: TextView = view.findViewById(R.id.tvHoTenKH)
        //val tvGioiTinhKH: TextView = view.findViewById(R.id.tvGioiTinhKH)
        //val tvDiaChiKH: TextView = view.findViewById(R.id.tvDiaChiKH)
        //val tvNgaySinhKH: TextView = view.findViewById(R.id.tvNgaySinhKH)
        val tvSdtKH: TextView = view.findViewById(R.id.tvSdtKH)
        val btnViewDetails: Button = view.findViewById(R.id.btnViewDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhachHangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_khachhang, parent, false)
        return KhachHangViewHolder(view)
    }

    override fun onBindViewHolder(holder: KhachHangViewHolder, position: Int) {
        val khachHang = khachHangList[position]
        holder.tvMaKH.text = khachHang.maKH
        holder.tvHoTenKH.text = khachHang.hoTenKH
        //holder.tvGioiTinhKH.text = khachHang.gioiTinhKH
        //holder.tvDiaChiKH.text = khachHang.diaChiKH
        //holder.tvNgaySinhKH.text = khachHang.ngaySinhKH
        holder.tvSdtKH.text = khachHang.sdtKH

        holder.btnViewDetails.setOnClickListener {
            val fragment = CTKhachHangFragment()
            val bundle = Bundle()
            bundle.putString("maKH", khachHang.maKH)
            bundle.putString("hoTenKH", khachHang.hoTenKH)
            bundle.putString("gioiTinhKH", khachHang.gioiTinhKH)
            bundle.putString("diaChiKH", khachHang.diaChiKH)
            bundle.putString("ngaySinhKH", khachHang.ngaySinhKH)
            bundle.putString("sdtKH", khachHang.sdtKH)
            fragment.arguments = bundle

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return khachHangList.size
    }
    fun updateData(newKhachHangList: List<KhachHang>) {
        khachHangList = newKhachHangList
        notifyDataSetChanged()
    }
}
