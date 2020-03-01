package com.grind.vksecondround.presenters

import com.grind.vksecondround.models.Item
import java.io.File


interface IDocsListPresenter {
    fun getDocsList()
    fun renameDocument(docId: Long, newDocName: String)
    fun deleteDocument(docId: Long)
    fun getDocFile(outFile: File, item: Item)
}