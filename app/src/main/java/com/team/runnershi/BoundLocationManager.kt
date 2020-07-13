package com.team.runnershi

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class BoundLocationManager {

    @SuppressWarnings("MissingPermission")
    companion object {
        fun bindLocationListnerIn(
            lifecycleOwner: LifecycleOwner,
            locationListener: LocationListener,
            context: Context
        ) {
            BoundLocationListener(
                lifecycleOwner,
                locationListener,
                context
            )

        }

        class BoundLocationListener(
            private val lifecycleOwner: LifecycleOwner,
            private val mLocationListener: LocationListener,
            private val context: Context
        ) : LifecycleObserver {
            private var mLocationManager: LocationManager? = null

            init {
                lifecycleOwner.lifecycle.addObserver(this)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun addLocationListener() {
                mLocationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                mLocationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3000,
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
