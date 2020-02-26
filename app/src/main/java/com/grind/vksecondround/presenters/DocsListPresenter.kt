package com.grind.vksecondround.presenters

import BaseDocResponse
import android.util.Log
import com.google.gson.Gson
import com.grind.vksecondround.views.IDocsListView
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse

class DocsListPresenter(view: IDocsListView): IDocsListPresenter {

    val v = view

    override fun getDocsList() {
        val docs = VKRequest("docs.get")
        docs.executeWithListener(object: VKRequest.VKRequestListener(){
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                Log.e("Response", response!!.responseString)
                val baseDocResponse = Gson().fromJson(response.responseString, BaseDocResponse::class.java)
                v.showDocs(baseDocResponse.response.items)
            }
        })
    }
}