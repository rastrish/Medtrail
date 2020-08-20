package com.zerone.medtrail

import com.zerone.medtrail.data.MedTrailNetworkService
import com.zerone.medtrail.data.model.FlickrModel

class MedtrailRepository (
    private val medTrailNetworkService: MedTrailNetworkService
) {


    private  val apiKey = BuildConfig.APKI_KEY

    suspend fun getPhotos(tag : String,page : String) : FlickrModel {
       return medTrailNetworkService.getPhotos("flickr.photos.search",apiKey,tag,page,"16","json","1")
    }

}