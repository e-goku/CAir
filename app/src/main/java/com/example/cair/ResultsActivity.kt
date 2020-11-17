package com.example.cair

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cair.adapters.FragmentAdapter
import com.example.cair.fragments.GraphicsFragment
import com.example.cair.fragments.InfoFragment
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : AppCompatActivity() {
   // private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(InfoFragment(), "AIR QUALITY")
        adapter.addFragment(GraphicsFragment(), "GRAPHICS")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)





//        val city = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
//
//        val url = "http://api.waqi.info/feed/$city/?token=77329dff03657c6b6620ceabb5f59f418ab9eaee"
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET, url, null,
//            Response.Listener { response ->
//                textView.text = "Response: %s".format(response.toString())
//            },
//            Response.ErrorListener { error ->
//                textView.text = "Something went wrong!"
//            }
//        )
//
//        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


   // private fun calculateAqi() {}
}