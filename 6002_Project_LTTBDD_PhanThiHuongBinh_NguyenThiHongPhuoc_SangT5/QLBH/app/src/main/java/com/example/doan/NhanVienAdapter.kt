package com.example.doan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import androidx.fragment.app.FragmentActivity

class NhanVienAdapter(private var nhanVienList: List<NhanVien>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder>() {

    class NhanVienViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val maNV: TextView = itemView.findViewById(R.id.tvMaNV)
        val hoTenNV: TextView = itemView.findViewById(R.id.tvHoTenNV)
        val maBP: TextView = itemView.findViewById(R.id.tvMaBP)
        val btnXemChiTiet: Button = itemView.findViewById(R.id.btnXemChiTiet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NhanVienViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nhanvien, parent, false)
        return NhanVienViewHolder(view)
    }

    override fun onBindViewHolder(holder: NhanVienViewHolder, position: Int) {
        val nhanVien = nhanVienList[position]
        holder.maNV.text = nhanVien.maNV
        holder.hoTenNV.text = nhanVien.hoTenNV
        holder.maBP.text = nhanVien.maBP

        holder.btnXemChiTiet.setOnClickListener {
            val fragment = CTNhanVienFragment()
            val bundle = Bundle()
            bundle.putString("maNV", nhanVien.maNV)
            bundle.putString("hoTenNV", nhanVien.hoTenNV)
            bundle.putString("gioiTinhNV", nhanVien.gioiTinhNV)
            bundle.putString("diaChiNV", nhanVien.diaChiNV)
            bundle.putString("ngaySinhNV", nhanVien.ngaySinhNV)
            bundle.putString("sdtNV", nhanVien.sdtNV)
            bundle.putString("maBP", nhanVien.maBP)
            bundle.putString("maCH", nhanVien.maCH)
            fragment.arguments = bundle

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return nhanVienList.size
    }
    fun updateData(newNhanVienList: List<NhanVien>) {
        nhanVienList = newNhanVienList
        notifyDataSetChanged()
    }
}

