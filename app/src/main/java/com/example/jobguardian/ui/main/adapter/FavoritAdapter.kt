package com.example.jobguardian.ui.main.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobguardian.R
import com.example.jobguardian.data.db.entity.FavoriteEntity
import com.example.jobguardian.databinding.ListCompanyBinding
import com.example.jobguardian.ui.main.view.detail.DetailActivity

class FavoritAdapter(private val onItemClick: (FavoriteEntity) -> Unit) :
    ListAdapter<FavoriteEntity, FavoritAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favoritEntity = getItem(position)
        holder.bind(favoritEntity)
    }

    class MyViewHolder(val binding: ListCompanyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteEntity) {
            Glide.with(itemView.context)
                .load(data.companyProfile)
                .error(R.drawable.temp_iv)
                .into(binding.ivCompanyPhoto)
            binding.tvCompany.text = data.companyProfile
            binding.tvSalary.text = data.salaryRange
            binding.tvPosition.text = data.title
            binding.tvLocation.text = data.location
            binding.tvDescription.text = data.desc

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    this.putExtra("data", data.title)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        androidx.core.util.Pair(binding.tvCompany, "companyProfile"),
                        androidx.core.util.Pair(binding.tvSalary, "salaryRange"),
                        androidx.core.util.Pair(binding.tvPosition, "title"),
                        androidx.core.util.Pair(binding.tvLocation, "location"),
                        androidx.core.util.Pair(binding.tvDescription, "description")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}