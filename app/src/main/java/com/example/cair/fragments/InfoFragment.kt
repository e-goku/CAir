package com.example.cair.fragments

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.example.cair.databinding.FragmentInfoBinding

class  InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var objectString : String


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        val(location,isChecked) = (activity as ResultsActivity).getMyData()

        val url: String = if (isChecked == "true"){
            "https://api.waqi.info/feed/geo:$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
        }else{
            "https://api.waqi.info/feed/$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
        }

            //make a json request for the aqi results
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                objectString = response.toString()
                Log.d("aqi", objectString)

                val status = response.getString("status").toString()
                if (status == "ok") {
                    if (response.getJSONObject("data").getJSONObject("city").has("name")) {
                        val city =
                            response.getJSONObject("data").getJSONObject("city").getString("name")
                                .toString()
                        binding.city.text = "$city Real-time Air Quality Index (AQI)"
                    }
                    if (response.getJSONObject("data").getJSONObject("time").has("s")) {
                        val time =
                            response.getJSONObject("data").getJSONObject("time").getString("s")
                                .toString()
                        val tz =   response.getJSONObject("data").getJSONObject("time").getString("tz")
                            .toString()
                        binding.time.text = "Updated at $time  $tz"
                    }
                    if (response.getJSONObject("data").has("aqi")) {
                        val aqi = response.getJSONObject("data").getInt("aqi").toString()
                        if (aqi.toInt() in 1..50) {
                            Color.parseColor("#22EF2B")
                            binding.aqi.setTextColor(Color.parseColor("#22EF2B"))
                            binding.aqi.text = aqi
                            binding.aqiInfo.setTextColor(Color.parseColor("#22EF2B"))
                            binding.aqiInfo.text = "Good"
                            binding.info.text = getString(R.string.good)
                        }
                        if (aqi.toInt() in 51..100) {
                            Color.parseColor("#F7FF2A")
                            binding.aqi.setTextColor(Color.parseColor("#F7FF2A"))
                            binding.aqi.text = aqi
                            binding.aqiInfo.setTextColor(Color.parseColor("#F7FF2A"))
                            binding.aqiInfo.text = "Moderate"
                            binding.info.text = getString(R.string.moderate)

                        }
                        if (aqi.toInt() in 101..150) {
                            Color.parseColor("#FF9800")
                            binding.aqi.setTextColor(Color.parseColor("#FF9800"))
                            binding.aqi.text = aqi
                            binding.aqiInfo.setTextColor(Color.parseColor("#FF9800"))
                            binding.aqiInfo.text = "Unhealthy for Sensitive Groups"
                            binding.info.text = getString(R.string.unhealthySensitiveGroups)
                        }
                        if (aqi.toInt() in 151..200) {
                            Color.parseColor("#E91E63")
                            binding.aqi.setTextColor(Color.parseColor("#E91E63"))
                            binding.aqi.text = aqi
                            binding.aqiInfo.setTextColor(Color.parseColor("#E91E63"))
                            binding.aqiInfo.text = "Unhealthy"
                            binding.info.text = getString(R.string.unhealthy)

                        }
                        if (aqi.toInt() in 201..300) {
                            Color.parseColor("#A01DB6")
                            binding.aqi.setTextColor(Color.parseColor("#A01DB6"))
                            binding.aqi.text = aqi
                            binding.aqiInfo.setTextColor(Color.parseColor("#A01DB6"))
                            binding.aqiInfo.text = "Very Unhealthy"
                            binding.info.text = getString(R.string.veryUnhealthy)
                        }
                        if (aqi.toInt() > 300) {
                            Color.parseColor("#DF002D")
                            binding.aqi.setTextColor(Color.parseColor("#DF002D"))
                            binding.aqi.text = aqi
                            binding.aqiInfo.setTextColor(Color.parseColor("#DF002D"))
                            binding.aqiInfo.text = "Hazardous"
                            binding.info.text = getString(R.string.hazardous)
                        }
                    }
                    if (response.getJSONObject("data").getJSONObject("iaqi").has("pm25")) {
                        val pm25 =
                            response.getJSONObject("data").getJSONObject("iaqi")
                                .getJSONObject("pm25")
                                .getInt("v").toString()
                        binding.pm25.visibility = View.VISIBLE
                        binding.pm25info.visibility = View.VISIBLE
                        binding.pm25.text = "PM2.5"
                        binding.pm25info.text = pm25
                    }
                    if (response.getJSONObject("data").getJSONObject("iaqi").has("pm10")) {
                        val pm10 =
                            response.getJSONObject("data").getJSONObject("iaqi")
                                .getJSONObject("pm10")
                                .getInt("v").toString()
                        binding.pm10.visibility = View.VISIBLE
                        binding.pm10info.visibility = View.VISIBLE
                        binding.pm10.text = "PM10"
                        binding.pm10info.text = pm10
                    }
                    if (response.getJSONObject("data").getJSONObject("iaqi").has("no2")) {
                        val no2 =
                            response.getJSONObject("data").getJSONObject("iaqi")
                                .getJSONObject("no2")
                                .getInt("v").toString()
                        binding.no2.visibility = View.VISIBLE
                        binding.no2info.visibility = View.VISIBLE
                        binding.no2.text = "NO\u2082"
                        binding.no2info.text = no2
                    }
                    if (response.getJSONObject("data").getJSONObject("iaqi").has("o3")) {
                        val o3 =
                            response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("o3")
                                .getInt("v").toString()
                        binding.o3.visibility = View.VISIBLE
                        binding.o3info.visibility = View.VISIBLE
                        binding.o3.text = "O\u2083"
                        binding.o3info.text = o3
                    }
                    if (response.getJSONObject("data").getJSONObject("iaqi").has("so2")) {
                        val so2 =
                            response.getJSONObject("data").getJSONObject("iaqi")
                                .getJSONObject("so2")
                                .getInt("v").toString()
                        binding.so2.visibility = View.VISIBLE
                        binding.so2info.visibility = View.VISIBLE
                        binding.so2.text = "SO\u2082"
                        binding.so2info.text = so2
                    }
                    if (response.getJSONObject("data").getJSONObject("iaqi").has("co")) {
                        val co =
                            response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("co")
                                .getInt("v").toString()
                        binding.co.visibility = View.VISIBLE
                        binding.coinfo.visibility = View.VISIBLE
                        binding.co.text = "CO "
                        binding.coinfo.text = co
                    }
                }else{
                    binding.info.text = "Error! Unknown city or station! Make sure your toggle button is checked right based on your search. City based or lat/lng based. "
                }
            }, {
                binding.info.text = "Something went wrong!Please try again!"
            })

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)


        return view
    }












//    private fun particles() {
//        val rootView = binding.rootConstraintLayout
//
//        val viewTreeObserver: ViewTreeObserver = rootView.viewTreeObserver
//        if (viewTreeObserver.isAlive) {
//            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
////
////                    var particleSystem = ParticleSystem(activity, 500, R.drawable.cloud, 20000)
////                        .setSpeedRange(0.02f, 0.1f)
////                        .setRotationSpeed(40F)
////                        .setScaleRange(0.3f, 0.4f)
////                        .emitWithGravity(view?.findViewById(R.id.anim_background), Gravity.BOTTOM, 8)
//                }
//            })
//        }
//
//        // ------------------------------------------------------------------------------------------------------------------
//        // When the app starts, we create a thread that is responsible for updating the main currency view every X seconds.
//        // This thread is posting changes for the UI thread.
//        // PROCEED WITH CAUTION WHEN CHANGING THIS PART OF THE CODE
//        // ------------------------------------------------------------------------------------------------------------------
//        val mainDelay : Long = 300
//        val handler = Handler()
//        val runnable: Runnable = object : Runnable {
//            override fun run() {
//                var x: Int = 0
//                var y: Int = 0
//                when ((0..3).random()) {
//                    0 -> {
//                        x = -40
//                        y = (0..rootView.height).random()
//                    }
//                    1 -> {
//                        x = (0..rootView.width).random()
//                        y = -40
//                    }
//                    2 -> {
//                        x = rootView.width + 40
//                        y = (0..rootView.height).random()
//                    }
//                    3 -> {
//                        x = (0..rootView.width).random()
//                        y = rootView.height + 400
//                    }
//                }
//
//
//                lateinit var floatingItem: Drawable
//                when ((0..5).random()) {
//                    0 -> floatingItem = resources.getDrawable(R.drawable.cloud)
//                    1 -> floatingItem = resources.getDrawable(R.drawable.cloud1)
//                    2 -> floatingItem = resources.getDrawable(R.drawable.cloud2)
//                    3 -> floatingItem = resources.getDrawable(R.drawable.cloud3)
//                    4 -> floatingItem = resources.getDrawable(R.drawable.cloud4)
//                    5 -> floatingItem = resources.getDrawable(R.drawable.cloud5)
//                }
//
//                rootView.post {
//                    var particleSystem = ParticleSystem(
//                        activity,
//                        500,
//                        floatingItem,
//                        30000,
//                        R.id.anim_background
//                    )
//                        .setSpeedRange(0.02f, 0.1f)
//                        .setRotationSpeed(40F)
//                        .setScaleRange(0.2f, 0.2f)
//                        .emit(x, y, 1, 700)
//                }
//                handler.postDelayed(this, mainDelay)
//            }
//        }
//        handler.postDelayed(runnable, mainDelay)
//        // ------------------------------------------------------------------------------------------------------------------
//        // ------------------------------------------------------------------------------------------------------------------
//    }
}






//val rootView = findViewById<ConstraintLayout>(R.id.root_constraint_layout)
//
//val viewTreeObserver: ViewTreeObserver = rootView.viewTreeObserver
//if (viewTreeObserver.isAlive) {
//    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//        override fun onGlobalLayout() {
//            rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//
//            var particleSystem = ParticleSystem(this@InfoFragment, 500, R.drawable.compass, 20000)
//                .setSpeedRange(0.02f, 0.1f)
//                .setRotationSpeed(40F)
//                .setScaleRange(0.3f, 0.4f)
//                .emit((0..200).random(), (0..200).random(), 1)
//
//
//        }
//    })
//}
//
//// ------------------------------------------------------------------------------------------------------------------
//// When the app starts, we create a thread that is responsible for updating the main currency view every X seconds.
//// This thread is posting changes for the UI thread.
//// PROCEED WITH CAUTION WHEN CHANGING THIS PART OF THE CODE
//// ------------------------------------------------------------------------------------------------------------------
//val mainDelay : Long = 300
//val handler = Handler()
//val runnable: Runnable = object : Runnable {
//    override fun run() {
//        var x: Int = 0
//        var y: Int = 0
//        when ((0..3).random()) {
//            0 -> {
//                x = -40
//                y = (0..rootView.height).random()
//            }
//            1 -> {
//                x = (0..rootView.width).random()
//                y = -40
//            }
//            2 -> {
//                x = rootView.width + 40
//                y = (0..rootView.height).random()
//            }
//            3 -> {
//                x = (0..rootView.width).random()
//                y = rootView.height + 400
//            }
//        }
//
//
//        var floating_item: Drawable =
//            resources.getDrawable(R.drawable.compass)
////                    ResourcesCompat.getDrawable(resources, R.drawable.compass, null)!!
//        when ((0..8).random()) {
//            0 -> floating_item = resources.getDrawable(R.drawable.compass)
//            1 -> floating_item = resources.getDrawable(R.drawable.eraser)
//            2 -> floating_item = resources.getDrawable(R.drawable.highlighter)
//            3 -> floating_item = resources.getDrawable(R.drawable.pencil)
//            4 -> floating_item = resources.getDrawable(R.drawable.pens)
//            5 -> floating_item = resources.getDrawable(R.drawable.protractor)
//            6 -> floating_item = resources.getDrawable(R.drawable.ruler)
//            7 -> floating_item = resources.getDrawable(R.drawable.send)
//            8 -> floating_item = resources.getDrawable(R.drawable.sharpener)
//        }
//
//        rootView.post {
////                    Log.d("xy", "$x $y")
//            var particleSystem = ParticleSystem(
//                this@MainActivity,
//                500,
//                floating_item,
//                30000,
//                R.id.frame_anim_background
//            )
////                        .setSpeedModuleAndAngleRange(0.02f, 0.1f, 0, 180)
//                .setSpeedRange(0.02f, 0.1f)
//                .setRotationSpeed(40F)
//                .setScaleRange(0.2f, 0.2f)
//                .emit(x, y, 1, 700)
//        }
//        handler.postDelayed(this, mainDelay)
//    }
//}
//handler.postDelayed(runnable, mainDelay)
//// ------------------------------------------------------------------------------------------------------------------
//// ------------------------------------------------------------------------------------------------------------------
//}


