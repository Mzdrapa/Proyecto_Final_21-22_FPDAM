package com.ubedaPablo.proyecto.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ubedaPablo.proyecto.R
import com.ubedaPablo.proyecto.ui.characters.CharacterFragment

class EditDeleteDialog : DialogFragment() {

    private lateinit var listener: DialogListener

    interface DialogListener {
        fun onEditClick()
        fun onDeleteClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dialogTitle)
                .setItems(
                    arrayOf(getString(R.string.dialogEdit), getString(R.string.dialogDelete)),
                    DialogInterface.OnClickListener() { _, i ->
                        when (i) {
                            0 -> listener.onEditClick()
                            1 -> listener.onDeleteClick()
                        }
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setListener(characterFragment: CharacterFragment) {
        try {
            listener = characterFragment
        } catch (e: ClassCastException) {
            throw ClassCastException(
                ("$context must implement DialogListener")
            )
        }
    }
}