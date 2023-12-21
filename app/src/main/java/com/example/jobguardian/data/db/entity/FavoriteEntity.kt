package com.example.jobguardian.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    val title: String = "",
    val companyProfile: String? = null,
    val salaryRange: String? = null,
    val location: String? = null,
    val desc: String? = null,
    val companyLogo: String? = null

    ) : Parcelable