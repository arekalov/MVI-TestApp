package com.arekalov.mvi_app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arekalov.mvi_app.data.FoxNetWorkService
import com.arekalov.mvi_app.data.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MyViewModel() : ViewModel() {
    private val liveData = MutableLiveData<State>()

    init {
        liveData.value = State()
    }

    fun render(intent: Intents) {
        when (intent) {
            is Intents.LoadingData -> {
                liveData.value = liveData.value?.copy(
                    isProgressBarVisible = true
                )
            }

            is Intents.DataLoaded -> {
                var a: Model? = null
                CoroutineScope(Job()).launch(Dispatchers.IO) {
                    try {
                        a = async { FoxNetWorkService.getFox() }.await().body()
                        withContext(Dispatchers.Main) {
                            liveData.value = liveData.value?.copy(
                                isProgressBarVisible = false,
                                imageUrl = a!!.image,
                                imageID = a!!.link
                            )
                        }
                    } catch (e: Exception) {
                        render(Intents.DataError(e.message.toString()))
                    }
                }
//                CoroutineScope(Job()).launch {
//                    delay(2000)
//                    withContext(Dispatchers.Main) {
//                        liveData.value =
//                            liveData.value?.copy(imageID = liveData.value!!.imageID + "!", isProgressBarVisible = false)
//                    }
//                }

            }

            is Intents.DataError -> {
                liveData.value = liveData.value?.copy(
                    isProgressBarVisible = false,
                    imageID = "Error"
                )
            }
        }
    }


    fun getLiveData(): LiveData<State> = liveData
}