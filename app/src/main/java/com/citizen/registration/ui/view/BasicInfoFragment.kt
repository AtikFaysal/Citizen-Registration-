package com.citizen.registration.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.data.model.Items
import com.citizen.registration.databinding.LayoutBasicInfoBinding
import com.citizen.registration.interfaces.ItemSelectionListener
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlBirthRegNo
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlEducation
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlGender
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlHoldingType
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlIdentityType
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlLiveIn
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlMaritalStatus
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlNidNo
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlOccupation
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlReligion
import com.citizen.registration.utils.*
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_BIRTH_REG_NO
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_DOB
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_EDUCATION
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_FATHER_NAME_BN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_FATHER_NAME_EN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_GENDER
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_HOLDING_NO
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_HOLDING_TYPE
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_HUSBAND_NAME
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_IDENTITY
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_LIVE_IN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_MARITAL_STATUS
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_MOTHER_NAME_BN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_MOTHER_NAME_EN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_NAME_BN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_NAME_EN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_NID
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_OCCUPATION
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_RELIGION
import com.citizen.registration.utils.constants.AppConstants.Companion.EDUCATION_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.GENDER_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.HOLDING_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.LIVE_IN_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.MARITAL_STATUS_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.NID_OR_BIRTH_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.OCCUPATION_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.RELIGION_SELECTION
import com.citizen.registration.utils.constants.ConstantItems
import com.citizen.registration.utils.constants.ConstantItems.Companion.educationList
import com.citizen.registration.utils.constants.ConstantItems.Companion.gender
import com.citizen.registration.utils.constants.ConstantItems.Companion.identityType
import com.citizen.registration.utils.constants.ConstantItems.Companion.liveInType
import com.citizen.registration.utils.constants.ConstantItems.Companion.maritalStatus
import com.citizen.registration.utils.constants.ConstantItems.Companion.occupationList
import com.citizen.registration.utils.constants.ConstantItems.Companion.religionList
import com.citizen.registration.utils.constants.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasicInfoFragment : BaseFragment<LayoutBasicInfoBinding>()
{
    private var isIdentityNoValid = false
    private lateinit var binding: LayoutBasicInfoBinding
    private val viewModel : CitizenRegistrationViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.layout_basic_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = viewDataBinding
        binding.lifecycleOwner = this
        binding.basicInfo = viewModel
        init()
        onNidDuplicateObserver()
        onCitizenRegistration()
        onDataChanged()
        onClickListener()
        holdingTypeObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
        viewModel.clearUserInfo()
    }

    override fun init() {
        (activity as BaseActivity).showToolbar() //display toolbar
        (activity as BaseActivity).setToolbarTitle("নাগরিক পরিচয়")

        if(holdingTypeList.isEmpty())
            viewModel.getHoldingNoType()
        else mContext.selectItemFromSpinner(binding.spHoldingNoType, holdingTypeList, HOLDING_SELECTION,onItemSelection)

        mContext.selectItemFromSpinner(binding.spGender, gender, GENDER_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spMarital, maritalStatus,MARITAL_STATUS_SELECTION ,onItemSelection)
        mContext.selectItemFromSpinner(binding.spIdentityType, identityType, NID_OR_BIRTH_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spReligion, religionList, RELIGION_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spOccupation, occupationList, OCCUPATION_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spEducation, educationList, EDUCATION_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spLiveIn, liveInType, LIVE_IN_SELECTION,onItemSelection)
    }

    override fun onClickListener() {
        binding.btnNext.setOnClickListener {
            if(onDataValidation()){
                if(isIdentityNoValid)goToNextFragment(R.id.action_basicInfo_to_addressFragment, mRootView, null)
            }
        }

        binding.tvDob.setOnClickListener {
            mActivity.pickPreviousDate(binding.tvDob)
        }

        binding.tvScan.setOnClickListener {

        }
    }

    private fun onDataChanged()
    {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if(it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })

        mlIdentityType.observe(viewLifecycleOwner,{
            if(it == "1")
            {
                binding.lnNid.visibility = VISIBLE
                binding.lnBirthReg.visibility = GONE
                mlNidNo.value = ""
                mlBirthRegNo.value = ""
                binding.tvValid.visibility = GONE
            }else if(it == "2")
            {
                binding.lnNid.visibility = GONE
                binding.lnBirthReg.visibility = VISIBLE
                mlNidNo.value = ""
                mlBirthRegNo.value = ""
                binding.tvValid.visibility = GONE
            }
        })

        mlGender.observe(viewLifecycleOwner,{
            visibleHusbandNameField()
        })

        mlMaritalStatus.observe(viewLifecycleOwner,{
            visibleHusbandNameField()
        })

        mlNidNo.observe(viewLifecycleOwner,{
            if(it.length >= 10)
                viewModel.checkDuplicateIdentity()
            else {
                binding.tvValid.visibility = GONE
                isIdentityNoValid = false
            }
        })

        mlBirthRegNo.observe(viewLifecycleOwner,{
            if(it.length >= 10)
                viewModel.checkDuplicateIdentity()
            else {
                binding.tvValid.visibility = GONE
                isIdentityNoValid = false
            }
        })
    }

    private fun onNidDuplicateObserver()
    {
        viewModel.isDuplicateData.observe(viewLifecycleOwner, {
            when (it.responseCode) {
                Constants.RESPONSE_SUCCESS_CODE -> {
                    binding.tvValid.visibility = VISIBLE
                    binding.tvValid.text = mActivity.resources.getString(R.string.not_valid)
                    isIdentityNoValid = false
                }
                Constants.NETWORK_ERROR_CODE -> {
                    if(onDataValidation())handleNetworkError(mContext) { viewModel.checkDuplicateIdentity() }
                    isIdentityNoValid = false
                }

                Constants.RESPONSE_NOT_FOUND_CODE ->
                {
                    binding.tvValid.visibility = GONE
                    binding.tvValid.text = mActivity.resources.getString(R.string.valid)
                    isIdentityNoValid = true
                }

                else -> {
                    binding.tvValid.visibility = VISIBLE
                    binding.tvValid.text = mActivity.resources.getString(R.string.not_valid)
                    isIdentityNoValid = false
                }
            }
        })
    }

    private fun holdingTypeObserver()
    {
        viewModel.holdingNoInfo.observe(viewLifecycleOwner, {
            if(it.isNotEmpty())
            {
                holdingTypeList.clear()
                holdingTypeList.addAll(it)
                holdingTypeList.add(0,ConstantItems.getEmptyHoldingType())
                mContext.selectItemFromSpinner(binding.spHoldingNoType, holdingTypeList, HOLDING_SELECTION,onItemSelection)
            }
        })
    }

    private fun onDataValidation() : Boolean
    {
        when(viewModel.basicInfoValidation())
        {
            FormErrors.INVALID_HOLDING_TYPE -> {
                mActivity.warningToast(INVALID_HOLDING_TYPE)
                return false
            }
            FormErrors.INVALID_HOLDING_NO -> {
                binding.etHolingNo.requestFocus()
                binding.etHolingNo.error = INVALID_HOLDING_NO
                return false
            }
            FormErrors.INVALID_IDENTITY -> {
                mActivity.warningToast(INVALID_IDENTITY)
                return false
            }
            FormErrors.INVALID_NID_NO -> {
                binding.etNidNo.requestFocus()
                binding.etNidNo.error = INVALID_NID
                return false
            }
            FormErrors.INVALID_BIRTH_REG_NO -> {
                binding.etBirthRegNo.requestFocus()
                binding.etBirthRegNo.error = INVALID_BIRTH_REG_NO
                return false
            }
            FormErrors.INVALID_DOB -> {
                mActivity.warningToast(INVALID_DOB)
                return false
            }
            FormErrors.INVALID_NAME_EN -> {
                binding.etMyName.requestFocus()
                binding.etMyName.error = INVALID_NAME_EN
                return false
            }
            FormErrors.INVALID_NAME_BN -> {
                binding.etMyNameBn.requestFocus()
                binding.etMyNameBn.error = INVALID_NAME_BN
                return false
            }
            FormErrors.INVALID_FATHER_NAME_EN -> {
                binding.etFatherName.requestFocus()
                binding.etFatherName.error = INVALID_FATHER_NAME_EN
                return false
            }
            FormErrors.INVALID_FATHER_NAME_BN -> {
                binding.etFatherNameBn.requestFocus()
                binding.etFatherNameBn.error = INVALID_FATHER_NAME_BN
                return false
            }
            FormErrors.INVALID_MOTHER_NAME_EN -> {
                binding.etMotherName.requestFocus()
                binding.etMotherName.error = INVALID_MOTHER_NAME_EN
                return false
            }
            FormErrors.INVALID_MOTHER_NAME_BN -> {
                binding.etMotherNameBn.requestFocus()
                binding.etMotherNameBn.error = INVALID_MOTHER_NAME_BN
                return false
            }
            FormErrors.INVALID_OCCUPATION -> {
                mActivity.warningToast(INVALID_OCCUPATION)
                return false
            }
            FormErrors.INVALID_EDUCATION -> {
                mActivity.warningToast(INVALID_EDUCATION)
                return false
            }
            FormErrors.INVALID_RELIGION -> {
                mActivity.warningToast(INVALID_RELIGION)
                return false
            }
            FormErrors.INVALID_LIVE_IN -> {
                mActivity.warningToast(INVALID_LIVE_IN)
                return false
            }
            FormErrors.INVALID_GENDER -> {
                mActivity.warningToast(INVALID_GENDER)
                return false
            }
            FormErrors.INVALID_MARITAL_STATUS -> {
                mActivity.warningToast(INVALID_MARITAL_STATUS)
                return false
            }
            FormErrors.INVALID_HUSBAND_NAME_EN -> {
                binding.etHusbandName.requestFocus()
                binding.etHusbandName.error = INVALID_HUSBAND_NAME
                return false
            }
            FormErrors.INVALID_HUSBAND_NAME_BN -> {
                binding.etHusbandNameBn.requestFocus()
                binding.etHusbandNameBn.error = INVALID_HUSBAND_NAME
                return false
            }
            else -> return true
        }
    }

    private fun visibleHusbandNameField()
    {
        if(mlGender.value == "মহিলা" && mlMaritalStatus.value == "বিবাহিত")
        {
            binding.lnHusbandName.visibility = VISIBLE
            binding.lnFatherName.visibility = GONE
        } else {
            binding.lnHusbandName.visibility = GONE
            binding.lnFatherName.visibility = VISIBLE
        }
    }

    private var onItemSelection = object : ItemSelectionListener {
        override fun onItemSelected(code: Int?, item: Any) {
            code?.let {
                when(it)
                {
                    GENDER_SELECTION-> mlGender.value = item as String
                    HOLDING_SELECTION-> mlHoldingType.value = item as String
                    NID_OR_BIRTH_SELECTION-> mlIdentityType.value = (item as Items).id
                    OCCUPATION_SELECTION-> mlOccupation.value = (item as Items).title
                    EDUCATION_SELECTION-> mlEducation.value = (item as Items).title
                    RELIGION_SELECTION-> mlReligion.value = (item as Items).title
                    LIVE_IN_SELECTION-> mlLiveIn.value = (item as Items).title
                    MARITAL_STATUS_SELECTION-> mlMaritalStatus.value = (item as Items).title
                }
            }
        }
    }

    private fun onCitizenRegistration()
    {
        viewModel.isRegistered.observe(viewLifecycleOwner, {
            Log.d("responseCode", "${it.responseCode}")
            when (it.responseCode) {
                Constants.INSERT_SUCCESS_CODE -> {
                    mActivity.successToast("Success")
                }
                Constants.NETWORK_ERROR_CODE -> {
                    handleNetworkError(mContext) { viewModel.citizenRegistration() }
                }

                else -> mActivity.warningToast(ErrorMessage.REGISTRATION_FAILED)
            }
        })
    }
}