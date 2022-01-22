package com.geekbrains.myweatherapplicatinons.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class City(
    var city: String,
    val lat: Double,
    val lon: Double
):Parcelable