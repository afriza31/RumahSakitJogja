package com.example.rumahsakitjogja.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rumahsakitjogja.R
import com.example.rumahsakitjogja.data.local.HospitalEntity
import com.google.android.material.button.MaterialButton

class RumahSakitAdapter(
    private val onClick: (HospitalEntity) -> Unit
) : ListAdapter<HospitalEntity, RumahSakitAdapter.VH>(DIFF) {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tv_name)
        val tvAddress: TextView = v.findViewById(R.id.tv_address)
        val tvIs24h: TextView = v.findViewById(R.id.tv_is24h)
        val tvServices: TextView = v.findViewById(R.id.tv_services)
        val btnDetail: MaterialButton = v.findViewById(R.id.btn_detail)
        val ivLogo: ImageView = v.findViewById(R.id.iv_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_hospital, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val rs = getItem(position)
        holder.ivLogo.setImageResource(rs.logoResId)
        holder.tvName.text = rs.nama
        holder.tvAddress.text = "${rs.alamat} • ${rs.kecamatan}"
        holder.tvIs24h.text = if (rs.is24Jam) "24 Jam" else "Jam tertentu"
        holder.tvServices.text = rs.layanan

        holder.itemView.setOnClickListener { onClick(rs) }
        holder.btnDetail.setOnClickListener { onClick(rs) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<HospitalEntity>() {
            override fun areItemsTheSame(oldItem: HospitalEntity, newItem: HospitalEntity) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: HospitalEntity, newItem: HospitalEntity) = oldItem == newItem
        }
    }
}