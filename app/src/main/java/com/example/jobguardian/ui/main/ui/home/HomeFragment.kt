package com.example.jobguardian.ui.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobguardian.R
import com.example.jobguardian.databinding.FragmentHomeBinding
import com.example.jobguardian.ui.main.adapter.Company
import com.example.jobguardian.ui.main.adapter.ListCompanyAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recycler: RecyclerView
    private val list = ArrayList<Company>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recycler = binding.rvCompany
        recycler.setHasFixedSize(true)

        list.addAll(getList())
        showRecyclerList()

        return root
    }

    private fun getList(): ArrayList<Company> {
        val dataName = resources.getStringArray(R.array.company_name)
        val dataDescription = resources.getStringArray(R.array.description)
        val dataLogo = resources.obtainTypedArray(R.array.logo)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataSalary =resources.getStringArray(R.array.salary)
        val dataPosition = resources.getStringArray(R.array.position)
        val listCompany = ArrayList<Company>()
        for (i in dataName.indices) {
            val company = Company(dataName[i],dataSalary[i],dataPosition[i], dataLocation[i], dataDescription[i], dataLogo.getResourceId(i, -1))
            listCompany.add(company)
        }
        return listCompany
    }

    private fun showRecyclerList() {
        recycler.layoutManager = LinearLayoutManager(requireContext())
        val listClubAdapter = ListCompanyAdapter(list)
        recycler.adapter = listClubAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}