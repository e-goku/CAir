package com.example.cair.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.cair.MySingleton
import com.example.cair.ResultsActivity
import com.example.cair.databinding.FragmentInfoBinding



class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val view = binding.root



        val activity: ResultsActivity? = activity as ResultsActivity?
        val location: String? = activity?.getMyData()

        val urlPhoton="https://photon.komoot.io/api/?q=$location"

        //make a json request for the aqi results
        val url = "https://api.waqi.info/feed/geo:$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                binding.aqi.text = "Response: %s".format(response.toString())
            },
            { error ->
                binding.aqi.text = "Something went wrong!\n Please try again!"
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)
       // getAqi()

        return view
    }


//    private fun getAqi() {
//        val activity: ResultsActivity? = activity as ResultsActivity?
//        val location: String? = activity?.getMyData()
//
//        val url = "http://api.waqi.info/feed/$location/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET, url, null,
//            Response.Listener { response ->
//                binding.aqi.text = "Response: %s".format(response.toString())
//            },
//            Response.ErrorListener { error ->
//                binding.aqi.text = "Something went wrong!\n Please try again!"
//            }
//        )
//
//        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)
//    }








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


