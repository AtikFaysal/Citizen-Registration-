package com.citizen.registration.ui.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.data.model.Items
import com.citizen.registration.database.SharedPreferenceManager
import com.citizen.registration.databinding.LayoutCitizenAddressBinding
import com.citizen.registration.interfaces.ItemSelectionListener
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlIsSameAddress
import com.citizen.registration.utils.*
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_PARA_EN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_POST_OFFICE_BN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_POST_OFFICE_EN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_ROAD_NO_BN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_ROAD_NO_EN
import com.citizen.registration.utils.constants.AppConstants
import com.citizen.registration.utils.constants.ConstantItems
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CitizenAddressFragment : BaseFragment<LayoutCitizenAddressBinding>()
{
    private lateinit var binding: LayoutCitizenAddressBinding
    private val viewModel : CitizenRegistrationViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.layout_citizen_address

    @Inject
    lateinit var prefManager : SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = viewDataBinding
        binding.lifecycleOwner = this
        binding.address = viewModel
        init()
        onDataChanged()
        onClickListener()
        holdingTypeObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.job.cancel()
    }

    override fun init() {
        (activity as BaseActivity).showToolbar() //display toolbar
        (activity as BaseActivity).setToolbarTitle("ঠিকানা")

        mContext.selectItemFromSpinner(binding.spWardEn, ConstantItems.wardNoListEn, AppConstants.WARD_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spWardBn, ConstantItems.wardNoListBn, AppConstants.WARD_SELECTION_BN,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDivisionEn, ConstantItems.divisionListEn, AppConstants.DIVISION_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDivisionBn, ConstantItems.divisionListBn, AppConstants.DIVISION_SELECTION_BN,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDistrictEn, ConstantItems.districtListEn, AppConstants.DISTRICT_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDistrictBn, ConstantItems.districtListBn, AppConstants.DISTRICT_SELECTION_BN,onItemSelection)
        mContext.selectItemFromSpinner(binding.spSubDistrictEn, ConstantItems.subDistrictListEn, AppConstants.SUB_DISTRICT_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spSubDistrictBn, ConstantItems.subDistrictListEn, AppConstants.SUB_DISTRICT_SELECTION_BN,onItemSelection)

        mContext.selectItemFromSpinner(binding.spWardEnPermanent, ConstantItems.wardNoListEn, AppConstants.WARD_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spWardBnPermanent, ConstantItems.wardNoListBn, AppConstants.WARD_SELECTION_BN_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDivisionEnPermanent, ConstantItems.divisionListEn, AppConstants.DIVISION_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDivisionBnPermanent, ConstantItems.divisionListBn, AppConstants.DIVISION_SELECTION_BN_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDistrictEnPermanent, ConstantItems.districtListEn, AppConstants.DISTRICT_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDistrictBnPermanent, ConstantItems.districtListBn, AppConstants.DISTRICT_SELECTION_BN_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spSubDistrictEnPermanent, ConstantItems.subDistrictListEn, AppConstants.SUB_DISTRICT_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spSubDistrictBnPermanent, ConstantItems.subDistrictListEn, AppConstants.SUB_DISTRICT_SELECTION_BN_PERMANENT,onItemSelection)
    }

    override fun onClickListener() {
        binding.cbSameAsPresent.setOnClickListener {
            if(binding.cbSameAsPresent.isChecked)
                mlIsSameAddress.value = "1"
            else mlIsSameAddress.value = "0"
        }

        binding.btnNext.setOnClickListener {
            goToNextFragment(R.id.action_address_to_contactDetailsFragment, mRootView, null)
        }
    }

    private fun onDataChanged()
    {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if(it) loadingUtils.showProgressDialog()
            else loadingUtils.dismissProgressDialog()
        })

        mlIsSameAddress.observe(viewLifecycleOwner,{
            if(it == "1")binding.lnPermanentAddress.visibility = GONE
            else binding.lnPermanentAddress.visibility = VISIBLE
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
        when(viewModel.addressDataValidation())
        {
            FormErrors.INVALID_PARA_EN_PRE->{
                binding.etParaEn.requestFocus()
                binding.etParaEn.error = INVALID_PARA_EN
                return false
            }
            FormErrors.INVALID_PARA_BN_PRE->{
                binding.etParaBn.requestFocus()
                binding.etParaBn.error = INVALID_PARA_EN
                return false
            }
            FormErrors.INVALID_PARA_EN_PER->{
                binding.etParaEnPermanent.requestFocus()
                binding.etParaEnPermanent.error = INVALID_PARA_EN
                return false
            }
            FormErrors.INVALID_PARA_BN_PER->{
                binding.etParaBnPermanent.requestFocus()
                binding.etParaBnPermanent.error = INVALID_PARA_EN
                return false
            }

            FormErrors.INVALID_ROAD_EN_PRE->{
                binding.etRoadEn.requestFocus()
                binding.etRoadEn.error = INVALID_ROAD_NO_EN
                return false
            }
            FormErrors.INVALID_ROAD_BN_PRE->{
                binding.etRoadBn.requestFocus()
                binding.etRoadBn.error = INVALID_ROAD_NO_BN
                return false
            }
            FormErrors.INVALID_ROAD_EN_PER->{
                binding.etRoadEnPermanent.requestFocus()
                binding.etRoadEnPermanent.error = INVALID_ROAD_NO_EN
                return false
            }
            FormErrors.INVALID_ROAD_BN_PER->{
                binding.etRoadBnPermanent.requestFocus()
                binding.etRoadBnPermanent.error = INVALID_ROAD_NO_BN
                return false
            }

            FormErrors.INVALID_POST_OFFICE_EN_PRE->{
                binding.etPostOfficeEn.requestFocus()
                binding.etPostOfficeEn.error = INVALID_POST_OFFICE_EN
                return false
            }
            FormErrors.INVALID_POST_OFFICE_BN_PRE->{
                binding.etPostOfficeBn.requestFocus()
                binding.etPostOfficeBn.error = INVALID_POST_OFFICE_BN
                return false
            }
            FormErrors.INVALID_POST_OFFICE_EN_PER->{
                binding.etPostOfficeEnPermanent.requestFocus()
                binding.etPostOfficeEnPermanent.error = INVALID_POST_OFFICE_EN
                return false
            }
            FormErrors.INVALID_POST_OFFICE_BN_PER->{
                binding.etPostOfficeBnPermanent.requestFocus()
                binding.etPostOfficeBnPermanent.error = INVALID_POST_OFFICE_BN
                return false
            }

            FormErrors.INVALID_DIVISION_EN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_DIVISION_EN)
                return false
            }
            FormErrors.INVALID_DIVISION_BN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_DIVISION_EN)
                return false
            }
            FormErrors.INVALID_DIVISION_EN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_DIVISION_EN)
                return false
            }
            FormErrors.INVALID_DIVISION_BN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_DIVISION_EN)
                return false
            }

            FormErrors.INVALID_DISTRICT_EN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_DISTRICT_EN)
                return false
            }
            FormErrors.INVALID_DISTRICT_BN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_DISTRICT_EN)
                return false
            }
            FormErrors.INVALID_DISTRICT_EN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_DISTRICT_EN)
                return false
            }
            FormErrors.INVALID_DISTRICT_BN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_DISTRICT_EN)
                return false
            }

            FormErrors.INVALID_SUB_DISTRICT_EN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_SUB_DISTRICT_BN)
                return false
            }
            FormErrors.INVALID_SUB_DISTRICT_BN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_SUB_DISTRICT_BN)
                return false
            }
            FormErrors.INVALID_SUB_DISTRICT_EN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_SUB_DISTRICT_BN)
                return false
            }
            FormErrors.INVALID_SUB_DISTRICT_BN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_SUB_DISTRICT_BN)
                return false
            }

            FormErrors.INVALID_WARD_EN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_WARD_NO_EN)
                return false
            }
            FormErrors.INVALID_WARD_BN_PRE->{
                mActivity.warningToast(ErrorMessage.INVALID_WARD_NO_EN)
                return false
            }
            FormErrors.INVALID_WARD_EN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_WARD_NO_EN)
                return false
            }
            FormErrors.INVALID_WARD_BN_PER->{
                mActivity.warningToast(ErrorMessage.INVALID_WARD_NO_EN)
                return false
            }
            else -> return true
        }
    }

    private var onItemSelection = object : ItemSelectionListener {
        override fun onItemSelected(code: Int?, item: Any) {
            code?.let {
                when(it)
                {
                    AppConstants.WARD_SELECTION -> CitizenRegistrationViewModel.mlWardEnPre.value = (item as Items).title
                    AppConstants.WARD_SELECTION_BN -> CitizenRegistrationViewModel.mlWardBnPre.value = (item as Items).title
                    AppConstants.WARD_SELECTION_PERMANENT -> CitizenRegistrationViewModel.mlWardEnPer.value = (item as Items).title
                    AppConstants.WARD_SELECTION_BN_PERMANENT -> CitizenRegistrationViewModel.mlWardBnPer.value = (item as Items).title

                    AppConstants.DIVISION_SELECTION -> CitizenRegistrationViewModel.mlDivisionEnPre.value = (item as Items).title
                    AppConstants.DIVISION_SELECTION_BN -> CitizenRegistrationViewModel.mlDivisionBnPre.value = (item as Items).title
                    AppConstants.DIVISION_SELECTION_PERMANENT -> CitizenRegistrationViewModel.mlDivisionEnPer.value = (item as Items).title
                    AppConstants.DIVISION_SELECTION_BN_PERMANENT -> CitizenRegistrationViewModel.mlDivisionBnPer.value = (item as Items).title

                    AppConstants.DISTRICT_SELECTION -> CitizenRegistrationViewModel.mlDistrictEnPre.value = (item as Items).title
                    AppConstants.DISTRICT_SELECTION_BN -> CitizenRegistrationViewModel.mlDistrictBnPre.value = (item as Items).title
                    AppConstants.DISTRICT_SELECTION_PERMANENT -> CitizenRegistrationViewModel.mlDistrictEnPer.value = (item as Items).title
                    AppConstants.DISTRICT_SELECTION_BN_PERMANENT -> CitizenRegistrationViewModel.mlDistrictBnPer.value = (item as Items).title

                    AppConstants.SUB_DISTRICT_SELECTION -> CitizenRegistrationViewModel.mlSubDistrictEnPre.value = (item as Items).title
                    AppConstants.SUB_DISTRICT_SELECTION_BN -> CitizenRegistrationViewModel.mlSubDistrictBnPre.value = (item as Items).title
                    AppConstants.SUB_DISTRICT_SELECTION_PERMANENT -> CitizenRegistrationViewModel.mlSubDistrictEnPer.value = (item as Items).title
                    AppConstants.SUB_DISTRICT_SELECTION_BN_PERMANENT -> CitizenRegistrationViewModel.mlSubDistrictBnPer.value = (item as Items).title

                }
            }
        }
    }
}