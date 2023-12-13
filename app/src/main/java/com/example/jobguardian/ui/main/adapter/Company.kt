package com.example.jobguardian.ui.main.adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val companyName: String,
    val salary: String,
    val position: String,
    val location: String,
    val description: String,
    val logo: Int
) : Parcelable