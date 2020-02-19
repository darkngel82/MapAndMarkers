package com.example.mapdemoapp.ui.widgets

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mapdemoapp.R
import com.example.mapdemoapp.domain.MapResource
import com.example.mapdemoapp.utils.paintData
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson


class InfoWindowCustom(var context: Context) : InfoWindowAdapter {
    var inflater: LayoutInflater? = null

    override fun getInfoContents(marker: Marker): View? {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater!!.inflate(R.layout.view_mapinfoview, null)
        val llInfoWindow = v.findViewById(R.id.llInfoWindow) as LinearLayout

        Gson().fromJson<MapResource>(marker.snippet, MapResource::class.java)?.let {
            paintData(context, llInfoWindow, it)
            addOnClickInfo(llInfoWindow)
        }
        return v
    }

    private fun addOnClickInfo(llInfoWindow: LinearLayout) {
        val infoText = TextView(context)
        infoText.text = "Click me for more detail"
        infoText.setTextColor(Color.BLUE)
        infoText.setPadding(5, 5, 5, 5)
        llInfoWindow.addView(infoText)
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}