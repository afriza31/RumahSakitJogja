package com.example.rumahsakitjogja.data

import android.content.Context
import com.example.rumahsakitjogja.models.RumahSakit
import org.json.JSONArray
import org.json.JSONObject

object FavoritesStore {
    private const val PREF_NAME = "favorites_pref"
    private const val KEY_JSON = "favorites_json"

    private fun prefs(ctx: Context) =
        ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getAll(ctx: Context): List<RumahSakit> {
        val json = prefs(ctx).getString(KEY_JSON, "[]") ?: "[]"
        val arr = JSONArray(json)
        val out = mutableListOf<RumahSakit>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            out.add(jsonToRs(o))
        }
        return out
    }

    fun isFavorite(ctx: Context, nama: String, alamat: String): Boolean {
        return getAll(ctx).any { it.nama == nama && it.alamat == alamat }
    }

    fun toggle(ctx: Context, rs: RumahSakit): Boolean {
        // return true jika setelah toggle = tersimpan (favorit), false jika dihapus
        val list = getAll(ctx).toMutableList()
        val idx = list.indexOfFirst { it.nama == rs.nama && it.alamat == rs.alamat }
        if (idx >= 0) {
            list.removeAt(idx)
            saveAll(ctx, list)
            return false
        } else {
            list.add(rs)
            saveAll(ctx, list)
            return true
        }
    }

    private fun saveAll(ctx: Context, list: List<RumahSakit>) {
        val arr = JSONArray()
        list.forEach { arr.put(rsToJson(it)) }
        prefs(ctx).edit().putString(KEY_JSON, arr.toString()).apply()
    }

    private fun rsToJson(rs: RumahSakit): JSONObject = JSONObject().apply {
        put("nama", rs.nama)
        put("alamat", rs.alamat)
        put("kecamatan", rs.kecamatan)
        put("is24Jam", rs.is24Jam)
        put("layanan", rs.layanan)
        put("jamBuka", rs.jamBuka)
        put("telepon", rs.telepon)
        put("lat", rs.lat)
        put("lng", rs.lng)
    }

    private fun jsonToRs(o: JSONObject): RumahSakit = RumahSakit(
        nama = o.optString("nama"),
        alamat = o.optString("alamat"),
        kecamatan = o.optString("kecamatan"),
        is24Jam = o.optBoolean("is24Jam"),
        layanan = o.optString("layanan"),
        jamBuka = o.optString("jamBuka").ifEmpty { null },
        telepon = o.optString("telepon").ifEmpty { null },
        lat = if (o.isNull("lat")) null else o.optDouble("lat"),
        lng = if (o.isNull("lng")) null else o.optDouble("lng"),
    )
}
