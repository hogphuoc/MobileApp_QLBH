package com.example.doan

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SanPhamAdapter(private var sanPhamList: List<SanPham>, private val onItemClicked: (SanPham) -> Unit) : RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SanPhamViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sanpham, parent, false)
        return SanPhamViewHolder(itemView, onItemClicked)
    }

    override fun onBindViewHolder(holder: SanPhamViewHolder, position: Int) {
        val currentSanPham = sanPhamList[position]
        holder.bind(currentSanPham)
    }

    override fun getItemCount() = sanPhamList.size

    fun updateData(newSanPhamList: List<SanPham>) {
        sanPhamList = newSanPhamList
        notifyDataSetChanged()
    }

    class SanPhamViewHolder(itemView: View, private val onItemClicked: (SanPham) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageViewHinhAnh: ImageView = itemView.findViewById(R.id.imageViewHinhAnh)
        private val textViewMaSP: TextView = itemView.findViewById(R.id.textViewMaSP)
        private val textViewTenSP: TextView = itemView.findViewById(R.id.textViewTenSP)
        private val textViewGiaBanSP: TextView = itemView.findViewById(R.id.textViewGiaBanSP)
        private val buttonXemChiTiet: Button = itemView.findViewById(R.id.buttonXemChiTiet)

        fun bind(sanPham: SanPham) {
            textViewMaSP.text = sanPham.maSP
            textViewTenSP.text = sanPham.tenSP
            textViewGiaBanSP.text = sanPham.giaban.toString()

            // Sử dụng Glide để tải hình ảnh từ URI
            val imageUri = Uri.parse(sanPham.hinhanh)
            Glide.with(itemView.context)
                .load(imageUri)
                .apply(RequestOptions()
                    .placeholder(R.drawable.placeholder) // Hình ảnh placeholder
                    .error(R.drawable.error)) // Hình ảnh error
                .into(imageViewHinhAnh)

            buttonXemChiTiet.setOnClickListener {
                onItemClicked(sanPham)
            }
        }
    }
}
