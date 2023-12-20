package com.example.jobguardian.ui.main.view.favorit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobguardian.databinding.FragmentFavoritBinding
import com.example.jobguardian.factory.FavoritViewModelFactory
import com.example.jobguardian.ui.main.adapter.FavoritAdapter
import com.example.jobguardian.ui.main.view.detail.DetailActivity

class FavoritFragment : Fragment() {

    private var _binding: FragmentFavoritBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritAdapter: FavoritAdapter
    private lateinit var favoritViewModel: FavoritViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val favoritViewModelFactory = FavoritViewModelFactory(requireActivity().application)
        favoritViewModel =
            ViewModelProvider(this, favoritViewModelFactory)[FavoritViewModel::class.java]

        val recyclerView = binding.rvFavUser
        favoritAdapter = FavoritAdapter { favorit ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("data", favorit.title)
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoritAdapter
        }

        favoritViewModel.favoritEntity.observe(viewLifecycleOwner) { favorit ->
            favoritAdapter.submitList(favorit)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
