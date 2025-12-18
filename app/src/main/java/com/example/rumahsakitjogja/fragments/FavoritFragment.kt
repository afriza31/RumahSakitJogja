package com.example.rumahsakitjogja.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rumahsakitjogja.R
import com.example.rumahsakitjogja.Refreshable
import com.example.rumahsakitjogja.adapters.RumahSakitAdapter
import com.example.rumahsakitjogja.ui.viewmodel.RumahSakitViewModel
import kotlinx.coroutines.launch

class FavoritFragment : Fragment(R.layout.fragment_favorit), Refreshable {

    private val vm: RumahSakitViewModel by activityViewModels()
    private lateinit var adapter: RumahSakitAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv = view.findViewById<RecyclerView>(R.id.rv_favorites)
        val tvEmpty = view.findViewById<TextView>(R.id.tv_empty)

        adapter = RumahSakitAdapter { rs ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, DetailRumahSakitFragment.newInstance(rs.id))
                .addToBackStack(null)
                .commit()
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.favoritFlow.collect { list ->
                    adapter.submitList(list)
                    tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    rv.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }
    }

    override fun refreshOnResume() {
        // flow sudah auto-update, ini opsional (boleh dikosongkan)
    }
}