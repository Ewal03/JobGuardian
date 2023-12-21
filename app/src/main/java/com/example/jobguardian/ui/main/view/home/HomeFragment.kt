package com.example.jobguardian.ui.main.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobguardian.databinding.FragmentHomeBinding
import com.example.jobguardian.factory.ViewModelFactory
import com.example.jobguardian.ui.main.adapter.ListCompanyAdapter
import com.example.jobguardian.ui.main.adapter.LoadingStateAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCompany.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvCompany.addItemDecoration(itemDecoration)

        homeViewModel.isLoading.observe(requireActivity()) {
            loading(it)}

        val adapter = ListCompanyAdapter(this)
        binding.rvCompany.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        homeViewModel.getData().observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        return root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
        private fun loading(isLoading: Boolean) {
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
}