package com.example.upnews.ui.signUp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

private const val REQUEST_LOCATION_PERMISSION_CODE = 1001

fun getLocationAndAddress(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onAddressReceived: (String) -> Unit
) {
    // Cek izin lokasi terlebih dahulu
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Tampilkan koordinat lokasi yang diterima
                Log.d("Location", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")

                // Proses lokasi yang ditemukan dan gunakan Geocoder untuk mendapatkan alamat
                val latitude = location.latitude
                val longitude = location.longitude
                val geocoder = Geocoder(context, Locale.getDefault())

                try {
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val address = addresses[0]  // Ambil alamat pertama yang ditemukan
                        val fullAddress = address.getAddressLine(0)  // Ambil alamat lengkap
                        Log.d("Location", "Full Address: $fullAddress")

                        // Kirimkan alamat ke callback untuk mengisi field
                        onAddressReceived(fullAddress)
                    } else {
                        Log.d("Location", "Alamat tidak ditemukan.")
                    }
                } catch (e: Exception) {
                    Log.e("Geocoder", "Error fetching address: ${e.message}")
                }
            } else {
                Log.d("Location", "Lokasi tidak tersedia")
            }
        }
    } else {
        Log.d("Permission", "Izin lokasi tidak diberikan")
        // Jika izin belum diberikan, minta izin
        checkAndRequestLocationPermission(context)
    }
}

fun checkAndRequestLocationPermission(context: Context) {
    // Jika izin belum diberikan, minta izin
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Meminta izin lokasi
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_LOCATION_PERMISSION_CODE
        )
    }
}
