package com.arekalov.mvi_app

data class State(
    val imageUrl: String = "",
    val imageID: String = "",
    val isProgressBarVisible: Boolean = false,
)