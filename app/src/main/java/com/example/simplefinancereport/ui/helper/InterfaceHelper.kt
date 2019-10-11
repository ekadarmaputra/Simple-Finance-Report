package com.example.simplefinancereport.ui.helper

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class InterfaceHelper {

    companion object Instance{

        private val instance = InterfaceHelper()

        @JvmStatic
        fun getInstance(): InterfaceHelper{
            return instance
        }

    }

    fun showConfirmationDialog(
        context: Context,
        message: String,
        positiveBtnClickListener: DialogInterface.OnClickListener
    ) {
        val alerBuilder = AlertDialog.Builder(context)
        alerBuilder.setMessage(message)
            .setPositiveButton("Ok", positiveBtnClickListener)
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        val alertDialog = alerBuilder.create()
        alertDialog.show()
    }

    fun showAlertDialog(context: Context, message: String) {
        val alerBuilder = AlertDialog.Builder(context)
        alerBuilder.setMessage(message)
            .setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        val alertDialog = alerBuilder.create()
        alertDialog.show()
    }

}