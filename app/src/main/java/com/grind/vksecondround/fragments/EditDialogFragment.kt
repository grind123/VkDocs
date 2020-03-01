package com.grind.vksecondround.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.grind.vksecondround.R

class EditDialogFragment(private val fileName: String,
                         private val nameListener: OnEditNameListener): DialogFragment() {

    private lateinit var name: EditText
    private lateinit var renameButton: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = View.inflate(context, R.layout.dialog_fragment_edit, null)
        name = v.findViewById(R.id.et_name)
        renameButton = v.findViewById(R.id.tv_rename)

        name.setText(fileName)
        renameButton.setOnClickListener { nameListener.saveNewFileName(name.text.toString()) }
        val dialog = AlertDialog.Builder(context!!)
            .setView(v)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context!!, android.R.color.transparent)))
        return dialog
    }

    override fun onResume() {
        super.onResume()
        name.requestFocus()
        name.setSelection(0, fileName.lastIndexOf('.'))

        //TODO: need force open keyboard

    }

    interface OnEditNameListener{
        fun saveNewFileName(newFileName: String)
    }
}