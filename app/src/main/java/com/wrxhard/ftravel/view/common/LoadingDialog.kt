package com.wrxhard.ftravel.view.common

import android.app.Activity
import android.app.AlertDialog
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import com.wrxhard.ftravel.R

class LoadingDialog(private val activity: Activity) {
    private var alertDialog: AlertDialog? = null
    private val handler: Handler = Handler()

    fun startLoading() {
        val builder = AlertDialog.Builder(activity, R.style.loadingDialogStyle)
        val view: View = LayoutInflater.from(activity).inflate(R.layout.loading_layout, null, false)
        builder.setView(view)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog?.show()
    }

    fun stopLoading() {
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
    }

    fun destroyDialog() {
        if (alertDialog != null) {
            alertDialog!!.dismiss()
        }
    }
}
