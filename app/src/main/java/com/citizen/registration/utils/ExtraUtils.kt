package com.citizen.registration.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import androidx.navigation.Navigation
import com.citizen.registration.R
import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.data.response.LoginResponse
import com.google.gson.GsonBuilder


/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/30/2021 at 4:57 PM
 */

class ExtraUtils{
    companion object{
        fun errorResponse(it : Resource.Failure) : DefaultResponse
        {
            var errorResponse = DefaultResponse()
            val gson = GsonBuilder().create()
            try {
                it.errorBody?.let { error ->
                    val json = error.string()
                    errorResponse = gson.fromJson(json, DefaultResponse::class.java)
                }?: run {
                    errorResponse = DefaultResponse()
                    errorResponse.data = null
                    errorResponse.responseCode = it.statusCode
                    errorResponse.message = it.errorMessage.toString()
                }
            }catch (ex : Exception)
            {
                errorResponse = DefaultResponse()
                errorResponse.data = null
                errorResponse.responseCode = it.statusCode
                errorResponse.message = it.errorMessage.toString()
            }
            return errorResponse
        }

        fun invalidLogin(it : Resource.Failure) : LoginResponse
        {
            var errorResponse = LoginResponse()
            val gson = GsonBuilder().create()
            try {
                it.errorBody?.let { error ->
                    val json = error.string()
                    errorResponse = gson.fromJson(json, LoginResponse::class.java)
                }?: run {
                    errorResponse = LoginResponse()
                    errorResponse.user = null
                    errorResponse.responseCode = it.statusCode
                    errorResponse.message = it.errorMessage.toString()
                }
            }catch (ex : Exception)
            {
                errorResponse = LoginResponse()
                errorResponse.user = null
                errorResponse.responseCode = it.statusCode
                errorResponse.message = it.errorMessage.toString()
            }
            return errorResponse
        }
    }
}

/**
 * ...change drawable background color
 */
fun Activity.getDrawableBackgroundColor(mView: View, color: Int) {
    val gradientDrawable = mView.background as GradientDrawable
    gradientDrawable.setColor(this.resources.getColor(color))
    gradientDrawable.setStroke(0, this.resources.getColor(color))
    mView.invalidate()
}

/**
 * ...Customized snackBar view
 * ...used for no internet connection error
 * ...display snackBar
 * ...execute action while action is not null
 */
@SuppressLint("InflateParams")
fun View.snackBar(mContext : Context, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    val layout = snackBar.view as Snackbar.SnackbarLayout
    val inflater = LayoutInflater.from(mContext)
    val snackView = inflater.inflate(R.layout.layout_snackbar, null)
    layout.setPadding(0,0,0,0)
    layout.addView(snackView)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}

fun Fragment.handleNetworkError(
    mContext: Context,
    retry: (() -> Unit)? = null
) {
    requireView().snackBar(mContext, retry)
}

/**
 * ...start next fragment
 * ...passing value one fragment to next fragment
 * ...execute actionId wise
 */
fun goToNextFragment(actionId : Int, mView : View, bundle : Bundle?)
{
    bundle?.let {
        Navigation.findNavController(mView).navigate(actionId,bundle)
    }?: run {
        Navigation.findNavController(mView).navigate(actionId)
    }
}