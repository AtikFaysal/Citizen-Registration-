package com.citizen.registration.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:54 PM
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope
{
    var isLoading = MutableLiveData(false)
    var isNetworkError = MutableLiveData(false)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var job: Job
}