 package com.example.cair

 import android.Manifest
 import android.content.Intent
 import android.content.pm.PackageManager
 import android.content.res.Resources
 import android.location.Location
 import android.os.Bundle
 import android.provider.AlarmClock
 import android.util.Log
 import android.view.View
 import android.view.inputmethod.EditorInfo
 import android.widget.EditText
 import android.widget.Toast
 import androidx.appcompat.app.AppCompatActivity
 import androidx.core.app.ActivityCompat
 import androidx.core.content.ContextCompat
 import com.example.cair.databinding.ActivityMapsBinding
 import com.google.android.gms.maps.CameraUpdateFactory
 import com.google.android.gms.maps.GoogleMap
 import com.google.android.gms.maps.OnMapReadyCallback
 import com.google.android.gms.maps.SupportMapFragment
 import com.google.android.gms.maps.model.LatLng
 import com.google.android.gms.maps.model.MapStyleOptions
 import com.google.android.gms.maps.model.MarkerOptions


 class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var map: GoogleMap
    private val TAG = MapsActivity::class.java.simpleName
     private val REQUEST_LOCATION_PERMISSION = 1
     var mapView: View? = null


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
         mapView = mapFragment.view

         mapFragment.getMapAsync(this)


         

        binding.goButton.setOnClickListener { searchResults() }


         //Handles the keyboard action button
         findViewById<EditText>(R.id.location_edit_text).setOnEditorActionListener { v, actionId, event ->
             return@setOnEditorActionListener when (actionId) {
                 EditorInfo.IME_ACTION_SEARCH -> {
                     searchResults()
                     true
                 }
                 else -> false
             }
         }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        setMapStyle(map)

        googleMap.setPadding(0, 2000, 0, 0)

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()


        // Add a marker in Volos and move the camera
        val latitude = 39.36815553925427
        val longitude = 22.948957502138782
        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 10f
        map.addMarker(MarkerOptions().position(homeLatLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        setMapLongClick(map)
    }

    /**
     * Called when the user taps the OK button
     * checks if the string in the text field is empty
     * if is send message "Location cannot be empty"
     * if not calls fun sendCityName()
     */
    private fun searchResults() {
        val stringInTextField = binding.locationEditText.text
        if ( stringInTextField.isNullOrBlank()) {
            Toast.makeText(applicationContext, "Location cannot be empty", Toast.LENGTH_SHORT).show()
        }
        else{ sendCityName() }
    }

    /** Starts the results activity  */
    private fun sendCityName() {
        val message = binding.locationEditText.text.toString()
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
        }
        startActivity(intent)
   }

    /** Sets style for the map*/
    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    /** On long click adds marker*/
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
        }
    }

     /**
      * Enables the My Location layer if the fine location permission has been granted.
      */
     private fun enableMyLocation() {
         if (!::map.isInitialized) return
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
             == PackageManager.PERMISSION_GRANTED) {
             map.isMyLocationEnabled = true
         } else {
             // Permission to access the location is missing. Show rationale and request permission
             ActivityCompat.requestPermissions(
                 this,
                 arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                 REQUEST_LOCATION_PERMISSION
             )
         }
     }

     override fun onMyLocationButtonClick(): Boolean {
         Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
         // Return false so that we don't consume the event and the default behavior still occurs
         // (the camera animates to the user's current position).
         return false
     }

     override fun onMyLocationClick(location: Location) {
         Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG).show()
     }

     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<String>,
         grantResults: IntArray
     ) {
             if (requestCode == REQUEST_LOCATION_PERMISSION) {
                 if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                     enableMyLocation()
                 }
             }
     }
 }

