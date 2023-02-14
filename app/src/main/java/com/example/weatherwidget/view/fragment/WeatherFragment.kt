package com.example.weatherwidget.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.weatherwidget.R
import com.example.weatherwidget.databinding.FragmentWeatherBinding
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_weather.Weather
import com.example.weatherwidget.model.remote.data_weather.WeatherResponse
import com.example.weatherwidget.presenter.mvp_weather.MVPWeather
import com.example.weatherwidget.presenter.mvp_weather.WeatherPresenter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherFragment : Fragment(), MVPWeather.WeatherView {
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var presenter: MVPWeather.WeatherPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWeatherBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = WeatherPresenter(VolleyHandler(this.context),this)
        presenter.getWeatherData()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun setResult(weatherResponse: WeatherResponse) {
        val currentDate = getCurrentDate()

        binding.apply{
            weatherResponse.apply{
                city.text = "Houston"
                date.text = currentDate.toString()
                temperature.text = main.temp.toString()
                weatherDescription.text = weather[0].main
                minTemperature.text = main.temp_min.toString()
                maxTemperature.text = main.temp_max.toString()
            }
        }
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            Toast.makeText(this.context,"loading", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this.context,message, Toast.LENGTH_SHORT).show()
    }

    fun getCurrentDate(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE,MMMM dd, yyyy")
        val formatted = current.format(formatter)

        return formatted
    }
}