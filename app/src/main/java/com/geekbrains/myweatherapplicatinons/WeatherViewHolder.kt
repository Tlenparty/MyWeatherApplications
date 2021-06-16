package com.geekbrains.myweatherapplicatinons

import android.view.View
import androidx.recyclerview.widget.RecyclerView

// Есть конструктор, принимающий View
// родительский класс имеет конструктор, принимающий View в качестве параметра,
// поэтому мы обязаны передать его при создании.
class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBindViewHolder(holder:WeatherViewHolder, position:Int){

    }


}