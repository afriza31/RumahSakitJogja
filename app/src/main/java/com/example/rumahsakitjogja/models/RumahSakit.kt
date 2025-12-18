package com.example.rumahsakitjogja.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RumahSakit(
    val nama: String,
    val alamat: String,
    val kecamatan: String,
    val is24Jam: Boolean,
    val layanan: String,
    val jamBuka: String? = null,
    val telepon: String? = null,
    val lat: Double? = null,
    val lng: Double? = null
) : Parcelable
