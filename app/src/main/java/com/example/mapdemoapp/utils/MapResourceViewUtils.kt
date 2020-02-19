package com.example.mapdemoapp.utils

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mapdemoapp.domain.MapResource


/**
 * Generic function used to generate one Textview for each field of item, and add it to the LinearLayout view
 */
fun paintData(ctx: Context, view: LinearLayout, item: MapResource) {

    addTextFieldFor(ctx, "name", item.name)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "scheduledArrival", item.scheduledArrival)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "locationType", item.locationType)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "companyZoneId", item.companyZoneId)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "licencePlate", item.licencePlate)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "range", item.range)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "batteryLevel", item.batteryLevel)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "seats", item.seats)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "model", item.model)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "pricePerMinuteParking", item.pricePerMinuteParking)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "realTimeData", item.realTimeData)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "engineType", item.engineType)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "resourceType", item.resourceType)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "helmets", item.helmets)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "station", item.station)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "availableResources", item.availableResources)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "spacesAvailable", item.spacesAvailable)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "allowDropoff", item.allowDropoff)?.let {
        view.addView(it)
    }

    addTextFieldFor(ctx, "bikesAvailable", item.bikesAvailable)?.let {
        view.addView(it)
    }

}

/**
 *  Gets one Textview for one field if it has value, if it's null return null :)
 *
 */
inline fun <reified T : Any> addTextFieldFor(ctx: Context, fieldName: String, value: T?):
        TextView? {

    val strValue: String? = when (T::class) {
        String::class -> value as String?
        else -> value?.toString()
    }

    var textView: TextView? = null
    if (!strValue.isNullOrEmpty()) {
        textView = TextView(ctx)
        textView.text = "$fieldName : $value"
    }
    return textView
}