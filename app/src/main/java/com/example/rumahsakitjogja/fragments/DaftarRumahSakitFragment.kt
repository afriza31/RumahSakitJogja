package com.example.rumahsakitjogja.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rumahsakitjogja.R
import com.example.rumahsakitjogja.ScrollToTop
import com.example.rumahsakitjogja.adapters.RumahSakitAdapter
import com.example.rumahsakitjogja.ui.viewmodel.RumahSakitViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class DaftarRumahSakitFragment : Fragment(R.layout.fragment_daftar_rumahsakit), ScrollToTop {

    private val vm: RumahSakitViewModel by activityViewModels()
    private lateinit var adapter: RumahSakitAdapter
    private var rv: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv = view.findViewById(R.id.rv_hospitals)

        adapter = RumahSakitAdapter { rs ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, DetailRumahSakitFragment.newInstance(rs.id))
                .addToBackStack(null)
                .commit()
        }

        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.adapter = adapter

        // seed data 1x (aman dipanggil berkali-kali, karena hanya insert jika kosong)
        vm.seedDataIfEmpty()

        // search
        val et = view.findViewById<TextInputEditText>(R.id.et_search)
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.setSearch(s?.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.rumahSakitFlow.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
        val sw = view.findViewById<com.google.android.material.materialswitch.MaterialSwitch>(R.id.sw_24h)
        sw.setOnCheckedChangeListener { _, isChecked ->
            vm.setOnly24h(isChecked)
        }
    }

    override fun scrollToTop() {
        rv?.smoothScrollToPosition(0)
    }
}