package com.citizen.registration.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.citizen.registration.R

private fun Activity.setToast(layout : View)
{
    val toast = Toast(this)
    toast.setGravity(Gravity.BOTTOM, 0, 20)
    toast.duration = Toast.LENGTH_LONG
    toast.view = layout
    toast.show()
}

/**
 * ...display success toast message
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun Activity.successToast(body : String)
{
    val inflater = this.layoutInflater
    val layout: View = inflater.inflate(R.layout.layout_toast_view, this.findViewById(R.id.toast_layout_root))

    val tvBody = layout.findViewById<TextView>(R.id.tv_message)
    val tvTitle = layout.findViewById<TextView>(R.id.tv_title)
    val ivIcon = layout.findViewById<ImageView>(R.id.iv_icon)
    tvTitle.setTextColor(this.resources.getColor(R.color.success_color))
    ivIcon.background = this.resources.getDrawable(R.drawable.bg_toast_icon)
    this.getDrawableBackgroundColor(ivIcon, R.color.success_bg_color)
    ivIcon.setImageDrawable(this.resources.getDrawable(R.drawable.ic_success_toast_icon))

    tvTitle.text = this.resources.getString(R.string.success)
    tvBody.text = body

    this.setToast(layout)
}

/**
 * ...display error toast message
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun Activity.errorToast(body : String)
{
    val inflater = this.layoutInflater
    val layout: View = inflater.inflate(R.layout.layout_toast_view, this.findViewById(R.id.toast_layout_root))

    val tvBody = layout.findViewById<TextView>(R.id.tv_message)
    val tvTitle = layout.findViewById<TextView>(R.id.tv_title)
    val ivIcon = layout.findViewById<ImageView>(R.id.iv_icon)
    tvTitle.setTextColor(this.resources.getColor(R.color.error_color))
    ivIcon.background = this.resources.getDrawable(R.drawable.bg_toast_icon)
    this.getDrawableBackgroundColor(ivIcon, R.color.error_bg_color)
    ivIcon.setImageDrawable(this.resources.getDrawable(R.drawable.ic_error_toast))

    tvTitle.text = this.resources.getString(R.string.error)
    tvBody.text = body

    this.setToast(layout)
}

/**
 * ...display information toast message
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun Activity.infoToast(body : String)
{
    val inflater = this.layoutInflater
    val layout: View = inflater.inflate(R.layout.layout_toast_view, this.findViewById(R.id.toast_layout_root))

    val tvBody = layout.findViewById<TextView>(R.id.tv_message)
    val tvTitle = layout.findViewById<TextView>(R.id.tv_title)
    val ivIcon = layout.findViewById<ImageView>(R.id.iv_icon)
    tvTitle.setTextColor(this.resources.getColor(R.color.info_color))
    ivIcon.background = this.resources.getDrawable(R.drawable.bg_toast_icon)
    this.getDrawableBackgroundColor(ivIcon, R.color.info_bg_color)
    ivIcon.setImageDrawable(this.resources.getDrawable(R.drawable.ic_info_toast))

    tvTitle.text = this.resources.getString(R.string.info)
    tvBody.text = body

    this.setToast(layout)
}

/**
 * ...display warning toast message
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun Activity.warningToast(body : String)
{
    val inflater = this.layoutInflater
    val layout: View = inflater.inflate(R.layout.layout_toast_view, this.findViewById(R.id.toast_layout_root))

    val tvBody = layout.findViewById<TextView>(R.id.tv_message)
    val tvTitle = layout.findViewById<TextView>(R.id.tv_title)
    val ivIcon = layout.findViewById<ImageView>(R.id.iv_icon)
    tvTitle.setTextColor(this.resources.getColor(R.color.warning_color))
    ivIcon.background = this.resources.getDrawable(R.drawable.bg_toast_icon)
    this.getDrawableBackgroundColor(ivIcon, R.color.warning_bg_color)
    ivIcon.setImageDrawable(this.resources.getDrawable(R.drawable.ic_warning_toast))

    tvTitle.text = this.resources.getString(R.string.warning)
    tvBody.text = body

    this.setToast(layout)
}