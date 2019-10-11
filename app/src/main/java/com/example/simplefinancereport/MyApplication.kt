package com.example.simplefinancereport

import android.app.Application
import com.example.simplefinancereport.model.MyObjectBox
import io.objectbox.BoxStore

class MyApplication: Application() {

    private lateinit var boxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }

    fun getBoxStore(): BoxStore {
        return boxStore
    }
}