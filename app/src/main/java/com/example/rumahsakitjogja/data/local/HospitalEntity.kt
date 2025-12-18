package com.example.rumahsakitjogja.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rumahsakitjogja.R

//version

@Entity(tableName = "hospitals")
data class HospitalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String,
    val alamat: String,
    val kecamatan: String,
    val is24Jam: Boolean,
    val layanan: String,
    val jamBuka: String? = null,
    val telepon: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val isFavorit: Boolean = false,
    val logoResId: Int = R.drawable.ic_launcher_foreground

)