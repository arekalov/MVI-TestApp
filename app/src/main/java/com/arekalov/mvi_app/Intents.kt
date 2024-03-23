package com.arekalov.mvi_app

sealed class Intents {
    object LoadingData: Intents()
    object DataLoaded: Intents()
    class DataError(val text: String) : Intents()
}