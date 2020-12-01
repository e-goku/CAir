package com.example.cair

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cair.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.okButton.setOnClickListener { buttonClick() }
    }


    private fun buttonClick() {
        val stringInTextField = binding.locationEditText.text
        if ( stringInTextField.isNullOrBlank()) {
            Toast.makeText(applicationContext,"Location cannot be empty",Toast.LENGTH_SHORT).show()
        }
        else{ sendCityName() }
    }

    /** Called when the user taps the OK button */
    private fun sendCityName() {
        val message = binding.locationEditText.text.toString()
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }


}

