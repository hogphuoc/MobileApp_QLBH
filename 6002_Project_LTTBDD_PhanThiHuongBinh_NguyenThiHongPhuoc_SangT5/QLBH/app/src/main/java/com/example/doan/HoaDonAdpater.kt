package com.example.doan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HoaDonAdapter(
    private var hoaDonList: List<HoaDon>,
    private val onItemClicked: (HoaDon) -> Unit
) : RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoaDonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hoadon, parent, false)
        return HoaDonViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoaDonViewHolder, position: Int) {
        val hoaDon = hoaDonList[position]
        holder.bind(hoaDon, onItemClicked)
    }

    fun updateData(newHoaDonList: List<HoaDon>) {
        hoaDonList = newHoaDonList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = hoaDonList.size

    class HoaDonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val maHDTextView: TextView = itemView.findViewById(R.id.maHDTextView)
        private val maKHTextView: TextView = itemView.findViewById(R.id.maKHTextView)
        private val maNVTextView: TextView = itemView.findViewById(R.id.maNVTextView)
        private val ngayXuatHDTextView: TextView = itemView.findViewById(R.id.ngayXuatHDTextView)
        private val ptTTTextView: TextView = itemView.findViewById(R.id.ptTTTextView)
        private val xemChiTietButton: Button = itemView.findViewById(R.id.xemChiTietButton)

        fun bind(hoaDon: HoaDon, onItemClicked: (HoaDon) -> Unit) {
            maHDTextView.text = hoaDon.maHD
            maKHTextView.text = hoaDon.maKH
            maNVTextView.text = hoaDon.maNV
            ngayXuatHDTextView.text = hoaDon.ngayXuatHD
            ptTTTextView.text = hoaDon.ptTT
            xemChiTietButton.setOnClickListener {
                onItemClicked(hoaDon)
            }
        }
    }
}