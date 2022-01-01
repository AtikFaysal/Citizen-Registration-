package com.citizen.registration.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizen.registration.core.BaseViewModel
import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.repository.UserRepository
import com.citizen.registration.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository : UserRepository) : BaseViewModel()
{
    var mlTotal = MutableLiveData<String>()
    var mlToday = MutableLiveData<String>()
    var mlLastWeek = MutableLiveData<String>()

    private var _reportData = SingleLiveEvent<DefaultResponse>()
    val reportData : SingleLiveEvent<DefaultResponse> get() = _reportData


    init {
        job = Job()
    }

    /**
     * ...get citizen registration report
     * ...will received today, last week and total number registered citizen
     */
    fun getReport()
    {
        job = viewModelScope.launch {
            isLoading.value = true
            repository.getDashboardReport().let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _reportData.value = response
                        }
                    }
                    is Resource.Failure->{
                        _reportData.value = ExtraUtils.errorResponse(it)
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
            isLoading.value = false
        }
    }
}