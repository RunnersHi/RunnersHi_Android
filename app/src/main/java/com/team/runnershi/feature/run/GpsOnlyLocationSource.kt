package com.team.runnershi.feature.run

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.naver.maps.map.LocationSource


class GpsOnlyLocationSource(private val activity: Activity)
    : LocationSource, LocationListener {
    private val locationManager = activity
        .getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    private var listener: LocationSource.OnLocationChangedListener? = null

    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        if (locationManager == null) {
            return
        }

        if (PermissionChecker.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PERMISSION_GRANTED
            && PermissionChecker.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
                REQUEST_LOCATION_PERMISSION_CODE
            )
        }

        this.listener = listener
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 1000, 10f, this)
    }

    override fun deactivate() {
        if (locationManager == null) {
            return
        }

        listener = null
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        listener?.onLocationChanged(location)
    }

    override fun onStatusChanged(provider: String, status: Int,
                                 extras: Bundle
    ) {
    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onProviderDisabled(provider: String) {
    }

    companion object{
        val REQUEST_LOCATION_PERMISSION_CODE = 1000;
    }
}
