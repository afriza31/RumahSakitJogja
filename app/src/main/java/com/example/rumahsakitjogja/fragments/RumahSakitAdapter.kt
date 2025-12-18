package com.example.rumahsakitjogja.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rumahsakitjogja.R
import com.example.rumahsakitjogja.models.RumahSakit
import com.google.android.material.button.MaterialButton

class RumahSakitAdapter(
    private val onClick: (RumahSakit) -> Unit
) : ListAdapter<RumahSakit, RumahSakitAdapter.ViewHolder>(DIFF) {

    init { setHasStableIds(true) }

    override fun getItemId(position: Int): Long {
        // pakai kombinasi nama+alamat sbg key stabil; sesuaikan jika punya id unik
        val item = getItem(position)
        return ("${item.nama}|${item.alamat}").hashCode().toLong()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvAddress: TextView = view.findViewById(R.id.tv_address)
        val tvIs24h: TextView = view.findViewById(R.id.tv_is24h)
        val tvServices: TextView = view.findViewById(R.id.tv_services)
        val btnDetail: MaterialButton = view.findViewById(R.id.btn_detail)
        fun bind(rs: RumahSakit) {
            tvName.text = rs.nama
            tvAddress.text = "${rs.alamat} • ${rs.kecamatan}"
            tvIs24h.text = if (rs.is24Jam) "24 Jam" else "Jam tertentu"
            tvServices.text = rs.layanan
            itemView.setOnClickListener { onClick(rs) }
            btnDetail.setOnClickListener { onClick(rs) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hospital, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<RumahSakit>() {
            override fun areItemsTheSame(oldItem: RumahSakit, newItem: RumahSakit): Boolean {
                // definisikan identitas unik item (idealnya pakai id); fallback nama+alamat
                return oldItem.nama == newItem.nama && oldItem.alamat == newItem.alamat
            }
            override fun areContentsTheSame(oldItem: RumahSakit, newItem: RumahSakit): Boolean {
                return oldItem == newItem
            }
        }
    }
}
