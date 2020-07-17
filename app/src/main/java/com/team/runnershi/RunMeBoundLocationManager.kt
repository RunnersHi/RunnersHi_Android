package com.team.runnershi

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.*

class RunMeBoundLocationManager {
    @SuppressWarnings("MissingPermission")
    companion object {
        lateinit var runMeBoundLocationListener: RunMeBoundLocationListener
        fun bindLocationListnerIn(
            lifecycleOwner: LifecycleOwner,
            locationListener: LocationListener,
            context: Context
        ) {
            runMeBoundLocationListener = RunMeBoundLocationListener(
                lifecycleOwner,
                locationListener,
                context
            )
        }
        class RunMeBoundLocationListener(
            private val lifecycleOwner: LifecycleOwner,
            private val mLocationListener: LocationListener,
            private val context: Context
        ) : LifecycleObserver {
            var mLocationManager: LocationManager? = null
            init {
                lifecycleOwner.lifecycle.addObserver(this)
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun addLocationListener() {
                mLocationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                mLocationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0f,
                    mLocationListener
                )
                Log.d("BoundLocationMgr", "Listener added")
                val lastLocation = mLocationManager?.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER
                )
                if (lastLocation != null) {
                    mLocationListener.onLocationChanged(lastLocation)
                }
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun removeLocationListener() {
                val i = if (mLocationManager == null) {
                    return
                } else {
                    mLocationManager?.removeUpdates(mLocationListener)
                    mLocationManager = null
                    Log.d("BoundLocationMgr", "Listener Removed")
                }
            }
        }
    }
}