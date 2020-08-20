package com.zerone.medtrail.data

import com.zerone.medtrail.data.model.FlickrModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MedTrailNetworkService {

    @GET(EndPoints.FLICKR)
    suspend fun getPhotos(
        @Query("method") method: String,
        @Query("api_key") apikey: String,
        @Query("tags") tags: String,
        @Query("page")page : String,
        @Query("per_page") perPage: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: String
    ) : FlickrModel
}