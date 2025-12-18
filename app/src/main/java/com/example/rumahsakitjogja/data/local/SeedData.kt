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
            lat = -7.7689, lng = 110.3720,
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
            lat = -7.7829, lng = 110.3671,
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
            lat = -7.8878, lng = 110.3339,
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
            lat = -7.7169, lng = 110.3555,
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
            lat = -7.9675, lng = 110.6030,
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
            lat = -7.8587, lng = 110.1611,
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
            lat = -7.7477, lng = 110.4038,
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
            lat = -7.8015, lng = 110.3652,
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