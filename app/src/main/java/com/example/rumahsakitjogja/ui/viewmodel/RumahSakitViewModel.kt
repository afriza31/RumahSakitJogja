package com.example.rumahsakitjogja.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahsakitjogja.data.local.AppDatabase
import com.example.rumahsakitjogja.data.local.HospitalEntity
import com.example.rumahsakitjogja.data.local.SeedData
import com.example.rumahsakitjogja.data.repo.RumahSakitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class RumahSakitViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getInstance(app).rumahSakitDao()
    private val repo = RumahSakitRepository(dao)

    private val query = MutableStateFlow<String?>(null)
    private val only24h = MutableStateFlow(false)

    val rumahSakitFlow = kotlinx.coroutines.flow.combine(query, only24h) { q, o24 ->
        q to o24
    }.flatMapLatest { (q, o24) ->
        repo.observeFiltered(q, o24)
    }
    fun observeById(id: Long): Flow<HospitalEntity?> = repo.observeById(id)

    val favoritFlow = repo.observeFavorit()

    fun setSearch(q: String?) { query.value = q }
    fun setOnly24h(enabled: Boolean) { only24h.value = enabled }

    fun toggleFavorit(id: Long) {
        viewModelScope.launch { repo.toggleFavorit(id) }
    }

    fun seedDataIfEmpty() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.seedIfEmpty(seed = SeedData.rumahSakitJogja())
        }
    }
    val allHospitalsFlow = repo.observeAll()
    fun forceReseed() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.reset(seed = demoHospitals())
        }
    }


    private fun demoHospitals(): List<HospitalEntity> = listOf(
        HospitalEntity(
            nama="RSUP Dr. Sardjito",
            alamat="Jl. Kesehatan No.1",
            kecamatan="Sleman",
            is24Jam=true,
            layanan="UGD, Poli Anak, Poli Jantung",
            jamBuka="24 Jam",
            telepon="0274-123456",
            lat=-7.7689, lng=110.3720
        ),
        HospitalEntity(
            nama="RS Bethesda",
            alamat="Jl. Jend. Sudirman No.70",
            kecamatan="Kota Yogyakarta",
            is24Jam=true,
            layanan="UGD, Poli Umum",
            jamBuka="24 Jam",
            telepon="0274-987654"
        ),
        HospitalEntity(
            nama="RSJ Grhasia",
            alamat="Jl. Kaliurang KM 17",
            kecamatan="Sleman",
            is24Jam=false,
            layanan="Poli Jiwa, IGD Psikiatri",
            jamBuka="Sen–Jum 08.00–15.00"
        ),
        HospitalEntity(
            nama="RSUD Kota Yogyakarta",
            alamat="Jl. Ki Ageng Pemanahan",
            kecamatan="Kota Yogyakarta",
            is24Jam=true,
            layanan="UGD, Poli Umum, Poli Anak",
            jamBuka="24 Jam"
        ),
        HospitalEntity(
            nama="RSKIA Sadewa",
            alamat="Jl. Babarsari",
            kecamatan="Depok",
            is24Jam=false,
            layanan="KIA, Kandungan, Poli Anak",
            jamBuka="Sen–Sab 08.00–20.00"
        )
    )
}