package com.example.rumahsakitjogja.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rumahsakitjogja.R
import com.example.rumahsakitjogja.ui.viewmodel.RumahSakitViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class DetailRumahSakitFragment : Fragment(R.layout.fragment_detail_rumahsakit) {

    private val vm: RumahSakitViewModel by activityViewModels()
    private var hospitalId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hospitalId = arguments?.getLong(ARG_ID, -1) ?: -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ivLogo      = view.findViewById<ImageView>(R.id.iv_logo)
        val tvName      = view.findViewById<TextView>(R.id.tv_hospital_name)
        val tvAddress   = view.findViewById<TextView>(R.id.tv_full_address)
        val tvKecOpen   = view.findViewById<TextView>(R.id.tv_district_open)
        val tvServices  = view.findViewById<TextView>(R.id.tv_services_all)
        val tvOpenHours = view.findViewById<TextView>(R.id.tv_open_hours)
        val tvPhone     = view.findViewById<TextView>(R.id.tv_phone)

        val btnBack     = view.findViewById<MaterialButton>(R.id.btn_back)
        val btnCall     = view.findViewById<MaterialButton>(R.id.btn_call)
        val btnMap      = view.findViewById<MaterialButton>(R.id.btn_map)
        val btnFavorite = view.findViewById<MaterialButton>(R.id.btn_favorite)

        btnBack.setOnClickListener { parentFragmentManager.popBackStack() }

        if (hospitalId <= 0) {
            parentFragmentManager.popBackStack()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.observeById(hospitalId).collect { rs ->
                    if (rs == null) return@collect

                    ivLogo.setImageResource(rs.logoResId)
                    tvName.text = rs.nama
                    tvAddress.text = rs.alamat
                    tvKecOpen.text = "Kecamatan: ${rs.kecamatan} • ${if (rs.is24Jam) "24 Jam" else "Jam tertentu"}"
                    tvServices.text = rs.layanan
                    tvOpenHours.text = rs.jamBuka ?: "-"
                    tvPhone.text = rs.telepon ?: "-"

                    btnFavorite.text = if (rs.isFavorit) "⭐ Di-favoritkan" else "⭐ Favorit"

                    btnCall.setOnClickListener {
                        rs.telepon?.let {
                            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it")))
                        }
                    }

                    btnMap.setOnClickListener {
                        val uri = if (rs.lat != null && rs.lng != null)
                            Uri.parse("geo:${rs.lat},${rs.lng}?q=${rs.lat},${rs.lng}(${Uri.encode(rs.nama)})")
                        else
                            Uri.parse("geo:0,0?q=${Uri.encode("${rs.nama} ${rs.alamat}")}")

                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                    }

                    btnFavorite.setOnClickListener {
                        vm.toggleFavorit(hospitalId)
                    }
                }
            }
        }
    }

    companion object {
        private const val ARG_ID = "id"
        fun newInstance(id: Long) = DetailRumahSakitFragment().apply {
            arguments = Bundle().apply { putLong(ARG_ID, id) }
        }
    }
}