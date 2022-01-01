package com.citizen.registration.ui.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import com.citizen.registration.R
import com.citizen.registration.core.BaseActivity
import com.citizen.registration.core.BaseFragment
import com.citizen.registration.data.model.DistrictModel
import com.citizen.registration.data.model.DivisionModel
import com.citizen.registration.data.model.Items
import com.citizen.registration.data.model.SubDistrictModel
import com.citizen.registration.database.SharedPreferenceManager
import com.citizen.registration.databinding.LayoutCitizenAddressBinding
import com.citizen.registration.interfaces.ItemSelectionListener
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictBnPerId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictBnPreId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictEnPerId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDistrictEnPreId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionBnPerId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionBnPreId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionEnPerId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionEnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlDivisionEnPreId
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlIsSameAddress
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictBnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictBnPre
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictEnPer
import com.citizen.registration.ui.viewmodel.CitizenRegistrationViewModel.Companion.mlSubDistrictEnPre
import com.citizen.registration.utils.*
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_PARA_EN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_POST_OFFICE_BN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_POST_OFFICE_EN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_ROAD_NO_BN
import com.citizen.registration.utils.ErrorMessage.Companion.INVALID_ROAD_NO_EN
import com.citizen.registration.utils.constants.AppConstants
import com.citizen.registration.utils.constants.ConstantItems
import com.citizen.registration.utils.constants.ConstantItems.Companion.getEmptyDistrict
import com.citizen.registration.utils.constants.ConstantItems.Companion.getEmptyDivision
import com.citizen.registration.utils.constants.ConstantItems.Companion.getEmptySubDistrict
import com.citizen.registration.utils.constants.Constants.Companion.RESPONSE_SUCCESS_CODE
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

        if(districtList.isEmpty() || divisionList.isEmpty() || subDistrictList.isEmpty())
        {
            getPlaceData()
            viewModel.getPlaceInfo()
        }else
            displayPlaceListInSpinner()

        mContext.selectItemFromSpinner(binding.spWardEn, ConstantItems.wardNoListEn, AppConstants.WARD_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spWardBn, ConstantItems.wardNoListBn, AppConstants.WARD_SELECTION_BN,onItemSelection)
        mContext.selectItemFromSpinner(binding.spWardEnPermanent, ConstantItems.wardNoListEn, AppConstants.WARD_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spWardBnPermanent, ConstantItems.wardNoListBn, AppConstants.WARD_SELECTION_BN_PERMANENT,onItemSelection)
    }

    override fun onClickListener() {
        binding.cbSameAsPresent.setOnClickListener {
            if(binding.cbSameAsPresent.isChecked)
                mlIsSameAddress.value = "1"
            else mlIsSameAddress.value = "0"
        }

        binding.btnNext.setOnClickListener {
            if(onDataValidation())
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

    private fun getPlaceData()
    {
        viewModel.placeInfo.observe(viewLifecycleOwner, {
            if(it.responseCode == RESPONSE_SUCCESS_CODE)
            {
                divisionList.clear()
                divisionList.addAll(it.divisionList)
                divisionList.add(0, getEmptyDivision())

                districtList.clear()
                districtList.addAll(it.districtList)
                districtList.add(0, getEmptyDistrict())

                subDistrictList.clear()
                subDistrictList.addAll(it.subDistrictList)
                subDistrictList.add(0, getEmptySubDistrict())

                displayPlaceListInSpinner()
            }
        })
    }

    private fun displayPlaceListInSpinner()
    {
        mContext.selectItemFromSpinner(binding.spDivisionEn, divisionList, AppConstants.DIVISION_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDivisionBn, divisionList, AppConstants.DIVISION_SELECTION_BN,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDivisionEnPermanent,divisionList, AppConstants.DIVISION_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDivisionBnPermanent,divisionList, AppConstants.DIVISION_SELECTION_BN_PERMANENT,onItemSelection)

        mContext.selectItemFromSpinner(binding.spDistrictEn, viewModel.getDistrict(AppConstants.DISTRICT_SELECTION), AppConstants.DISTRICT_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDistrictBn, viewModel.getDistrict(AppConstants.DISTRICT_SELECTION_BN), AppConstants.DISTRICT_SELECTION_BN,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDistrictEnPermanent,viewModel.getDistrict(AppConstants.DISTRICT_SELECTION_PERMANENT), AppConstants.DISTRICT_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spDistrictBnPermanent, viewModel.getDistrict(AppConstants.DISTRICT_SELECTION_BN_PERMANENT), AppConstants.DISTRICT_SELECTION_BN_PERMANENT,onItemSelection)

        mContext.selectItemFromSpinner(binding.spSubDistrictEn, viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION), AppConstants.SUB_DISTRICT_SELECTION,onItemSelection)
        mContext.selectItemFromSpinner(binding.spSubDistrictBn,viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION_BN), AppConstants.SUB_DISTRICT_SELECTION_BN,onItemSelection)
        mContext.selectItemFromSpinner(binding.spSubDistrictEnPermanent,viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION_PERMANENT), AppConstants.SUB_DISTRICT_SELECTION_PERMANENT,onItemSelection)
        mContext.selectItemFromSpinner(binding.spSubDistrictBnPermanent, viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION_BN_PERMANENT), AppConstants.SUB_DISTRICT_SELECTION_BN_PERMANENT,onItemSelection)
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

                    AppConstants.DIVISION_SELECTION -> {
                        mlDivisionEnPre.value = (item as DivisionModel).divisionNameEn
                        mlDivisionEnPreId.value = item.id
                        mlDistrictEnPre.value = ""
                        mContext.selectItemFromSpinner(binding.spDistrictEn, viewModel.getDistrict(AppConstants.DISTRICT_SELECTION), AppConstants.DISTRICT_SELECTION,this)
                    }
                    AppConstants.DIVISION_SELECTION_BN -> {
                        mlDivisionBnPre.value = (item as DivisionModel).divisionNameBn
                        mlDivisionBnPreId.value = item.id
                        mlDistrictBnPre.value = ""
                        mContext.selectItemFromSpinner(binding.spDistrictBn, viewModel.getDistrict(AppConstants.DISTRICT_SELECTION_BN), AppConstants.DISTRICT_SELECTION_BN,this)
                    }
                    AppConstants.DIVISION_SELECTION_PERMANENT -> {
                        mlDivisionEnPer.value = (item as DivisionModel).divisionNameEn
                        mlDivisionEnPerId.value = item.id
                        mlDistrictEnPer.value = ""
                        mContext.selectItemFromSpinner(binding.spDistrictEnPermanent,viewModel.getDistrict(AppConstants.DISTRICT_SELECTION_PERMANENT), AppConstants.DISTRICT_SELECTION_PERMANENT,this)
                    }
                    AppConstants.DIVISION_SELECTION_BN_PERMANENT -> {
                        mlDivisionBnPer.value = (item as DivisionModel).divisionNameBn
                        mlDivisionBnPerId.value = item.id
                        mlDistrictBnPer.value = ""
                        mContext.selectItemFromSpinner(binding.spDistrictBnPermanent, viewModel.getDistrict(AppConstants.DISTRICT_SELECTION_BN_PERMANENT), AppConstants.DISTRICT_SELECTION_BN_PERMANENT,this)
                    }

                    AppConstants.DISTRICT_SELECTION -> {
                        mlDistrictEnPre.value = (item as DistrictModel).districtNameEn
                        mlDistrictEnPreId.value = item.id
                        mlSubDistrictEnPre.value = ""
                        mContext.selectItemFromSpinner(binding.spSubDistrictEn, viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION), AppConstants.SUB_DISTRICT_SELECTION,this)
                    }
                    AppConstants.DISTRICT_SELECTION_BN -> {
                        mlDistrictBnPre.value = (item as DistrictModel).districtNameBn
                        mlDistrictBnPreId.value = item.id
                        mlSubDistrictBnPre.value = ""
                        mContext.selectItemFromSpinner(binding.spSubDistrictBn,viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION_BN), AppConstants.SUB_DISTRICT_SELECTION_BN,this)
                    }
                    AppConstants.DISTRICT_SELECTION_PERMANENT -> {
                        mlDistrictEnPer.value = (item as DistrictModel).districtNameEn
                        mlDistrictEnPerId.value = item.id
                        mlSubDistrictEnPer.value = ""
                        mContext.selectItemFromSpinner(binding.spSubDistrictEnPermanent,viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION_PERMANENT), AppConstants.SUB_DISTRICT_SELECTION_PERMANENT,this)
                    }
                    AppConstants.DISTRICT_SELECTION_BN_PERMANENT -> {
                        mlDistrictBnPer.value = (item as DistrictModel).districtNameBn
                        mlDistrictBnPerId.value = item.id
                        mlSubDistrictBnPer.value = ""
                        mContext.selectItemFromSpinner(binding.spSubDistrictBnPermanent, viewModel.getSubDistrict(AppConstants.SUB_DISTRICT_SELECTION_BN_PERMANENT), AppConstants.SUB_DISTRICT_SELECTION_BN_PERMANENT,this)
                    }

                    AppConstants.SUB_DISTRICT_SELECTION -> mlSubDistrictEnPre.value = (item as SubDistrictModel).subDistrictNameEn
                    AppConstants.SUB_DISTRICT_SELECTION_BN -> mlSubDistrictBnPre.value = (item as SubDistrictModel).subDistrictNameBn
                    AppConstants.SUB_DISTRICT_SELECTION_PERMANENT -> mlSubDistrictEnPer.value = (item as SubDistrictModel).subDistrictNameEn
                    AppConstants.SUB_DISTRICT_SELECTION_BN_PERMANENT -> mlSubDistrictBnPer.value = (item as SubDistrictModel).subDistrictNameBn
                }
            }
        }
    }
}