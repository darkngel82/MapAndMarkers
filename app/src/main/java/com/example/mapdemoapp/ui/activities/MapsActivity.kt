package com.example.mapdemoapp.ui.activities

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mapdemoapp.R
import com.example.mapdemoapp.domain.MapResource
import com.example.mapdemoapp.ui.widgets.InfoWindowCustom
import com.example.mapdemoapp.ws.NetRepo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var disposableCall: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /** when map is ready, we can set:
     *  starting bounds
     *  request Pois (MapResources)
     *  set map clicks and infowindow*/
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val startBounds = LatLngBounds(
            LatLng(38.711046, -9.160096),
            LatLng(38.739429, -9.137115)
        )

        mMap.setOnMapLoadedCallback {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(startBounds, 17))
        }

        /**EXTRA, unlock to update POIs when user change map bounds, but with requestResource, it can cause stress
         * it can be optimized with feats like:
         * - Don't redraw existing POIS
         * - If zoom out, group nearer POIS into one
         * - Delete POIS out of view
         * */
        /* mMap.setOnCameraChangeListener {
             it as CameraPosition
             requestResources(mMap.projection.visibleRegion.latLngBounds)
         }*/

        mMap.setInfoWindowAdapter(
            InfoWindowCustom(this)
        )

        mMap.setOnInfoWindowClickListener {
            Toast.makeText(this, "wi!", Toast.LENGTH_SHORT).show()
            try {
                startActivity(
                    ResourceDetailActivity.newInstance(this@MapsActivity, it.snippet)
                )
            } catch (e: Exception) {
                Log.e("MAP", e.localizedMessage)
            }
        }
        requestResources(startBounds)
    }

    /**
     * Async call(RX) to get resources
     * if call is in progress (!disposed) we dispose it before create a new call
     */
    private fun requestResources(bounds: LatLngBounds) {

        disposableCall?.apply {
            if (!this.isDisposed) dispose()
        }

        disposableCall = NetRepo().getMapResources(bounds.northeast, bounds.southwest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { listOfResults ->
                    addResourcesFromList(listOfResults)
                },
                onError = {
                    Log.e("DisposeCall", "ERROR: ${it.localizedMessage}")
                }
            )
    }

    /** call addMarker function for each item of received list*/
    private fun addResourcesFromList(listOfResults: List<MapResource>) {
        for (item in listOfResults) {
            if (!existInMap(item)) {
                addMarker(item)
            }
        }
    }

    /** returns if item is already present in the markers*/
    private fun existInMap(item: MapResource): Boolean {
        //TODO check if item is present in map, if it's present, it should not be repainted
        // we can manage this with marker hashmap, if it return the marker requested, it exists in the map
        return false
    }

    /** returns one BitmapDescriptor containing the marker icon tinted as param color*/
    private fun getTintedMarker(color: Int): BitmapDescriptor? {
        val markerBitmap =
            BitmapFactory.decodeResource(
                resources,
                R.drawable.baseline_place_white_24
            )
        val resultBitmap = Bitmap.createBitmap(
            markerBitmap,
            0,
            0,
            markerBitmap.width - 1,
            markerBitmap.height - 1
        )
        val filter: ColorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)

        val markerPaint = Paint()
        markerPaint.colorFilter = filter

        val canvas = Canvas(resultBitmap)
        canvas.drawBitmap(resultBitmap, 0f, 0f, markerPaint)
        return BitmapDescriptorFactory.fromBitmap(resultBitmap)
    }

    /** returns one color for each different companyZoneId
     * it passes the value from 3 digits to 6 according:
     * colorValue = abc
     * newColorValue = aabbcc
     * to use it as parseable color
     * */
    private fun colorOfItem(item: MapResource): Int {
        return item.companyZoneId?.let {
            var colorString = "#${item.companyZoneId}"

            colorString =
                colorString.replace(
                    "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])".toRegex(),
                    "#$1$1$2$2$3$3"
                )
            Color.parseColor(colorString)
        } ?: run { 0 }
    }

    /** Adds the marker to the map*/
    private fun addMarker(item: MapResource) {

        val coordinates = LatLng(item.lat!!, item.lon!!)

        mMap.addMarker(
            MarkerOptions()
                .icon(getTintedMarker(colorOfItem(item)))
                .position(coordinates)
                .infoWindowAnchor(0.5f, -0.1f)
                .snippet(Gson().toJson(item))
        )

    }
}

