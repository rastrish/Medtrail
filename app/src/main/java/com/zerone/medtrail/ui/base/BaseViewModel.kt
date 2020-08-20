package com.zerone.medtrail.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerone.medtrail.R
import com.zerone.medtrail.util.NetworkHelper
import com.zerone.medtrail.util.Resource
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel() : ViewModel() , KoinComponent {



    private val networkHelper : NetworkHelper by inject()

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()


    fun onCreate(){
        messageStringId.postValue(Resource.success())
    }

    protected fun checkInternetConnection(): Boolean =
        if (networkHelper.isNetworkConnected()) true
        else {
            messageStringId.postValue(Resource.error(R.string.network_connection_error))
            false
        }
}