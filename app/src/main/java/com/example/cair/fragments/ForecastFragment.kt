package com.example.cair.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.cair.MySingleton
import com.example.cair.R
import com.example.cair.ResultsActivity
import com.example.cair.databinding.FragmentForecastBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class ForecastFragment : Fragment() {
    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        val(location,isChecked) = (activity as ResultsActivity).getMyData()

        val url: String = if (isChecked == "true"){
            "https://api.waqi.info/feed/geo:$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
        }else{
            "https://api.waqi.info/feed/$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
        }

        //make a json request for the aqi results
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val status = response.getString("status").toString()
            if (status == "ok") {
                if(response.getJSONObject("data").has("forecast")){
                    if (response.getJSONObject("data").getJSONObject("forecast").has("daily")) {
                        /////////////////////////pm25 forecast//////////////////////////////////
                        if (response.getJSONObject("data").getJSONObject("forecast")
                                .getJSONObject("daily").has("pm25")
                        ) {
                            val avgArray: MutableList<String> = mutableListOf()
                            val minArray: MutableList<String> = mutableListOf()
                            val maxArray: MutableList<String> = mutableListOf()
                            val dayArray: MutableList<String> = mutableListOf()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast")
                                .getJSONObject("daily").getJSONArray("pm25").length()) {
                                val avg = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm25").getJSONObject(i)
                                    .getString("avg").toString()
                                avgArray.add(avg)
                                val day = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm25").getJSONObject(i)
                                    .getString("day").toString()
                                dayArray.add(day)
                                val max = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm25").getJSONObject(i)
                                    .getString("max").toString()
                                maxArray.add(max)
                                val min = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm25").getJSONObject(i)
                                    .getString("min").toString()
                                minArray.add(min)
                            }

                            /////////////////////////Charts PM2.5///////////////////////////////////
                            val avg = ArrayList<BarEntry>()
                            val min = ArrayList<BarEntry>()
                            val max = ArrayList<BarEntry>()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast").getJSONObject("daily")
                                .getJSONArray("pm25").length()) {
                                avg.add(BarEntry(i.toFloat(), avgArray[i].toFloat()))
                                min.add(BarEntry(i.toFloat(), minArray[i].toFloat()))
                                max.add(BarEntry(i.toFloat(), maxArray[i].toFloat()))
                            }
                            val k = response.getJSONObject("data").getJSONObject("forecast")
                                .getJSONObject("daily").getJSONArray("pm25").length() + 1
                            avg.add(BarEntry(k.toFloat(), 0f))
                            min.add(BarEntry(k.toFloat(), 0f))
                            max.add(BarEntry(k.toFloat(), 0f))

                            val barDataSetAvg = BarDataSet(avg, "Average")
                            barDataSetAvg.setColors(resources.getColor(R.color.yellow))
                            val barDataSetMin = BarDataSet(min, "Min")
                            barDataSetMin.setColors(resources.getColor(R.color.blue))
                            val barDataSetMax = BarDataSet(max, "Max")
                            barDataSetMax.setColors(resources.getColor(R.color.red))

                            val days = IndexAxisValueFormatter(dayArray)
                            val barData = BarData(barDataSetAvg, barDataSetMin, barDataSetMax)
                            binding.BarchartPm25.data = barData
                            binding.BarchartPm25.xAxis.valueFormatter= days
                            binding.BarchartPm25.xAxis.setCenterAxisLabels(true)
                            binding.BarchartPm25.xAxis.position = XAxis.XAxisPosition.BOTTOM
                            binding.BarchartPm25.xAxis.granularity = 1f
                            binding.BarchartPm25.xAxis.axisMinimum = 0f
                            binding.BarchartPm25.setVisibleXRangeMaximum(3f)

                            binding.BarchartPm25.axisLeft.isEnabled = false
                            binding.BarchartPm25.axisRight.isEnabled = false

                            binding.BarchartPm25.isDragEnabled = true
                            binding.BarchartPm25.setDrawBorders(true)
                            binding.BarchartPm25.description.isEnabled = false
                            binding.BarchartPm25.animateY(1000)

                            barData.barWidth = 0.25f
                            val barSpace = 0.05f
                            val groupSpace = 0.1f

                            binding.BarchartPm25.groupBars(0f, groupSpace, barSpace)
                            binding.BarchartPm25.invalidate()
                            ////////////////////////////////////////////////////////////////////////
                        }
                        /////////////////////////pm10 forecast////////////////////////////////////
                        if (response.getJSONObject("data").getJSONObject("forecast")
                                .getJSONObject("daily").has("pm10")
                        ) {
                            val avgArray: MutableList<String> = mutableListOf()
                            val minArray: MutableList<String> = mutableListOf()
                            val maxArray: MutableList<String> = mutableListOf()
                            val dayArray: MutableList<String> = mutableListOf()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast")
                                .getJSONObject("daily").getJSONArray("pm10").length()) {
                                val avg = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm10").getJSONObject(i)
                                    .getString("avg").toString()
                                avgArray.add(avg)
                                val day = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm10").getJSONObject(i)
                                    .getString("day").toString()
                                dayArray.add(day)
                                val max = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm10").getJSONObject(i)
                                    .getString("max").toString()
                                maxArray.add(max)
                                val min = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("pm10").getJSONObject(i)
                                    .getString("min").toString()
                                minArray.add(min)
                            }

                            ///////////////////////Charts PM10//////////////////////////////////////
                            val avg = ArrayList<BarEntry>()
                            val min = ArrayList<BarEntry>()
                            val max = ArrayList<BarEntry>()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast").getJSONObject("daily")
                                .getJSONArray("pm10").length()) {
                                avg.add(BarEntry(i.toFloat(), avgArray[i].toFloat()))
                                min.add(BarEntry(i.toFloat(), minArray[i].toFloat()))
                                max.add(BarEntry(i.toFloat(), maxArray[i].toFloat()))
                            }

                            val k = response.getJSONObject("data").getJSONObject("forecast").getJSONObject("daily").getJSONArray("pm10").length() + 1
                            avg.add(BarEntry(k.toFloat(), 0f))
                            min.add(BarEntry(k.toFloat(), 0f))
                            max.add(BarEntry(k.toFloat(), 0f))

                            val barDataSetAvg = BarDataSet(avg, "Average")
                            barDataSetAvg.setColors(resources.getColor(R.color.yellow))
                            val barDataSetMin = BarDataSet(min, "Min")
                            barDataSetMin.setColors(resources.getColor(R.color.blue))
                            val barDataSetMax = BarDataSet(max, "Max")
                            barDataSetMax.setColors(resources.getColor(R.color.red))

                            val days = IndexAxisValueFormatter(dayArray)
                            val barData = BarData(barDataSetAvg, barDataSetMin, barDataSetMax)
                            binding.BarchartPm10.data = barData
                            binding.BarchartPm10.xAxis.valueFormatter= days
                            binding.BarchartPm10.xAxis.setCenterAxisLabels(true)
                            binding.BarchartPm10.xAxis.position = XAxis.XAxisPosition.BOTTOM
                            binding.BarchartPm10.xAxis.granularity = 1f
                            binding.BarchartPm10.xAxis.axisMinimum = 0f
                            binding.BarchartPm10.setVisibleXRangeMaximum(3f)

                            binding.BarchartPm10.axisLeft.isEnabled = false
                            binding.BarchartPm10.axisRight.isEnabled = false

                            binding.BarchartPm10.isDragEnabled = true
                            binding.BarchartPm10.setDrawBorders(true)
                            binding.BarchartPm10.description.isEnabled = false
                            binding.BarchartPm10.animateY(1000)

                            barData.barWidth = 0.25f
                            val barSpace = 0.05f
                            val groupSpace = 0.1f

                            binding.BarchartPm10.groupBars(0f, groupSpace, barSpace)
                            binding.BarchartPm10.invalidate()
                            ////////////////////////////////////////////////////////////////////////
                        }
                        ///////////////////////////o3 forecast///////////////////////////////////
                        if (response.getJSONObject("data").getJSONObject("forecast")
                                .getJSONObject("daily").has("o3")) {
                            val avgArray: MutableList<String> = mutableListOf()
                            val minArray: MutableList<String> = mutableListOf()
                            val maxArray: MutableList<String> = mutableListOf()
                            val dayArray: MutableList<String> = mutableListOf()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast")
                                .getJSONObject("daily").getJSONArray("o3").length()) {
                                val avg = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("o3").getJSONObject(i)
                                    .getString("avg").toString()
                                avgArray.add(avg)
                                val day = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("o3").getJSONObject(i)
                                    .getString("day").toString()
                                dayArray.add(day)
                                val max = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("o3").getJSONObject(i)
                                    .getString("max").toString()
                                maxArray.add(max)
                                val min = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("o3").getJSONObject(i)
                                    .getString("min").toString()
                                minArray.add(min)
                            }

                            /////////////////////////// charts o3 //////////////////////////////////
                            val avg = ArrayList<BarEntry>()
                            val min = ArrayList<BarEntry>()
                            val max = ArrayList<BarEntry>()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast").getJSONObject("daily").getJSONArray("o3")
                                .length()) {
                                avg.add(BarEntry(i.toFloat(), avgArray[i].toFloat()))
                                min.add(BarEntry(i.toFloat(), minArray[i].toFloat()))
                                max.add(BarEntry(i.toFloat(), maxArray[i].toFloat()))
                            }
                            val k = response.getJSONObject("data").getJSONObject("forecast").getJSONObject("daily").getJSONArray("o3").length() + 1
                            avg.add(BarEntry(k.toFloat(), 0f))
                            min.add(BarEntry(k.toFloat(), 0f))
                            max.add(BarEntry(k.toFloat(), 0f))

                            val barDataSetAvg = BarDataSet(avg, "Average")
                            barDataSetAvg.setColors(resources.getColor(R.color.yellow))
                            val barDataSetMin = BarDataSet(min, "Min")
                            barDataSetMin.setColors(resources.getColor(R.color.blue))
                            val barDataSetMax = BarDataSet(max, "Max")
                            barDataSetMax.setColors(resources.getColor(R.color.red))

                            val days = IndexAxisValueFormatter(dayArray)
                            val barData = BarData(barDataSetAvg, barDataSetMin, barDataSetMax)
                            binding.BarchartO3.data = barData
                            binding.BarchartO3.xAxis.valueFormatter= days
                            binding.BarchartO3.xAxis.setCenterAxisLabels(true)
                            binding.BarchartO3.xAxis.position = XAxis.XAxisPosition.BOTTOM
                            binding.BarchartO3.xAxis.granularity = 1f
                            binding.BarchartO3.xAxis.axisMinimum = 0f
                            binding.BarchartO3.setVisibleXRangeMaximum(3f)

                            binding.BarchartO3.axisLeft.isEnabled = false
                            binding.BarchartO3.axisRight.isEnabled = false

                            binding.BarchartO3.isDragEnabled = true
                            binding.BarchartO3.setDrawBorders(true)
                            binding.BarchartO3.description.isEnabled = false
                            binding.BarchartO3.animateY(1000)

                            barData.barWidth = 0.25f
                            val barSpace = 0.05f
                            val groupSpace = 0.1f

                            binding.BarchartO3.groupBars(0f, groupSpace, barSpace)
                            binding.BarchartO3.invalidate()
                            ////////////////////////////////////////////////////////////////////////
                        }
                        //////////////////////////uvi forecast/////////////////////////////
                        if (response.getJSONObject("data").getJSONObject("forecast")
                                .getJSONObject("daily").has("uvi")
                        ) {
                            val avgArray: MutableList<String> = mutableListOf()
                            val minArray: MutableList<String> = mutableListOf()
                            val maxArray: MutableList<String> = mutableListOf()
                            val dayArray: MutableList<String> = mutableListOf()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast")
                                .getJSONObject("daily").getJSONArray("uvi").length()) {
                                val avg = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("uvi").getJSONObject(i)
                                    .getString("avg").toString()
                                avgArray.add(avg)
                                val day = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("uvi").getJSONObject(i)
                                    .getString("day").toString()
                                dayArray.add(day)
                                val max = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("uvi").getJSONObject(i)
                                    .getString("max").toString()
                                maxArray.add(max)
                                val min = response.getJSONObject("data").getJSONObject("forecast")
                                    .getJSONObject("daily").getJSONArray("uvi").getJSONObject(i)
                                    .getString("min").toString()
                                minArray.add(min)
                            }

                            ////////////////////////// charts uvi //////////////////////////////////
                            val avg = ArrayList<BarEntry>()
                            val min = ArrayList<BarEntry>()
                            val max = ArrayList<BarEntry>()
                            for (i in 0 until response.getJSONObject("data")
                                .getJSONObject("forecast")
                                .getJSONObject("daily").getJSONArray("uvi").length()) {
                                avg.add(BarEntry(i.toFloat(), avgArray[i].toFloat()))
                                min.add(BarEntry(i.toFloat(), minArray[i].toFloat()))
                                max.add(BarEntry(i.toFloat(), maxArray[i].toFloat()))
                            }
                            val k = response.getJSONObject("data").getJSONObject("forecast").getJSONObject("daily").getJSONArray("uvi").length() + 1
                            avg.add(BarEntry(k.toFloat(), 0f))
                            min.add(BarEntry(k.toFloat(), 0f))
                            max.add(BarEntry(k.toFloat(), 0f))

                            val barDataSetAvg = BarDataSet(avg, "Average")
                            barDataSetAvg.setColors(resources.getColor(R.color.yellow))
                            val barDataSetMin = BarDataSet(min, "Min")
                            barDataSetMin.setColors(resources.getColor(R.color.blue))
                            val barDataSetMax = BarDataSet(max, "Max")
                            barDataSetMax.setColors(resources.getColor(R.color.red))

                            val days = IndexAxisValueFormatter(dayArray)
                            val barData = BarData(barDataSetAvg, barDataSetMin, barDataSetMax)
                            binding.BarchartUvi.data = barData
                            binding.BarchartUvi.xAxis.valueFormatter= days
                            binding.BarchartUvi.xAxis.setCenterAxisLabels(true)
                            binding.BarchartUvi.xAxis.position = XAxis.XAxisPosition.BOTTOM
                            binding.BarchartUvi.xAxis.granularity = 1f
                            binding.BarchartUvi.xAxis.axisMinimum = 0f
                            binding.BarchartUvi.setVisibleXRangeMaximum(3f)

                            binding.BarchartUvi.axisLeft.isEnabled = false
                            binding.BarchartUvi.axisRight.isEnabled = false

                            binding.BarchartUvi.isDragEnabled = true
                            binding.BarchartUvi.setDrawBorders(true)
                            binding.BarchartUvi.description.isEnabled = false
                            binding.BarchartUvi.animateY(1000)

                            barData.barWidth = 0.25f
                            val barSpace = 0.05f
                            val groupSpace = 0.1f

                            binding.BarchartUvi.groupBars(0f, groupSpace, barSpace)
                            binding.BarchartUvi
                                .invalidate()
                            ////////////////////////////////////////////////////////////////////////
                        }
                    }
                }
            }
        }, {
            //error case
        })

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)

        return view
    }
}

