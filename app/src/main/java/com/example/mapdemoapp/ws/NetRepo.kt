package com.example.mapdemoapp.ws

import com.example.mapdemoapp.BuildConfig
import com.example.mapdemoapp.constants.SERVICE_API_URL
import com.example.mapdemoapp.domain.MapResource
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class NetRepo {
    val client: MapService = setupClient()

    private fun setupClient(): MapService {
        val client = getHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit.create(MapService::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) { // show logs only for debug
            clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        }

        return clientBuilder.build()
    }


    /** Returns the map resources to be shown in map according to:
     *
     * @param upperRightLatLon: upper right coordinates of the map
     * @param lowerLeftLatLong:  lower left coordinates of the map
     * */
    fun getMapResources(
        upperRightLatLon: LatLng,
        lowerLeftLatLong: LatLng
    ): Observable<List<MapResource>> {
        return client.getResources(
            "${upperRightLatLon.latitude},${upperRightLatLon.longitude}",
            "${lowerLeftLatLong.latitude},${lowerLeftLatLong.longitude}"
        )
    }
}