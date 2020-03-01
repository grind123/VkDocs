package com.grind.vksecondround

import android.app.Application
import android.content.Context
import com.vk.api.sdk.VK

class App : Application() {
    companion object{
        var appContext: Context? = null
        var userId: Int = 0
    }


    override fun onCreate() {
        super.onCreate()
        appContext = this
        VK.initialize(this)
    }
}