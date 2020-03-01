package com.grind.vksecondround.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grind.vksecondround.App
import com.grind.vksecondround.R
import com.grind.vksecondround.models.Item
import com.grind.vksecondround.utils.DpPxUtil
import com.grind.vksecondround.utils.StringConverter
import java.text.SimpleDateFormat
import java.util.*


class DocsListAdapter(private val listener: ActionListener): RecyclerView.Adapter<DocsListAdapter.DocHolder>() {

    private var items = listOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocHolder {
        val view = View.inflate(parent.context, R.layout.item_document, null)
            .apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    DpPxUtil.toPx(100)
                )
            }
        val layoutParams = view.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin = DpPxUtil.toPx(8)
        layoutParams.bottomMargin = DpPxUtil.toPx(8)
        return DocHolder(view)
    }

    override fun onBindViewHolder(holder: DocHolder, position: Int) {
        val item = items[position]
        setImage(holder.image, item)

        holder.name.text = item.title
        val dateFormat = SimpleDateFormat("dd MMM yy")
        holder.information.text = "${item.ext.toUpperCase()} · " +
                "${StringConverter.convertFileSize(item.size)} · " +
                "${dateFormat.format(Date(item.date * 1000L))} "
        holder.editButton.setOnClickListener { listener.onEditButtonClick(it, item) }
        holder.itemView.setOnClickListener{ listener.onItemClick(item)}

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Item>){
        this.items = items
    }
    fun getItems(): List<Item>{
        return items
    }


    private fun setImage(image: ImageView, item: Item){
        when(item.type){
            1 -> image.setImageDrawable(App.appContext!!.resources
                .getDrawable(R.drawable.ic_placeholder_document_text_72, App.appContext!!.theme))
            2 -> image.setImageDrawable(App.appContext!!.resources
                .getDrawable(R.drawable.ic_placeholder_document_archive_72, App.appContext!!.theme))
            3, 4 -> Glide.with(App.appContext!!).load(item.url).centerCrop().into(image)

            5 -> image.setImageDrawable(App.appContext!!.resources
                .getDrawable(R.drawable.ic_placeholder_document_music_72, App.appContext!!.theme))
            6 -> image.setImageDrawable(App.appContext!!.resources
                .getDrawable(R.drawable.ic_placeholder_document_video_72, App.appContext!!.theme))
            7 -> image.setImageDrawable(App.appContext!!.resources
                .getDrawable(R.drawable.ic_placeholder_document_book_72, App.appContext!!.theme))
            8 -> image.setImageDrawable(App.appContext!!.resources
                .getDrawable(R.drawable.ic_placeholder_document_other_72, App.appContext!!.theme))
        }
    }

    class DocHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val v = itemView
        val image: ImageView = v.findViewById(R.id.imv_file_preview)
        val name: TextView = v.findViewById(R.id.tv_file_name)
        val information: TextView = v.findViewById(R.id.tv_file_information)
        val editButton: ImageView = v.findViewById(R.id.imv_edit_button)
    }

    interface ActionListener{
        fun onEditButtonClick(anchor: View, item: Item)
        fun onItemClick(item: Item)
    }
}