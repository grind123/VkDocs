package com.grind.vksecondround.views

import com.grind.vksecondround.models.Item
import java.io.File

interface IDocsListView {
    fun onDocsListPresent(items: List<Item>)
    fun onFileRenamed()
    fun onFileReadyToOpen(file: File, mimeType: String)
}