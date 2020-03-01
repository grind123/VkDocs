package com.grind.vksecondround.fragments


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grind.vksecondround.App
import com.grind.vksecondround.R
import com.grind.vksecondround.adapters.DocsListAdapter
import com.grind.vksecondround.models.Item
import com.grind.vksecondround.presenters.DocsListPresenter
import com.grind.vksecondround.utils.DocsDiffUtilCallback
import com.grind.vksecondround.views.IDocsListView
import java.io.File

class DocsListFragment : Fragment(), IDocsListView, PopupMenu.OnMenuItemClickListener {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: DocsListAdapter
    private val presenter = DocsListPresenter(this)

    private var currDoc: Item? = null
    private var renameDialog: DialogFragment? = null
    private var downloadStarted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_document_list, null)
        rv = v.findViewById(R.id.rv_docs)
        adapter = DocsListAdapter(object : DocsListAdapter.ActionListener {
            override fun onEditButtonClick(anchor: View, item: Item) {
                showPopupMenu(anchor)
                currDoc = item
            }

            override fun onItemClick(item: Item) {
                if (!downloadStarted) {
                    val format = item.title.substringAfterLast('.')
                    val file = File("${activity?.externalCacheDir}/${item.title.substringBeforeLast('.')}.$format")
                    presenter.getDocFile(file, item)
                    downloadStarted = true
                    Toast.makeText(context, "Загрузка файла...", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Загрузка еще не завершена. Подождите...", Toast.LENGTH_SHORT).show()
                }
            }
        })
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        return v
    }

    override fun onStart() {
        super.onStart()

        presenter.getDocsList()
    }

    override fun onDocsListPresent(items: List<Item>) {
        val callback = DocsDiffUtilCallback(adapter.getItems(), items)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setItems(items)
        result.dispatchUpdatesTo(adapter)
    }

    override fun onFileRenamed() {
        renameDialog?.dismissAllowingStateLoss()
        presenter.getDocsList()

    }

    override fun onFileReadyToOpen(file: File, mimeType: String) {
        downloadStarted = false
        previewFile(file, activity!!)
    }


    private fun showPopupMenu(anchor: View) {
        val popupMenu = PopupMenu(this.context, anchor)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.inflate(R.menu.edit_menu)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.deleteDocument(currDoc!!.id)
            }
            R.id.item_edit -> {
                renameDialog = EditDialogFragment(
                    currDoc!!.title,
                    object : EditDialogFragment.OnEditNameListener {
                        override fun saveNewFileName(newFileName: String) {
                            presenter.renameDocument(currDoc!!.id, newFileName)
                        }
                    })
                renameDialog?.show(fragmentManager!!, "editDialog")
            }
        }
        return true
    }

    private fun previewFile(file: File,activity: Activity) {
        val intent = Intent(Intent.ACTION_VIEW)
        val extension =
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimeType =
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        val uri = FileProvider.getUriForFile(
            App.appContext!!,
            App.appContext!!.packageName.toString() + ".provider",
            file
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, mimeType)
        activity.startActivity(intent)
    }
}