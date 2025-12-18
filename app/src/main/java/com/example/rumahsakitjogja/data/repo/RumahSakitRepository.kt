package com.example.rumahsakitjogja.data.repo

import com.example.rumahsakitjogja.data.local.HospitalEntity
import com.example.rumahsakitjogja.data.local.RumahSakitDao
import kotlinx.coroutines.flow.Flow

class RumahSakitRepository(private val dao: RumahSakitDao) {

    fun observeFiltered(q: String?, only24h: Boolean): Flow<List<HospitalEntity>> =
        dao.observeFiltered(q, if (only24h) 1 else 0)

    fun observeFavorit(): Flow<List<HospitalEntity>> = dao.observeFavorit()
    fun observeById(id: Long): Flow<HospitalEntity?> = dao.observeById(id)

    suspend fun toggleFavorit(id: Long) = dao.toggleFavorit(id)

    suspend fun seedIfEmpty(seed: List<HospitalEntity>) {
        if (dao.count() == 0) dao.insertAll(seed)
    }

    fun observeAll(): Flow<List<HospitalEntity>> = dao.observeAll()
    suspend fun reset(seed: List<HospitalEntity>) {
        dao.deleteAll()
        dao.insertAll(seed)
    }
}
