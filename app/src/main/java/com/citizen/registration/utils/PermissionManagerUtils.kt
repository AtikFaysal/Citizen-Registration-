package com.citizen.registration.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

/**
 * ...check phone call permission is enable or disable
 * ...return true while permission is granted
 * ...otherwise return false
 */
fun Context.isCallPhonePermissionGranted() = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

/**
 * ...check storage read and write permission is enable or disable
 * ...return true while permission is granted
 * ...otherwise return false
 */
fun Context.isStorageReadWritePermissionGranted() = (
        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

/**
 * -------- Phone call -----------
 * ...Request for phone call permission
 */
fun Activity.requestForPhoneCallPermission(permissionListener : PermissionListener)
{
    Dexter.withActivity(this)
        .withPermission(Manifest.permission.CALL_PHONE)
        .withListener(permissionListener)
        .withErrorListener { error -> this.errorToast(error.toString()) }
        .check()
}

/**
 * -------- READ EXTERNAL STORAGE -----------
 *  Request for read and write external storage permission to pick images from gallery
 */
fun Activity.requestForReadWriteExternalStoragePermission(permissionListener : MultiplePermissionsListener)
{
    Dexter.withActivity(this)
        .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,)
        .withListener(permissionListener)
        .withErrorListener { error -> this.errorToast(error.toString()) }
        .check()
}