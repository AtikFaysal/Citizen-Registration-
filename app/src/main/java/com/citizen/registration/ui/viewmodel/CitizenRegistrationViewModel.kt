package com.citizen.registration.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizen.registration.core.BaseViewModel
import com.citizen.registration.data.model.HoldingTypeModel
import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.repository.UserRepository
import com.citizen.registration.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitizenRegistrationViewModel @Inject constructor(private val repository : UserRepository) : BaseViewModel()
{
    companion object{
        var mlHoldingType = MutableLiveData<String>()
        var mlHoldingNo = MutableLiveData<String>()
        var mlIdentityType = MutableLiveData<String>()
        var mlNidNo = MutableLiveData<String>()
        var mlBirthRegNo = MutableLiveData<String>()
        var mlPassportNo = MutableLiveData<String>()
        var mlDob = MutableLiveData<String>()
        var mlNameEn = MutableLiveData<String>()
        var mlNameBn = MutableLiveData<String>()
        var mlFatherNameEn = MutableLiveData<String>()
        var mlFatherNameBn = MutableLiveData<String>()
        var mlMotherNameEn = MutableLiveData<String>()
        var mlMotherNameBn = MutableLiveData<String>()
        var mlOccupation = MutableLiveData<String>()
        var mlEducation = MutableLiveData<String>()
        var mlReligion = MutableLiveData<String>()
        var mlGender = MutableLiveData<String>()
        var mlMaritalStatus = MutableLiveData<String>()
        var mlLiveIn = MutableLiveData<String>()

        var mlEmail = MutableLiveData<String>()
        var mlAttachmentEn = MutableLiveData<String>()
        var mlAttachmentBn = MutableLiveData<String>()
        var mlPhoneNumber = MutableLiveData<String>()

        var mlParaEnPer = MutableLiveData<String>()
        var mlParaBnPer = MutableLiveData<String>()
        var mlParaEnPre = MutableLiveData<String>()
        var mlParaBnPre = MutableLiveData<String>()

        var mlRoadEnPer = MutableLiveData<String>()
        var mlRoadBnPer = MutableLiveData<String>()
        var mlRoadEnPre = MutableLiveData<String>()
        var mlRoadBnPre = MutableLiveData<String>()

        var mlWardEnPer = MutableLiveData<String>()
        var mlWardBnPer = MutableLiveData<String>()
        var mlWardEnPre = MutableLiveData<String>()
        var mlWardBnPre = MutableLiveData<String>()

        var mlPostOfficeEnPer = MutableLiveData<String>()
        var mlPostOfficeBnPer = MutableLiveData<String>()
        var mlPostOfficeEnPre = MutableLiveData<String>()
        var mlPostOfficeBnPre = MutableLiveData<String>()

        var mlDivisionEnPer = MutableLiveData<String>()
        var mlDivisionBnPer = MutableLiveData<String>()
        var mlDivisionEnPre = MutableLiveData<String>()
        var mlDivisionBnPre = MutableLiveData<String>()

        var mlDistrictEnPer = MutableLiveData<String>()
        var mlDistrictBnPer = MutableLiveData<String>()
        var mlDistrictEnPre = MutableLiveData<String>()
        var mlDistrictBnPre = MutableLiveData<String>()

        var mlSubDistrictEnPer = MutableLiveData<String>()
        var mlSubDistrictBnPer = MutableLiveData<String>()
        var mlSubDistrictEnPre = MutableLiveData<String>()
        var mlSubDistrictBnPre = MutableLiveData<String>()

        var mlIsSameAddress = MutableLiveData("0")
    }

    private var _reportData = SingleLiveEvent<DefaultResponse>()
    val reportData : SingleLiveEvent<DefaultResponse> get() = _reportData

    private var _isDuplicateData = SingleLiveEvent<DefaultResponse>()
    val isDuplicateData : SingleLiveEvent<DefaultResponse> get() = _isDuplicateData

    private var _holdingNoInfo = MutableLiveData<List<HoldingTypeModel>>()
    val holdingNoInfo : MutableLiveData<List<HoldingTypeModel>> get() = _holdingNoInfo

    init {
        job = Job()
    }

    fun basicInfoValidation() : FormErrors
    {
        if(!mlHoldingType.value.toString().anyInputValidation()) return FormErrors.INVALID_HOLDING_TYPE
        if(!mlHoldingNo.value.toString().anyInputValidation()) return FormErrors.INVALID_HOLDING_NO
        if(!mlIdentityType.value.toString().anyInputValidation()) return FormErrors.INVALID_IDENTITY
        else {
            if(mlIdentityType.value.toString() == "1")
                if(!mlNidNo.value.toString().anyInputValidation()) return FormErrors.INVALID_NID_NO
            else if(mlIdentityType.value.toString() == "2")
                if(!mlBirthRegNo.value.toString().anyInputValidation()) return FormErrors.INVALID_BIRTH_REG_NO
        }
        if(!mlDob.value.toString().anyInputValidation()) return FormErrors.INVALID_DOB
        if(!mlNameEn.value.toString().nameValidation()) return FormErrors.INVALID_NAME_EN
        if(!mlNameBn.value.toString().anyInputValidation()) return FormErrors.INVALID_NAME_BN
        if(!mlFatherNameEn.value.toString().anyInputValidation()) return FormErrors.INVALID_FATHER_NAME_EN
        if(!mlFatherNameBn.value.toString().anyInputValidation()) return FormErrors.INVALID_FATHER_NAME_BN
        if(!mlMotherNameEn.value.toString().anyInputValidation()) return FormErrors.INVALID_MOTHER_NAME_EN
        if(!mlMotherNameBn.value.toString().anyInputValidation()) return FormErrors.INVALID_MOTHER_NAME_BN
        if(!mlOccupation.value.toString().anyInputValidation()) return FormErrors.INVALID_OCCUPATION
        if(!mlEducation.value.toString().anyInputValidation()) return FormErrors.INVALID_EDUCATION
        if(!mlReligion.value.toString().anyInputValidation()) return FormErrors.INVALID_RELIGION
        if(!mlLiveIn.value.toString().anyInputValidation()) return FormErrors.INVALID_LIVE_IN
        if(!mlGender.value.toString().anyInputValidation()) return FormErrors.INVALID_GENDER
        if(!mlMaritalStatus.value.toString().anyInputValidation()) return FormErrors.INVALID_MARITAL_STATUS

        return FormErrors.SUCCESS
    }

    fun addressDataValidation() : FormErrors
    {
        if(!mlParaEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_PARA_EN_PRE
        if(!mlParaBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_PARA_BN_PRE

        if(!mlRoadEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_EN_PRE
        if(!mlRoadBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_BN_PRE

        if(!mlWardEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_WARD_EN_PRE
        if(!mlWardBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_WARD_BN_PRE

        if(!mlPostOfficeEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_POST_OFFICE_EN_PRE
        if(!mlPostOfficeBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_POST_OFFICE_BN_PRE

        if(!mlDivisionEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_DIVISION_EN_PRE
        if(!mlDivisionBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_DIVISION_BN_PRE

        if(!mlDistrictEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_DISTRICT_EN_PRE
        if(!mlDistrictBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_DISTRICT_BN_PRE

        if(!mlSubDistrictEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_SUB_DISTRICT_EN_PRE
        if(!mlSubDistrictBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_SUB_DISTRICT_BN_PRE

        if(mlIsSameAddress.value == "0")
        {
            if(!mlParaEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_PARA_EN_PER
            if(!mlParaBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_PARA_BN_PER

            if(!mlRoadEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_EN_PER
            if(!mlRoadBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_BN_PER

            if(!mlWardEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_WARD_EN_PER
            if(!mlWardBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_WARD_BN_PER

            if(!mlPostOfficeEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_POST_OFFICE_EN_PER
            if(!mlPostOfficeBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_POST_OFFICE_BN_PER

            if(!mlDivisionEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_DIVISION_EN_PER
            if(!mlDivisionBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_DIVISION_BN_PER

            if(!mlDistrictEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_DISTRICT_EN_PER
            if(!mlDistrictBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_DISTRICT_BN_PER

            if(!mlSubDistrictEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_SUB_DISTRICT_EN_PER
            if(!mlSubDistrictBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_SUB_DISTRICT_BN_PER
        }

        return FormErrors.SUCCESS
    }

    fun contactInfoValidation() : FormErrors
    {
        if(!mlPhoneNumber.value.toString().anyInputValidation()) return FormErrors.INVALID_PHONE

        return FormErrors.SUCCESS
    }

    /**
     * ...check nid or birth registration no is duplicate or not
     */
    fun checkDuplicateIdentity()
    {
        val identityNo = if(mlIdentityType.value.toString() == "1") mlNidNo.value.toString() else mlBirthRegNo.value.toString()
        job = viewModelScope.launch {
            isLoading.value = true
            repository.checkDuplicateIdentity(identityNo).let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _isDuplicateData.value = response
                        }
                    }
                    is Resource.Failure->{
                        _isDuplicateData.value = ExtraUtils.errorResponse(it)
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
            isLoading.value = false
        }
    }

    /**
     * ...get holding no type data
     */
    fun getHoldingNoType()
    {
        job = viewModelScope.launch {
            isLoading.value = true
            repository.getHoldingType().let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _holdingNoInfo.value = response.holdingList
                        }
                    }
                    is Resource.Failure->{
                        _holdingNoInfo.value = ArrayList()
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
            isLoading.value = false
        }
    }
}