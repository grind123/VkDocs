package com.grind.vksecondround.fragments

import Item
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grind.vksecondround.R
import com.grind.vksecondround.adapters.DocsListAdapter
import com.grind.vksecondround.presenters.DocsListPresenter
import com.grind.vksecondround.views.IDocsListView

class DocsListFragment: Fragment(), IDocsListView {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: DocsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_document_list, null)
        rv = v.findViewById(R.id.rv_docs)
        adapter = DocsListAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        return v
    }

    override fun onStart() {
        super.onStart()
        val docsListPresenter = DocsListPresenter(this)
        docsListPresenter.getDocsList()
    }

    override fun showDocs(items: List<Item>) {
        Log.e("${this.javaClass.simpleName}", "showDocs")
        adapter.setItems(items)
    }
}