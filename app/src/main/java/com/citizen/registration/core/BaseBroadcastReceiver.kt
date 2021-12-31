package com.citizen.registration.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/6/2021 at 3:03 PM
 */
abstract class BaseBroadcastReceiver : BroadcastReceiver()
{
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {}
}