package com.example.weatherwidget.view.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherwidget.databinding.ForecastItemBinding
import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.Constant.ABSOLUTE_ZERO
import com.example.weatherwidget.model.remote.Constant.IMG_URL
import com.example.weatherwidget.model.remote.data_forecast.Forecast
import com.example.weatherwidget.view.util.TimeUtil
import kotlin.time.Duration.Companion.days

class ForecastAdapter(private var listOfForecast: List<Forecast>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    private lateinit var binding: ForecastItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastAdapter.ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ForecastItemBinding.inflate(layoutInflater, parent, false)

        return ForecastViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ForecastViewHolder, position: Int) {
        holder.bind(listOfForecast[position])
    }

    override fun getItemCount() = listOfForecast.size

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(forecast: Forecast) {
            forecast.apply {
                binding.txtDay.text = TimeUtil.dayStringFormat(forecast.dt_txt)
                Glide.with(this@ForecastViewHolder.itemView.context)
                    .load("${IMG_URL}/${forecast.weather[0].icon}.png")
                    .into(binding.imgForecast)
                binding.txtForecastDegree.text =
                    Math.round(forecast.main.temp - ABSOLUTE_ZERO).toString() + "°"
            }
        }
    }
}