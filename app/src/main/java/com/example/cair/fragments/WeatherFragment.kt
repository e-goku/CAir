package com.example.cair.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.cair.MySingleton
import com.example.cair.ResultsActivity
import com.example.cair.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val view = binding.root

        val (location, isChecked) = (activity as ResultsActivity).getMyData()

        val url: String = if (isChecked == "true") {
            "https://api.waqi.info/feed/geo:$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
        } else {
            "https://api.waqi.info/feed/$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
        }

        //make a json request for the aqi results
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val status = response.getString("status").toString()
            if (status == "ok") {
                if (response.getJSONObject("data").getJSONObject("iaqi").has("t")) {
                    val temprature =
                        response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("t")
                            .getInt("v").toString()
                    binding.tempratureimage.visibility = View.VISIBLE
                    binding.temprature.visibility = View.VISIBLE
                    binding.tempratureinfo.visibility = View.VISIBLE
                    binding.tempratureinfo.text = temprature + "\u2103"
                }
                if (response.getJSONObject("data").getJSONObject("iaqi").has("h")) {
                    val humidity =
                        response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("h")
                            .getInt("v").toString()
                    binding.humidityimage.visibility = View.VISIBLE
                    binding.humidity.visibility = View.VISIBLE
                    binding.humidityinfo.visibility = View.VISIBLE
                    binding.humidityinfo.text = "$humidity%"
                }
                if (response.getJSONObject("data").getJSONObject("iaqi").has("p")) {
                    val pressure =
                        response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("p")
                            .getInt("v").toString()
                    binding.pressureimage.visibility = View.VISIBLE
                    binding.pressure.visibility = View.VISIBLE
                    binding.pressureinfo.visibility = View.VISIBLE
                    binding.pressureinfo.text = pressure + "hPa"
                }
                if (response.getJSONObject("data").getJSONObject("iaqi").has("w")) {
                    val wind =
                        response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("w")
                            .getInt("v").toString()
                    binding.windimage.visibility = View.VISIBLE
                    binding.wind.visibility = View.VISIBLE
                    binding.windinfo.visibility = View.VISIBLE
                    binding.windinfo.text = wind + "m/s"
                }
            } else {
                binding.info.text =
                    "Error! Unknown city or station! Make sure your toggle button is checked right based on your search. City based or lat/lng based. "
            }
        }, {
            binding.info.text = "Something went wrong!Please try again!"
        })

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)


        return view
    }
}


