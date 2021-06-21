package com.geekbrains.myweatherapplicatinons.framework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.myweatherapplicatinons.databinding.FragmentMainRecylcerItemBinding
import com.geekbrains.myweatherapplicatinons.model.Weather
import com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment.MainFragment

class MainFragmentAdapter(private var itemClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData: List<Weather> = listOf()
    lateinit var binding: FragmentMainRecylcerItemBinding

    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = FragmentMainRecylcerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) = with(binding) {
            /*  itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.city
              itemView.setOnClickListener {
                  Toast.makeText(itemView.context,weather.city.city, Toast.LENGTH_SHORT).show()
              }*/
            mainFragmentRecyclerItemTextView.text = weather.city.city
            root.setOnClickListener {
                itemClickListener?.onItemViewClick(weather)
            }
        }
    }

}