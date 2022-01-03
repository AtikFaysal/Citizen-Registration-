package com.citizen.registration.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.databinding.LayoutPreviewBinding
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlHoldingNo
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlHoldingType
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlIdentityType
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlIsSameAddress
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlParaBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlParaBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlParaEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlParaEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlPostOfficeBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlPostOfficeBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlPostOfficeEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlPostOfficeEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlRoadBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlRoadBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlRoadEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlRoadEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlWardBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlWardBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlWardEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlWardEnPre
import com.citizen.registration.utils.*
import com.citizen.registration.utils.ErrorMessage.Companion.REGISTRATION_SUCCESS
import com.citizen.registration.utils.constants.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/1/2022 at 11:11 AM
 */
@AndroidEntryPoint
class PreviewDataFragment : BaseFragment<LayoutPreviewBinding>()
{
    private lateinit var binding: LayoutPreviewBinding
    private val viewModel : CitizenRegistrationViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.layout_preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = viewDataBinding
        binding.lifecycleOwner = this
        binding.preview = viewModel
        init()
        onCitizenRegistration()
        onDataChanged()
        onClickListener()
        isPresentAndPermanentAddressSame()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
    }

    @SuppressLint("SetTextI18n")
    override fun init() {
        (activity as BaseActivity).showToolbar() //display toolbar
        (activity as BaseActivity).setToolbarTitle("তথ্য দেখুন")
        binding.tvHoldingNo.text = "${mlHoldingType.value.toString()}-${mlHoldingNo.value.toString()}"

        if(CitizenRegistrationViewModel.mlGender.value == "মহিলা" && CitizenRegistrationViewModel.mlMaritalStatus.value == "বিবাহিত")
        {
            binding.lnHusbandName.visibility = VISIBLE
            binding.lnFatherName.visibility = GONE
        } else {
            binding.lnHusbandName.visibility = GONE
            binding.lnFatherName.visibility = VISIBLE
        }

        if(mlIsSameAddress.value == "1") {
            binding.lnPermanentAddress.visibility = GONE
            binding.tvSameAddress.visibility = VISIBLE
        } else {
            binding.lnPermanentAddress.visibility = VISIBLE
            binding.tvSameAddress.visibility = GONE
        }

        if(mlIdentityType.value == "1") {
            binding.lnBirthReg.visibility = GONE
            binding.lnNid.visibility = VISIBLE
        }
        else {
            binding.lnBirthReg.visibility = VISIBLE
            binding.lnNid.visibility = GONE
        }
    }

    override fun onClickListener() {
        binding.btnSubmit.setOnClickListener {
            viewModel.citizenRegistration()
        }
    }

    private fun onDataChanged() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })
    }

    private fun isPresentAndPermanentAddressSame()
    {
        if(mlIsSameAddress.value == "1") {
            mlParaEnPer.value = mlParaEnPre.value
            mlParaBnPer.value = mlParaBnPre.value

            mlRoadEnPer.value = mlRoadEnPre.value
            mlRoadBnPer.value = mlRoadBnPre.value

            mlPostOfficeEnPer.value = mlPostOfficeEnPre.value
            mlPostOfficeBnPer.value = mlPostOfficeBnPre.value

            mlWardEnPer.value = mlWardEnPre.value
            mlWardBnPer.value = mlWardBnPre.value

            mlDivisionEnPer.value = mlDivisionEnPre.value
            mlDivisionBnPer.value = mlDivisionBnPre.value

            mlDistrictEnPer.value = mlDistrictEnPre.value
            mlDistrictBnPer.value = mlDistrictBnPre.value

            mlSubDistrictEnPer.value = mlSubDistrictEnPre.value
            mlSubDistrictBnPer.value = mlSubDistrictBnPre.value
        }
    }

    private fun onCitizenRegistration()
    {
        viewModel.isRegistered.observe(viewLifecycleOwner, {
            when (it.responseCode) {
                Constants.INSERT_SUCCESS_CODE -> {
                    Navigation.findNavController(mRootView).popBackStack(R.id.basicInfoFragment, true)
                    Navigation.findNavController(mRootView).popBackStack(R.id.addressFragment, true)
                    Navigation.findNavController(mRootView).popBackStack(R.id.contactDetailsFragment, true)
                    Navigation.findNavController(mRootView).popBackStack(R.id.previewDataFragment, true)
                    mActivity.successToast(REGISTRATION_SUCCESS)
                }
                Constants.NETWORK_ERROR_CODE -> {
                    handleNetworkError(mContext) { viewModel.citizenRegistration() }
                }

                else -> mActivity.warningToast(ErrorMessage.REGISTRATION_FAILED)
            }
        })
    }
}