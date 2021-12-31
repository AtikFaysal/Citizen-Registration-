package com.citizen.registration.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizen.registration.core.BaseViewModel
import com.citizen.registration.data.response.LoginResponse
import com.citizen.registration.repository.UserRepository
import com.citizen.registration.utils.*
import com.citizen.registration.utils.ExtraUtils.Companion.invalidLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 1:02 PM
 */
@HiltViewModel
class AuthViewModel @Inject constructor(private val repository : UserRepository) : BaseViewModel()
{
    var mlDob = MutableLiveData<String>()
    var mlName = MutableLiveData<String>()
    var mlPhone = MutableLiveData<String>()
    var mlGender = MutableLiveData<String>()
    var mlPassword = MutableLiveData<String>()
    var mlConPassword = MutableLiveData<String>()

    private var _isLoginComplete = SingleLiveEvent<LoginResponse>()
    val isLoginComplete : SingleLiveEvent<LoginResponse> get() = _isLoginComplete

    init {
        job = Job()
    }

    /**
     * ...Validation of login form data
     * ...while all data are valid then return success
     * ...otherwise return error
     */
    fun loginDataValidator() :  FormErrors
    {
        if(!mlPhone.value.toString().phoneValidation()) return FormErrors.INVALID_PHONE
        if(!mlPassword.value.toString().passwordValidation()) return FormErrors.INVALID_PASSWORD

        return FormErrors.SUCCESS
    }

    /**
     * ...user login
     * ...user name as phone number and password must be valid
     */
    fun login()
    {
        job = viewModelScope.launch {
            isLoading.value = true
            repository.login(mlPhone.value.toString().modifyPhoneNumber(), mlPassword.value.toString()).let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _isLoginComplete.value = response
                        }
                    }
                    is Resource.Failure->{
                        _isLoginComplete.value = invalidLogin(it)
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
            isLoading.value = false
        }
    }
}