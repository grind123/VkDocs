package com.grind.vksecondround

import android.app.Application
import android.content.Context
import com.vk.sdk.VKSdk

class App : Application() {
    companion object{
        var appContext: Context? = null
    }


    override fun onCreate() {
        super.onCreate()
        appContext = this
        VKSdk.initialize(this)
    }
}