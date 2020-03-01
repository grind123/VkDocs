package com.grind.vksecondround.presenters

import android.util.Log
import android.webkit.MimeTypeMap
import com.google.gson.Gson
import com.grind.vksecondround.App
import com.grind.vksecondround.models.BaseDocResponse
import com.grind.vksecondround.models.Item
import com.grind.vksecondround.views.IDocsListView
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.requests.VKRequest
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.json.JSONObject
import java.io.File
import java.net.URL
import kotlin.concurrent.thread

class DocsListPresenter(view: IDocsListView) : IDocsListPresenter {

    private val v = view

    override fun getDocsList() {
        val request = VKRequest<JSONObject>("docs.get")
        VK.execute(request, object : VKApiCallback<JSONObject> {
            override fun fail(error: Exception) {
            }

            override fun success(result: JSONObject) {
                val baseDocResponse =
                    Gson().fromJson(result.toString(), BaseDocResponse::class.java)
                v.onDocsListPresent(baseDocResponse.response.items)
            }
        })
    }

    override fun renameDocument(docId: Long, newDocName: String) {
        val request = VKRequest<JSONObject>("docs.edit")
            .addParam("doc_id", docId)
            .addParam("title", newDocName)
        VK.execute(request, object : VKApiCallback<JSONObject> {
            override fun fail(error: Exception) {
                Log.e("Rename", error.message)
            }

            override fun success(result: JSONObject) {
                Log.e("Rename", result.toString())
                v.onFileRenamed()
            }
        })
    }

    override fun deleteDocument(docId: Long) {
        val request = VKRequest<JSONObject>("docs.delete")
            .addParam("owner_id", App.userId)
            .addParam("doc_id", docId)
        VK.execute(request, object : VKApiCallback<JSONObject> {
            override fun fail(error: Exception) {
                Log.e("Rename", error.message)
            }

            override fun success(result: JSONObject) {
                Log.e("Rename", result.toString())
                getDocsList()
            }
        })
    }

    override fun getDocFile(outFile: File, item: Item) {
        thread(start = true) {
            val openConnection = URL(item.url).openConnection()
            val inputStream = openConnection.getInputStream()
            FileUtils.writeByteArrayToFile(outFile, IOUtils.toByteArray(inputStream))

            v.onFileReadyToOpen(outFile, defineMIME(outFile))
        }
    }

    private fun defineMIME(file: File): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.absolutePath)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: ""
    }
}