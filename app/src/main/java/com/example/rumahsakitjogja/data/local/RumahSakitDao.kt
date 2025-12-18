package com.example.rumahsakitjogja.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RumahSakitDao {

    @Query("""
        SELECT * FROM hospitals
        WHERE (:only24h = 0 OR is24Jam = 1)
          AND (
               :q IS NULL OR :q = ''
               OR nama LIKE '%' || :q || '%'
               OR alamat LIKE '%' || :q || '%'
               OR layanan LIKE '%' || :q || '%'
          )
        ORDER BY nama ASC
    """)
    fun observeFiltered(q: String?, only24h: Int): Flow<List<HospitalEntity>>

    @Query("SELECT * FROM hospitals WHERE isFavorit = 1 ORDER BY nama ASC")
    fun observeFavorit(): Flow<List<HospitalEntity>>

    @Query("SELECT * FROM hospitals WHERE id = :id LIMIT 1")
    fun observeById(id: Long): Flow<HospitalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<HospitalEntity>)

    @Query("SELECT COUNT(*) FROM hospitals")
    suspend fun count(): Int

    @Query("""
        UPDATE hospitals
        SET isFavorit = CASE WHEN isFavorit = 1 THEN 0 ELSE 1 END
        WHERE id = :id
    """)
    suspend fun toggleFavorit(id: Long)

    @Query("SELECT * FROM hospitals")
    fun observeAll(): Flow<List<HospitalEntity>>

    @Query("DELETE FROM hospitals")
    suspend fun deleteAll()
}