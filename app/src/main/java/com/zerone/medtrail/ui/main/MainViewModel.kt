package com.zerone.medtrail.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zerone.medtrail.MedtrailRepository
import com.zerone.medtrail.data.model.FlickrModel
import com.zerone.medtrail.ui.base.BaseViewModel
import com.zerone.medtrail.util.Resource
import com.zerone.medtrail.util.Status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val medtrailRepository: MedtrailRepository) : BaseViewModel() {


    val photos = MutableLiveData<Resource<FlickrModel>>()
    private var url = String()
    val temp: ArrayList<String> = ArrayList()
    var list: MutableLiveData<ArrayList<String>> = MutableLiveData()


    // Exception Handler
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Error", throwable?.let { it.message})
        photos.postValue(Resource(Status.ERROR, null))
    }

    // Network Call with Kotlin Corountines

    fun getPhotos(tag: String, page: String) {
        if (checkInternetConnection()) {
            photos.postValue(Resource(Status.LOADING, null))
            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                medtrailRepository.getPhotos(tag, page).let {
                    for (photos in it.photos.photo) {
                        url =
                            "https://farm${photos.farm}.staticflickr.com/${photos.server}/${photos.id}_${photos.secret}.jpg"
                        temp.add(url)
                    }
                    list.postValue(temp)
                }
                photos.postValue(Resource(Status.SUCCESS, null))
            }
        }

    }


}