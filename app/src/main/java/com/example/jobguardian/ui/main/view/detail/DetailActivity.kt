package com.example.jobguardian.ui.main.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.jobguardian.R
import com.example.jobguardian.data.db.entity.FavoriteEntity
import com.example.jobguardian.data.response.DataItem
import com.example.jobguardian.databinding.ActivityDetailBinding
import com.example.jobguardian.factory.FavoritViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        FavoritViewModelFactory.getInstance(application)
    }
    private lateinit var favoritEntity: FavoriteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataId = intent.getParcelableExtra<DataItem>("data")
        val dataId2 = intent.getParcelableExtra<FavoriteEntity>("data2")

        if (dataId != null) {
            binding.ivCompanyPhoto.apply {
                Glide.with(context)
                    .load(dataId.companyLogo)
                    .error(R.drawable.temp_iv)
                    .into(this)
            }

            binding.tvCompany.text = dataId.companyProfile
            binding.tvSalary.text = dataId.salaryRange
            binding.tvPosition.text = dataId.title
            binding.tvLocation.text = dataId.location
            binding.tvDescription.text = dataId.description


        }else if (dataId2 != null){
            favoritEntity = FavoriteEntity(
                title = dataId2.title.orEmpty(),
                companyProfile = dataId2.companyProfile,
                salaryRange = dataId2.salaryRange,
                location = dataId2.location,
                desc = dataId2.desc,
                companyLogo = dataId2.companyLogo
            )
            binding.ivCompanyPhoto.apply {
                Glide.with(context)
                    .load(dataId2.companyLogo)
                    .error(R.drawable.temp_iv)
                    .into(this)
            }

            binding.tvCompany.text = dataId2.companyProfile
            binding.tvSalary.text = dataId2.salaryRange
            binding.tvPosition.text = dataId2.title
            binding.tvLocation.text = dataId2.location
            binding.tvDescription.text = dataId2.desc
             }

        supportActionBar?.hide()

        binding.fabFav.apply {
            setOnClickListener {
                val dataId = intent.getParcelableExtra<DataItem>("data")

                dataId?.let {
                    favoritEntity = FavoriteEntity(
                        title = it.title.orEmpty(),
                        companyProfile = it.companyProfile,
                        salaryRange = it.salaryRange,
                        location = it.location,
                        desc = it.description,
                        companyLogo = it.companyLogo
                    )

                    when (tag) {
                        null -> {
                            // Insert ke favorit
                            detailViewModel.insertFavorit(favoritEntity)
                            tag = "favorit"
                            setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@DetailActivity,
                                    R.drawable.ic_favorited
                                )
                            )
                        }

                        else -> {
                            // Hapus dari favorit
                            detailViewModel.deleteFavorit(favoritEntity)
                            tag = null
                            setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@DetailActivity,
                                    R.drawable.ic_favorit
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}