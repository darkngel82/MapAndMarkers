package com.example.mapdemoapp.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MapResource {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("scheduledArrival")
    @Expose
    var scheduledArrival: Int? = null

    @SerializedName("locationType")
    @Expose
    var locationType: Int? = null

    @SerializedName("companyZoneId")
    @Expose
    var companyZoneId: Int? = null

    @SerializedName("lat", alternate = ["y"])
    @Expose
    var lat: Double? = null

    @SerializedName("lon", alternate = ["x"])
    @Expose
    var lon: Double? = null

    @SerializedName("licencePlate")
    @Expose
    var licencePlate: String? = null

    @SerializedName("range")
    @Expose
    var range: Int? = null

    @SerializedName("batteryLevel")
    @Expose
    var batteryLevel: Int? = null

    @SerializedName("seats")
    @Expose
    var seats: Int? = null

    @SerializedName("model")
    @Expose
    var model: String? = null

    @SerializedName("resourceImageId")
    @Expose
    var resourceImageId: String? = null

    @SerializedName("pricePerMinuteParking")
    @Expose
    var pricePerMinuteParking: Double? = null

    @SerializedName("pricePerMinuteDriving")
    @Expose
    var pricePerMinuteDriving: Double? = null

    @SerializedName("realTimeData")
    @Expose
    var realTimeData: Boolean? = null

    @SerializedName("engineType")
    @Expose
    var engineType: String? = null

    @SerializedName("resourceType")
    @Expose
    var resourceType: String? = null

    @SerializedName("helmets")
    @Expose
    var helmets: Int? = null

    @SerializedName("station")
    @Expose
    var station: Boolean? = null

    @SerializedName("availableResources")
    @Expose
    var availableResources: Int? = null

    @SerializedName("spacesAvailable")
    @Expose
    var spacesAvailable: Int? = null

    @SerializedName("allowDropoff")
    @Expose
    var allowDropoff: Boolean? = null

    @SerializedName("bikesAvailable")
    @Expose
    var bikesAvailable: Int? = null
}

