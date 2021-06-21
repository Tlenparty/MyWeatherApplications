package com.geekbrains.myweatherapplicatinons.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class City(
    val city: String = "Moscow",
    val lat: Double = 12.12,
    val lon: Double = 12.13
):Parcelable