package br.com.rodrigoamora.transitorio.util

import android.content.Context
import android.location.LocationManager

class GPSUtil {
    companion object {
        fun gpsIsEnable(context: Context): Boolean {
            return try {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE)
                        as LocationManager
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (e: Exception) {
                false
            }
        }
    }
}