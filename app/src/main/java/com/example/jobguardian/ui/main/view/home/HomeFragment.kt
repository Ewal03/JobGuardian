package com.example.jobguardian.ui.main.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.jobguardian.R
import com.example.jobguardian.databinding.FragmentHomeBinding
import com.example.jobguardian.factory.ViewModelFactory
import com.example.jobguardian.ui.main.SharedViewModel
import com.example.jobguardian.ui.main.adapter.ListCompanyAdapter
import com.example.jobguardian.ui.main.adapter.LoadingStateAdapter

class HomeFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
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
        showLoading(true)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCompany.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvCompany.addItemDecoration(itemDecoration)
        homeViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        val adapter = ListCompanyAdapter(this)
        binding.rvCompany.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        sharedViewModel.userId.observe(viewLifecycleOwner) {
            homeViewModel.getUserProfile(it)
            homeViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
                homeViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
                    val user = userProfile?.userProfile?.fullName
                    if (user.equals(null)) {
                        binding.userHello.text = "Hello, user"
                    } else {
                        binding.userHello.text = "Hello, $user"
                    }

                    Glide.with(binding.root.context)
                        .load(userProfile?.userProfile?.profilePicture)
                        .placeholder(R.drawable.pp)
                        .error(R.drawable.pp)
                        .into(binding.imageView2)

                })

            })
        }

        homeViewModel.getData().observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading:Boolean){
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
    }
}
