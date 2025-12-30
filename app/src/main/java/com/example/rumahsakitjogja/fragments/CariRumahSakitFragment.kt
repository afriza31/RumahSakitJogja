package com.example.rumahsakitjogja.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rumahsakitjogja.R
import com.example.rumahsakitjogja.data.local.HospitalEntity
import com.example.rumahsakitjogja.ui.viewmodel.RumahSakitViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class CariRumahSakitFragment : Fragment(R.layout.fragment_cari_rumahsakit), OnMapReadyCallback {

    private val vm: RumahSakitViewModel by activityViewModels()

    private var map: GoogleMap? = null
    private val markersById = mutableMapOf<Long, Marker>()
    private var hasMovedCamera = false

    private var lastList: List<HospitalEntity> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pastikan seed sudah jalan (insert jika DB kosong)
        vm.seedDataIfEmpty()

        // Map init
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        // Tombol refresh: render ulang + recenter (tanpa insert ulang data)
//        view.findViewById<MaterialButton>(R.id.btn_refresh).setOnClickListener {
//            vm.forceReseed()
//            hasMovedCamera = false
//        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.uiSettings?.isZoomControlsEnabled = true
        // permission
        if (androidx.core.content.ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation()
        } else {
            reqLocPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }


        // Klik marker: tampilkan info window
        map?.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }

//        opsi tampilan map (HYBRID, NORMAL, SATELLITE, TERRAIN)
        map?.setMapType(GoogleMap.MAP_TYPE_NORMAL)

        // Klik info window: buka detail RS
        map?.setOnInfoWindowClickListener { marker ->
            val id = marker.tag as? Long ?: return@setOnInfoWindowClickListener
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, DetailRumahSakitFragment.newInstance(id))
                .addToBackStack(null)
                .commit()
        }

        // Ambil semua data RS (bukan yang terfilter query)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.allHospitalsFlow.collect { list ->
                    val withLocation = list.filter { it.lat != null && it.lng != null }
                    lastList = withLocation
                    renderMarkers(withLocation)
                }
            }
        }
    }

    private fun renderMarkers(list: List<HospitalEntity>) {
        val gMap = map ?: return

        // Hapus marker lama
        markersById.values.forEach { it.remove() }
        markersById.clear()

        if (list.isEmpty()) {
            val jogja = LatLng(-7.797068, 110.370529)
            if (!hasMovedCamera) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jogja, 11f))
                hasMovedCamera = true
            }
            return
        }

        val boundsBuilder = LatLngBounds.Builder()

        list.forEach { rs ->
            val pos = LatLng(rs.lat!!, rs.lng!!)
            boundsBuilder.include(pos)

            val snippet = buildString {
                append(rs.alamat)
                if (rs.is24Jam) append(" • 24 Jam")
            }

            val marker = gMap.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title(rs.nama)
                    .snippet(snippet)
            )

            marker?.let {
                it.tag = rs.id
                markersById[rs.id] = it
            }
        }

        if (!hasMovedCamera) {
            val bounds = boundsBuilder.build()
            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))
            hasMovedCamera = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map = null
        markersById.clear()
        hasMovedCamera = false
        lastList = emptyList()
    }
    private fun enableMyLocation() {
        try {
            map?.isMyLocationEnabled = true
            map?.uiSettings?.isMyLocationButtonEnabled = true
        } catch (_: SecurityException) {}
    }
    private val reqLocPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) enableMyLocation()
    }
}