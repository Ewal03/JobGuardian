package com.example.jobguardian.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobguardian.R
import com.example.jobguardian.data.response.DataItem
import com.example.jobguardian.databinding.ListCompanyBinding
import com.example.jobguardian.ui.main.view.detail.DetailActivity

class ListCompanyAdapter(val fragment: Fragment) :
    PagingDataAdapter<DataItem, ListCompanyAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, fragment)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(val binding: ListCompanyBinding, val fragment: Fragment) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            Glide.with(fragment)
                .load(data.companyLogo)
                .error(R.drawable.temp_iv)
                .into(binding.ivCompanyPhoto)
            binding.tvCompany.text = data.companyProfile
            binding.tvSalary.text = data.salaryRange
            binding.tvPosition.text = data.title
            binding.tvLocation.text = data.location
            binding.tvDescription.text = data.description

            itemView.setOnClickListener {
                val intent = Intent(fragment.requireContext(), DetailActivity::class.java).apply {
                    this.putExtra("data", data)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        fragment.requireActivity(),
                        androidx.core.util.Pair(binding.ivCompanyPhoto, "companyLogo"),
                        androidx.core.util.Pair(binding.tvCompany, "companyProfile"),
                        androidx.core.util.Pair(binding.tvSalary, "salaryRange"),
                        androidx.core.util.Pair(binding.tvPosition, "title"),
                        androidx.core.util.Pair(binding.tvLocation, "location"),
                        androidx.core.util.Pair(binding.tvDescription, "description")
                    )
                fragment.requireContext().startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
