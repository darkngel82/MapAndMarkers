package com.example.mapdemoapp.ws

import com.example.mapdemoapp.domain.MapResource
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_VERSION = "v1/"

interface MapService {

    @GET("${API_VERSION}routers/lisboa/resources")
    fun getResources(
        @Query("upperRightLatLon") upperRightLatLon: String,  //38.739429,-9.137115
        @Query("lowerLeftLatLon") lowerLeftLatLong: String   //38.711046,-9.160096

    ): Observable<List<MapResource>>



}
