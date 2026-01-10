package com.example.rumahsakitjogja.data.local

import com.example.rumahsakitjogja.R

object SeedData {
    fun rumahSakitJogja(): List<HospitalEntity> = listOf(
        HospitalEntity(
            nama = "RSUP Dr. Sardjito",
            alamat = "Sleman, DIY",
            kecamatan = "Sleman",
            is24Jam = true,
            layanan = "UGD, Poli Anak, Poli Jantung, Farmasi",
            jamBuka = "24 Jam untuk UGD, Poli jam kerja",
            telepon = "0274-587333",
            lat = -7.767078677723353, lng = 110.37319526823036,
            logoResId = R.drawable.sardjito
        ),
        HospitalEntity(
            nama = "RS Bethesda Yogyakarta",
            alamat = "Kota Yogyakarta, DIY",
            kecamatan = "Kota Yogyakarta",
            is24Jam = true,
            layanan = "UGD, Poli Umum, Poli Penyakit Dalam",
            jamBuka = "24 Jam untuk UGD, Poli jam kerja",
            telepon = "0274-586688",
            lat = -7.783153361683199, lng = 110.37818659061413,
            logoResId = R.drawable.rs_bethesda
        ),
        HospitalEntity(
            nama = "RSUD Panembahan Senopati",
            alamat = "Bantul, DIY",
            kecamatan = "Bantul",
            is24Jam = true,
            layanan = "UGD, Rawat Inap, Poli Umum, Lab",
            jamBuka = "24 Jam untuk UGD, Poli jam kerja",
            telepon = "0274-367381",
            lat = -7.891613394309161, lng = 110.33796153051127,
            logoResId = R.drawable.rsud_panembahan
        ),
        HospitalEntity(
            nama = "RSUD Sleman",
            alamat = "Sleman, DIY",
            kecamatan = "Sleman",
            is24Jam = true,
            layanan = "UGD, Poli Umum, Poli Anak, Radiologi",
            jamBuka = "24 Jam untuk UGD, Poli jam kerja",
            telepon = "0274-868405",
            lat = -7.680823873504729, lng = 110.34227681489658,
            logoResId = R.drawable.rsud_sleman
        ),
        HospitalEntity(
            nama = "RSUD Wonosari",
            alamat = "Gunungkidul, DIY",
            kecamatan = "Gunungkidul",
            is24Jam = true,
            layanan = "UGD, Poli Umum, Rawat Inap",
            jamBuka = "24 Jam untuk UGD, Poli jam kerja",
            telepon = "0274-391012",
            lat = -7.956866050513851, lng = 110.6021941408069,
            logoResId = R.drawable.rsud_wonosari
        ),
        HospitalEntity(
            nama = "RSUD Wates",
            alamat = "Kulon Progo, DIY",
            kecamatan = "Kulon Progo",
            is24Jam = true,
            layanan = "UGD, Poli Umum, Poli Anak",
            jamBuka = "24 Jam untuk UGD, Poli jam kerja",
            telepon = "0274-773169",
            lat = -7.860764990172421 , lng = 110.14649151268728,
            logoResId = R.drawable.rsud_wates
        ),
        HospitalEntity(
            nama = "RSJ Grhasia",
            alamat = "Sleman, DIY",
            kecamatan = "Sleman",
            is24Jam = false,
            layanan = "Poli Jiwa, Rawat Inap Jiwa, Konsultasi Psikiatri",
            jamBuka = "Senin sampai Jumat jam kerja",
            telepon = "0274-439500",
            lat = -7.655969436435252, lng = 110.42269998609524,
            logoResId = R.drawable.grhasia
        ),
        HospitalEntity(
            nama = "RSKIA Sadewa",
            alamat = "Kota Yogyakarta, DIY",
            kecamatan = "Kota Yogyakarta",
            is24Jam = false,
            layanan = "Kebidanan, Anak, Imunisasi, USG",
            jamBuka = "Senin sampai Sabtu jam kerja",
            telepon = "0274-xxxxxx",
            lat = -7.767968167355577, lng = 110.41628782122089,
            logoResId = R.drawable.rskia_sadewa
        ),
        HospitalEntity(
            nama = "RSKIA Ummi Khasanah",
            alamat = "Sleman, DIY",
            kecamatan = "Sleman",
            is24Jam = false,
            layanan = "Kebidanan, Anak, IGD Kebidanan",
            jamBuka = "Senin sampai Sabtu jam kerja",
            telepon = "0274-xxxxxx",
            lat = -7.7590, lng = 110.3880,
            logoResId = R.drawable.rskia_ummi_khasanah
        )
    )
}