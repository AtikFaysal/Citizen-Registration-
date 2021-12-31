package com.citizen.registration.utils

import android.content.Context
import com.citizen.registration.interfaces.LoadingConfig
import com.kaopiz.kprogresshud.KProgressHUD

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/20/2021 at 12:22 PM
 */

open class LoadingUtils(context: Context) : LoadingConfig
{
    private val progressDialog = KProgressHUD.create(context)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setLabel("Please wait")
        .setDetailsLabel("Loading...")
        .setAnimationSpeed(2)
        .setCancellable(true)
        .setDimAmount(0.5f)

    /**
     * @ Display progress dialog
     */
    override fun showProgressDialog() {
        progressDialog.show()
    }

    /**
     *
     * @ Dismiss progress dialog
     */
    override fun dismissProgressDialog() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
}