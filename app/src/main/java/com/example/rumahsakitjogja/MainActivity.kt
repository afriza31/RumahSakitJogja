package com.example.rumahsakitjogja

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.window.SplashScreen
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.rumahsakitjogja.fragments.CariRumahSakitFragment
import com.example.rumahsakitjogja.fragments.DaftarRumahSakitFragment
import com.example.rumahsakitjogja.fragments.FavoritFragment
import com.example.rumahsakitjogja.fragments.TentangFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.rumahsakitjogja.fragments.*

interface Refreshable { fun refreshOnResume() }
interface ScrollToTop { fun scrollToTop() }

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private var selectedItemId: Int = R.id.nav_cari

    private var fragCari: Fragment? = null
    private var fragDaftar: Fragment? = null
    private var fragFavorit: Fragment? = null
    private var fragTentang: Fragment? = null

    private var connMgr: ConnectivityManager? = null
    private var netCallback: ConnectivityManager.NetworkCallback? = null

    companion object {
        private const val STATE_SELECTED_ITEM = "state_selected_item"
        private const val TAG = "MainActivity"
        private const val TAG_CARI = "tag_cari"
        private const val TAG_DAFTAR = "tag_daftar"
        private const val TAG_FAVORIT = "tag_favorit"
        private const val TAG_TENTANG = "tag_tentang"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(savedInstanceState=$savedInstanceState)")
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_nav)
        selectedItemId = savedInstanceState?.getInt(STATE_SELECTED_ITEM) ?: R.id.nav_cari

        val fm = supportFragmentManager
        fragCari    = fm.findFragmentByTag(TAG_CARI)    ?: CariRumahSakitFragment()
        fragDaftar  = fm.findFragmentByTag(TAG_DAFTAR)  ?: DaftarRumahSakitFragment()
        fragFavorit = fm.findFragmentByTag(TAG_FAVORIT) ?: FavoritFragment()
        fragTentang = fm.findFragmentByTag(TAG_TENTANG) ?: TentangFragment()

        fm.beginTransaction().apply {
            if (!fragCari!!.isAdded)    add(R.id.nav_host_container, fragCari!!, TAG_CARI).hide(fragCari!!)
            if (!fragDaftar!!.isAdded)  add(R.id.nav_host_container, fragDaftar!!, TAG_DAFTAR).hide(fragDaftar!!)
            if (!fragFavorit!!.isAdded) add(R.id.nav_host_container, fragFavorit!!, TAG_FAVORIT).hide(fragFavorit!!)
            if (!fragTentang!!.isAdded) add(R.id.nav_host_container, fragTentang!!, TAG_TENTANG).hide(fragTentang!!)
        }.commitNow()
        Log.d(TAG, "Fragments prepared (added+hidden)")

        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId != selectedItemId) {
                Log.d(TAG, "onItemSelected -> ${resources.getResourceEntryName(item.itemId)}")
                showTab(item.itemId)
                selectedItemId = item.itemId
            } else {
                Log.d(TAG, "onItemSelected (same id) ignored")
            }
            true
        }

        bottomNav.setOnItemReselectedListener { item ->
            Log.d(TAG, "onItemReselected -> ${resources.getResourceEntryName(item.itemId)}")
            (getCurrentFragment(item.itemId) as? ScrollToTop)?.scrollToTop()
        }

        if (savedInstanceState == null) {
            Log.d(TAG, "First launch -> show Cari")
            showTab(R.id.nav_cari)
            bottomNav.selectedItemId = R.id.nav_cari
        } else {
            Log.d(TAG, "Restore -> show ${resources.getResourceEntryName(selectedItemId)}")
            showTab(selectedItemId)
            bottomNav.selectedItemId = selectedItemId
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (selectedItemId != R.id.nav_cari) {
                    Log.d(TAG, "Back pressed -> go to Cari tab")
                    bottomNav.selectedItemId = R.id.nav_cari
                    showTab(R.id.nav_cari)
                    selectedItemId = R.id.nav_cari
                } else {
                    Log.d(TAG, "Back pressed -> finish()")
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart()")
        connMgr = getSystemService()
        if (netCallback == null) {
            netCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) { Log.d(TAG, "Network available") }
                override fun onLost(network: Network) { Log.d(TAG, "Network lost") }
            }
        }
        runCatching { connMgr?.registerNetworkCallback(NetworkRequest.Builder().build(), netCallback!!) }
            .onFailure { Log.w(TAG, "registerNetworkCallback failed: ${it.message}") }
    }

    override fun onResume() { super.onResume(); Log.d(TAG, "onResume()")
        (getCurrentFragment(selectedItemId) as? Refreshable)?.refreshOnResume()
    }

    override fun onPause()  { Log.d(TAG, "onPause()");  super.onPause() }
    override fun onStop()   { Log.d(TAG, "onStop()");   super.onStop()
        runCatching { if (netCallback != null) connMgr?.unregisterNetworkCallback(netCallback!!) }
            .onFailure { Log.w(TAG, "unregisterNetworkCallback failed: ${it.message}") }
    }
    override fun onDestroy(){ Log.d(TAG, "onDestroy()"); super.onDestroy()
        netCallback = null; connMgr = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState(selectedItemId=$selectedItemId)")
        outState.putInt(STATE_SELECTED_ITEM, selectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun showTab(itemId: Int) {
        val toShow = getCurrentFragment(itemId) ?: return
        Log.d(TAG, "showTab -> ${toShow::class.java.simpleName}")
        supportFragmentManager.beginTransaction().apply {
            listOfNotNull(fragCari, fragDaftar, fragFavorit, fragTentang).forEach { hide(it!!) }
            show(toShow)
        }.commit()
    }

    private fun getCurrentFragment(itemId: Int): Fragment? = when (itemId) {
        R.id.nav_cari    -> fragCari
        R.id.nav_daftar  -> fragDaftar
        R.id.nav_favorit -> fragFavorit
        R.id.nav_tentang -> fragTentang
        else -> fragCari
    }
}
