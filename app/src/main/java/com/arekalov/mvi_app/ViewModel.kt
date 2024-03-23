package com.arekalov.mvi_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.arekalov.mvi_app.data.FoxNetWorkService
import com.arekalov.mvi_app.data.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MyViewModel(private val foxNetworkService: FoxNetWorkService) : ViewModel() {
    private val liveData = MutableLiveData<State>()

    fun render(intent: Intents) {
        when (intent) {
            is Intents.LoadingData -> {
                liveData.value = liveData.value?.copy(
                    isProgressBarVisible = true
                )
            }
            is Intents.DataLoaded -> {
                
            }
            is Intents.DataError -> {
                liveData.value = liveData.value?.copy(
                    isProgressBarVisible = false,
                    imageID = "Error"
                )
            }
        }
    }


    fun getLiveData() : LiveData<State> = liveData
}