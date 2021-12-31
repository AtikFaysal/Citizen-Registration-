package com.citizen.registration.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.database.SharedPreferenceManager
import com.citizen.registration.databinding.LayoutLoginBinding
import com.citizen.registration.ui.viewmodel.AuthViewModel
import com.citizen.registration.utils.*
import com.citizen.registration.utils.ErrorMessage.Companion.LOGIN_FAILED
import com.citizen.registration.utils.constants.Constants.Companion.NETWORK_ERROR_CODE
import com.citizen.registration.utils.constants.Constants.Companion.RESPONSE_SUCCESS_CODE
import com.citizen.registration.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 1:01 PM
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment<LayoutLoginBinding>()
{
    private lateinit var binding: LayoutLoginBinding
    private val viewModel : AuthViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.layout_login

    @Inject lateinit var prefManager : SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = viewDataBinding
        binding.lifecycleOwner = this
        binding.login = viewModel
        init()
        onLoginObserver()
        onDataChanged()
        onClickListener()
    }

    override fun onStart() {
        super.onStart()
        if(prefManager.getLoginStatus())goToNextFragment(R.id.action_loginFragment_to_dashboard_fragment, mRootView, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
    }

    override fun init() {
        (activity as BaseActivity).hideToolbar() //hide toolbar
        binding.cbRememberMe.isChecked = prefManager.isRemembered()
        if(prefManager.isRemembered())
        {
            viewModel.mlPhone.value = prefManager.getRememberedUserName()
            viewModel.mlPassword.value = prefManager.getRememberedPassword()
        }
    }

    override fun onClickListener() {
        binding.btnLogin.setOnClickListener {
            if(setErrorMessage()) viewModel.login()
        }

        binding.cbRememberMe.setOnClickListener {
            if(binding.cbRememberMe.isChecked)
            {
                if(setErrorMessage())prefManager.setRememberMe(true, viewModel.mlPhone.value.toString().modifyPhoneNumber(), viewModel.mlPassword.value.toString())
                else binding.cbRememberMe.isChecked = false
            } else prefManager.setRememberMe(false, "", "")
        }
    }

    private fun onDataChanged()
    {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if(it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })
    }

    /**
     * ...show error message on any required field not valid
     */
    private fun setErrorMessage() : Boolean
    {
        when(viewModel.loginDataValidator())
        {
            FormErrors.INVALID_PHONE->{
                binding.etPhoneNumber.error = ErrorMessage.INVALID_PHONE
                binding.etPhoneNumber.requestFocus()
                return false
            }
            FormErrors.INVALID_PASSWORD->{
                binding.etPassword.error = ErrorMessage.INVALID_PASSWORD
                binding.etPassword.requestFocus()
                return false
            }
            FormErrors.SUCCESS-> return true

            else -> return true
        }
    }

    private fun onLoginObserver()
    {
        viewModel.isLoginComplete.observe(viewLifecycleOwner, {
            when (it.responseCode) {
                RESPONSE_SUCCESS_CODE -> {
                    it.user?.let { data ->
                        prefManager.setLoginStatus(true)
                        prefManager.setAccessToken(it.accessToken)
                        prefManager.setUserInfo(data)
                        goToNextFragment(R.id.action_loginFragment_to_dashboard_fragment, mRootView, null)
                    }?:run {
                        mActivity.errorToast(LOGIN_FAILED)
                    }
                }
                NETWORK_ERROR_CODE -> {
                    if(setErrorMessage())handleNetworkError(mContext) { viewModel.login() }
                }
                else -> mActivity.errorToast(LOGIN_FAILED)
            }
        })
    }
}