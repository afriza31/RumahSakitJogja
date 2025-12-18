package com.example.rumahsakitjogja.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rumahsakitjogja.R

class TentangFragment : Fragment(R.layout.fragment_tentang) {

    private var tvAppName: TextView? = null
    private var tvVersion: TextView? = null
    private var tvDesc: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAppName = view.findViewById(R.id.tv_app_name)
        tvVersion = view.findViewById(R.id.tv_version)
        tvDesc    = view.findViewById(R.id.tv_desc)

        tvAppName?.text = getString(R.string.app_name)

        // Ambil versi dari PackageManager
        val pm = requireContext().packageManager
        val pkg = requireContext().packageName
        val info = pm.getPackageInfo(pkg, 0)
        val versionName = info.versionName ?: "-"
        val versionCode = info.longVersionCode  // setara BuildConfig.VERSION_CODE (long)
        tvVersion?.text = "Versi $versionName ($versionCode)"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvAppName = null
        tvVersion = null
        tvDesc = null
    }
}
