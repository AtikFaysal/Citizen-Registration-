package com.citizen.registration.ui.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.databinding.LayoutContactDetailsBinding
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlPhoneNumber
import com.citizen.registration.utils.*
import com.citizen.registration.utils.constants.ConstantItems
import com.citizen.registration.utils.constants.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment: BaseFragment<LayoutContactDetailsBinding>()
{
    private lateinit var binding: LayoutContactDetailsBinding
    private val viewModel : CitizenRegistrationViewModel by viewModels()
    private var isValidPhone = false

    override val layoutId: Int
        get() = R.layout.layout_contact_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = viewDataBinding
        binding.lifecycleOwner = this
        binding.contact = viewModel
        init()
        onDataChanged()
        onPhoneCheckObserver()
        onClickListener()
        holdingTypeObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
    }

    override fun init() {
        (activity as BaseActivity).showToolbar() //display toolbar
        (activity as BaseActivity).setToolbarTitle("যোগাযোগের ঠিকানা")
    }

    override fun onClickListener() {
        binding.btnPreview.setOnClickListener {
            if(onDataValidation() && isValidPhone)
                goToNextFragment(R.id.action_contactDetailsFragment_to_previewDataFragment, mRootView, null)
        }
    }

    private fun onDataChanged()
    {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if(it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })

        mlPhoneNumber.observe(viewLifecycleOwner, {
            if(it.phoneValidation())
                viewModel.checkPhoneNumberUseLimit()
        })
    }

    private fun holdingTypeObserver()
    {
        viewModel.holdingNoInfo.observe(viewLifecycleOwner, {
            if(it.isNotEmpty())
            {
                holdingTypeList.clear()
                holdingTypeList.addAll(it)
                holdingTypeList.add(0, ConstantItems.getEmptyHoldingType())
            }
        })
    }

    private fun onDataValidation() : Boolean
    {
        return when(viewModel.contactInfoValidation()) {
            FormErrors.INVALID_PHONE->{
                binding.etPhone.requestFocus()
                binding.etPhone.error = ErrorMessage.INVALID_PHONE
                false
            }
            else -> true
        }
    }

    private fun onPhoneCheckObserver()
    {
        viewModel.isLimitOver.observe(viewLifecycleOwner, {
            when (it.responseCode) {
                Constants.RESPONSE_SUCCESS_CODE -> {
                    binding.tvValid.visibility = GONE
                    isValidPhone = true
                }
                Constants.NETWORK_ERROR_CODE -> {
                    if(onDataValidation())handleNetworkError(mContext) { viewModel.checkPhoneNumberUseLimit() }
                    isValidPhone = false
                }
                else -> {
                    binding.tvValid.visibility = VISIBLE
                    isValidPhone = false
                }
            }
        })
    }
}