package com.arekalov.mvi_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        prepareListeners()
        subscribeOnLiveData()
    }

    private fun subscribeOnLiveData() {
        val img = findViewById<ImageView>(R.id.iv)
        val id = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        myViewModel.getLiveData().observe(this) {
            lifecycleScope.launch(Dispatchers.Main){
                loadImage(it.imageUrl, img)
            }
            Glide.with(this)
                .load(it.imageUrl)
                .into(img)
            id.text = it.imageID
            if (it.isProgressBarVisible) {
                progressBar.visibility = View.VISIBLE
            }
            else {
                progressBar.visibility = View.GONE
            }
        }
    }
    private suspend fun loadImage(image: String, container: ImageView) {
        Glide.with(this)
            .load(image)
            .into(container)
    }

    private fun prepareListeners() {
        findViewById<Button>(R.id.button).setOnClickListener {
            myViewModel.render(Intents.LoadingData)
            myViewModel.render(Intents.DataLoaded)
        }
    }




}