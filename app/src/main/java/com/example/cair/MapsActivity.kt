 package com.example.cair

 import android.Manifest
 import android.annotation.SuppressLint
 import android.content.Intent
 import android.content.pm.PackageManager
 import android.content.res.Resources
 import android.location.Location
 import android.os.Bundle
 import android.util.Log
 import android.view.inputmethod.EditorInfo
 import android.widget.EditText
 import android.widget.TextView
 import android.widget.Toast
 import androidx.appcompat.app.AppCompatActivity
 import androidx.core.app.ActivityCompat
 import androidx.core.content.ContextCompat
 import com.example.cair.databinding.ActivityMapsBinding
 import com.google.android.gms.location.FusedLocationProviderClient
 import com.google.android.gms.location.LocationServices
 import com.google.android.gms.maps.*
 import com.google.android.gms.maps.model.*


 class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

     private lateinit var binding: ActivityMapsBinding
     private val TAG = MapsActivity::class.java.simpleName
     private val REQUEST_LOCATION_PERMISSION = 1
     private lateinit var marker: Marker
     private lateinit var map: GoogleMap
     private  lateinit var currentLocation: Location
     private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
     private val permissionCode = 101


     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityMapsBinding.inflate(layoutInflater)
         val view = binding.root
         setContentView(view)

         fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@MapsActivity)
         fetchLocation()

         /**sets the switch textOn and textOff*/
         binding.toggleText.text = getString(R.string.coordinates)
         binding.switchBtn.setOnCheckedChangeListener { _, _ ->
             val toggleText = binding.switchBtn.isChecked
             if (toggleText) {
                    binding.toggleText.text = getString(R.string.coordinates)
             } else {
                    binding.toggleText.text = getString(R.string.city)
                    binding.locationEditText.setText("", TextView.BufferType.EDITABLE)
                }
         }

         binding.goButton.setOnClickListener { searchResults() }


         //Handles the keyboard action button
         findViewById<EditText>(R.id.location_edit_text).setOnEditorActionListener { _, actionId, _ ->
             return@setOnEditorActionListener when (actionId) {
                 EditorInfo.IME_ACTION_SEARCH -> {
                     searchResults()
                     true
                 }
                 else -> false
             }
         }
     }

     @SuppressLint("SetTextI18n")
     private fun fetchLocation() {
         if (ActivityCompat.checkSelfPermission(
                 this, Manifest.permission.ACCESS_FINE_LOCATION
             ) !=
             PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 this, Manifest.permission.ACCESS_COARSE_LOCATION
             ) !=
             PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(
                 this,
                 arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
             )
             return
         }
         val task = fusedLocationProviderClient.lastLocation
         task.addOnSuccessListener { location ->
             if (location != null) {
                 currentLocation = location
                 val latitude = location.latitude
                 val longitude = location.longitude
                 binding.locationEditText.setText(
                     "$latitude;$longitude",
                     TextView.BufferType.EDITABLE
                 )
                 val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as
                         SupportMapFragment?)!!
                 supportMapFragment.getMapAsync(this@MapsActivity)
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

         val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
         googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

         enableMyLocation()

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
         if (stringInTextField.isNullOrBlank()) {
             Toast.makeText(applicationContext, "Location cannot be empty", Toast.LENGTH_SHORT)
                 .show()
         } else {
             sendCityName()
         }
     }

     /** Starts the results activity  */
     private fun sendCityName() {
         val message1 = binding.locationEditText.text.toString()
         val message2 = binding.switchBtn.isChecked
         val intent = Intent(this, ResultsActivity::class.java)
         val extras = Bundle()
         extras.putString("EXTRA_LOCATION", message1)
         extras.putString("EXTRA_BOOLEAN", message2.toString())
         intent.putExtras(extras)

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
     @SuppressLint("SetTextI18n")
     private fun setMapLongClick(map: GoogleMap) {
         map.setOnMapLongClickListener { latLng ->
             map.clear()
             marker = map.addMarker(MarkerOptions().position(latLng))
             val position = marker.position
             val latitude = position.latitude
             val longitude = position.longitude

             binding.locationEditText.setText("$latitude;$longitude", TextView.BufferType.EDITABLE)
         }


     }

     /**Enables the My Location layer if the fine location permission has been granted.*/
     private fun enableMyLocation() {
         if (!::map.isInitialized) return
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
             == PackageManager.PERMISSION_GRANTED
         ) {
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

         when (requestCode) {
             permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                 PackageManager.PERMISSION_GRANTED
             ) {
                 fetchLocation()
             }
         }
     }
 }