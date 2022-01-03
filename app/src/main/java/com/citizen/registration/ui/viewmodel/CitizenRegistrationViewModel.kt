package com.citizen.registration.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.citizen.registration.core.BaseFragment.Companion.districtList
import com.citizen.registration.core.BaseFragment.Companion.subDistrictList
import com.citizen.registration.core.BaseFragment.Companion.suggestionList
import com.citizen.registration.core.BaseViewModel
import com.citizen.registration.data.model.DistrictModel
import com.citizen.registration.data.model.HoldingTypeModel
import com.citizen.registration.data.model.SubDistrictModel
import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.database.room.entity.SuggestionEntity
import com.citizen.registration.repository.SuggestionRepository
import com.citizen.registration.repository.UserRepository
import com.citizen.registration.utils.*
import com.citizen.registration.utils.ExtraUtils.Companion.errorResponse
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION_BN
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION_BN_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.DISTRICT_SELECTION_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.PARA_SUGGESTION
import com.citizen.registration.utils.constants.AppConstants.Companion.POST_OFFICE_SUGGESTION
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION_BN
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION_BN_PERMANENT
import com.citizen.registration.utils.constants.AppConstants.Companion.SUB_DISTRICT_SELECTION_PERMANENT
import com.citizen.registration.utils.constants.ConstantItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitizenRegistrationViewModel @Inject constructor(private val repository : UserRepository, private val suggestionRepo : SuggestionRepository) : BaseViewModel()
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
        var mlHusbandNameEn = MutableLiveData<String>()
        var mlHusbandNameBn = MutableLiveData<String>()
        var mlOccupation = MutableLiveData<String>()
        var mlEducation = MutableLiveData<String>()
        var mlReligion = MutableLiveData<String>()
        var mlGender = MutableLiveData<String>()
        var mlMaritalStatus = MutableLiveData<String>()
        var mlLiveIn = MutableLiveData("স্থায়ী")

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

        var mlDivisionEnPerId = MutableLiveData<String>()
        var mlDivisionBnPerId = MutableLiveData<String>()
        var mlDivisionEnPreId = MutableLiveData<String>()
        var mlDivisionBnPreId = MutableLiveData<String>()

        var mlDistrictEnPer = MutableLiveData<String>()
        var mlDistrictBnPer = MutableLiveData<String>()
        var mlDistrictEnPre = MutableLiveData<String>()
        var mlDistrictBnPre = MutableLiveData<String>()

        var mlDistrictEnPerId = MutableLiveData<String>()
        var mlDistrictBnPerId = MutableLiveData<String>()
        var mlDistrictEnPreId = MutableLiveData<String>()
        var mlDistrictBnPreId = MutableLiveData<String>()

        var mlSubDistrictEnPer = MutableLiveData<String>()
        var mlSubDistrictBnPer = MutableLiveData<String>()
        var mlSubDistrictEnPre = MutableLiveData<String>()
        var mlSubDistrictBnPre = MutableLiveData<String>()

        var mlIsSameAddress = MutableLiveData("0")
    }

    private var _isDuplicateData = SingleLiveEvent<DefaultResponse>()
    val isDuplicateData : SingleLiveEvent<DefaultResponse> get() = _isDuplicateData

    private var _holdingNoInfo = MutableLiveData<List<HoldingTypeModel>>()
    val holdingNoInfo : MutableLiveData<List<HoldingTypeModel>> get() = _holdingNoInfo

    private var _placeInfo = SingleLiveEvent<DefaultResponse>()
    val placeInfo : SingleLiveEvent<DefaultResponse> get() = _placeInfo

    private var _isRegistered = SingleLiveEvent<DefaultResponse>()
    val isRegistered : SingleLiveEvent<DefaultResponse> get() = _isRegistered

    private var _isLimitOver = SingleLiveEvent<DefaultResponse>()
    val isLimitOver : SingleLiveEvent<DefaultResponse> get() = _isLimitOver

    init {
        job = Job()

        if(suggestionList.isEmpty())
            getSuggestion()
    }

    fun basicInfoValidation() : FormErrors
    {
        if(!mlHoldingType.value.toString().anyInputValidation()) return FormErrors.INVALID_HOLDING_TYPE
        if(!mlHoldingNo.value.toString().anyInputValidation()) return FormErrors.INVALID_HOLDING_NO
        //if(!mlBirthRegNo.value.toString().anyInputValidation() || mlBirthRegNo.value.toString().length < 10) return FormErrors.INVALID_BIRTH_REG_NO
        if(!mlIdentityType.value.toString().anyInputValidation()) return FormErrors.INVALID_IDENTITY
        else {
            if(mlIdentityType.value.toString() == "1")
                if(!mlNidNo.value.toString().anyInputValidation() || mlNidNo.value.toString().length < 10) return FormErrors.INVALID_NID_NO
            else if(mlIdentityType.value.toString() == "2")
                if(!mlBirthRegNo.value.toString().anyInputValidation() || mlBirthRegNo.value.toString().length < 10) return FormErrors.INVALID_BIRTH_REG_NO
        }
        if(!mlDob.value.toString().anyInputValidation()) return FormErrors.INVALID_DOB
        if(!mlNameEn.value.toString().nameValidation()) return FormErrors.INVALID_NAME_EN
        if(!mlNameBn.value.toString().anyInputValidation()) return FormErrors.INVALID_NAME_BN
        if(!mlMotherNameEn.value.toString().anyInputValidation()) return FormErrors.INVALID_MOTHER_NAME_EN
        if(!mlMotherNameBn.value.toString().anyInputValidation()) return FormErrors.INVALID_MOTHER_NAME_BN
        if(!mlOccupation.value.toString().anyInputValidation()) return FormErrors.INVALID_OCCUPATION
        if(!mlEducation.value.toString().anyInputValidation()) return FormErrors.INVALID_EDUCATION
        if(!mlReligion.value.toString().anyInputValidation()) return FormErrors.INVALID_RELIGION
        //if(!mlLiveIn.value.toString().anyInputValidation()) return FormErrors.INVALID_LIVE_IN
        if(!mlGender.value.toString().anyInputValidation()) return FormErrors.INVALID_GENDER
        if(!mlMaritalStatus.value.toString().anyInputValidation()) return FormErrors.INVALID_MARITAL_STATUS

        if(mlGender.value == "মহিলা" && mlMaritalStatus.value == "বিবাহিত"){
            if(!mlHusbandNameEn.value.toString().anyInputValidation()) return FormErrors.INVALID_HUSBAND_NAME_EN
            if(!mlHusbandNameBn.value.toString().anyInputValidation()) return FormErrors.INVALID_HUSBAND_NAME_BN
        }else {
            if(!mlFatherNameEn.value.toString().anyInputValidation()) return FormErrors.INVALID_FATHER_NAME_EN
            if(!mlFatherNameBn.value.toString().anyInputValidation()) return FormErrors.INVALID_FATHER_NAME_BN
        }

        return FormErrors.SUCCESS
    }

    fun addressDataValidation() : FormErrors
    {
        if(!mlParaEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_PARA_EN_PRE
        if(!mlParaBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_PARA_BN_PRE

//        if(!mlRoadEnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_EN_PRE
//        if(!mlRoadBnPre.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_BN_PRE

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

//            if(!mlRoadEnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_EN_PER
//            if(!mlRoadBnPer.value.toString().anyInputValidation()) return FormErrors.INVALID_ROAD_BN_PER

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
        if(!mlPhoneNumber.value.toString().phoneValidation()) return FormErrors.INVALID_PHONE

        return FormErrors.SUCCESS
    }

    /**
     * ...check nid or birth registration no is duplicate or not
     */
    fun checkDuplicateIdentity()
    {
        val identityNo = if(mlIdentityType.value.toString() == "1") mlNidNo.value.toString() else mlBirthRegNo.value.toString()
        job = viewModelScope.launch {
            repository.checkDuplicateIdentity(identityNo).let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _isDuplicateData.value = response
                        }
                    }
                    is Resource.Failure->{
                        _isDuplicateData.value = errorResponse(it)
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
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

    fun getPlaceInfo()
    {
        job = viewModelScope.launch {
            //isLoading.value = true
            repository.getPlaceInfo().let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _placeInfo.value = response
                        }
                    }
                    is Resource.Failure->{
                        _placeInfo.value = errorResponse(it)
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
            //isLoading.value = false
        }
    }

    fun getDistrict(selection : Int) : ArrayList<DistrictModel>
    {
        val districts = ArrayList<DistrictModel>()
        for(item in districtList)
        {
            if(selection == DISTRICT_SELECTION){
                if(mlDivisionEnPreId.value != null && item.divisionId == mlDivisionEnPreId.value.toString()) districts.add(item)
            }

            else if(selection == DISTRICT_SELECTION_BN) {
                if(mlDivisionEnPreId.value != null && item.divisionId == mlDivisionBnPreId.value.toString()) districts.add(item)
            }

            else if(selection == DISTRICT_SELECTION_PERMANENT) {
                if(mlDivisionEnPerId.value != null && item.divisionId == mlDivisionEnPerId.value.toString()) districts.add(item)
            }

            else if(selection == DISTRICT_SELECTION_BN_PERMANENT) {
                if(mlDivisionBnPerId.value != null && item.divisionId == mlDivisionBnPerId.value.toString()) districts.add(item)
            }
        }
        districts.add(0,ConstantItems.getEmptyDistrict())
        return districts
    }

    fun getSubDistrict(selection : Int) : ArrayList<SubDistrictModel>
    {
        val subDistricts = ArrayList<SubDistrictModel>()
        for(item in subDistrictList)
        {
            if(selection == SUB_DISTRICT_SELECTION) if(item.districtId == mlDistrictEnPreId.value.toString()) subDistricts.add(item)

            if(selection == SUB_DISTRICT_SELECTION_BN) if(item.districtId == mlDistrictBnPreId.value.toString()) subDistricts.add(item)

            if(selection == SUB_DISTRICT_SELECTION_PERMANENT) if(item.districtId == mlDistrictEnPerId.value.toString()) subDistricts.add(item)

            if(selection == SUB_DISTRICT_SELECTION_BN_PERMANENT) if(item.districtId == mlDistrictBnPerId.value.toString()) subDistricts.add(item)
        }
        subDistricts.add(0,ConstantItems.getEmptySubDistrict())
        return subDistricts
    }

    fun citizenRegistration()
    {
        job = viewModelScope.launch {
            isLoading.value = true
            repository.citizenRegistration(
                "", "", "", "", mlIsSameAddress.value.toString(), mlIdentityType.value.toString(), "", "${mlHoldingType.value.toString()}-${mlHoldingNo.value.toString()}",
                mlNidNo.value.toString(), mlBirthRegNo.value.toString(), mlPassportNo.value.toString(), mlDob.value.toString(), mlNameEn.value.toString(), mlNameBn.value.toString(), mlGender.value.toString(), mlMaritalStatus.value.toString(),
                mlFatherNameEn.value.toString(), mlFatherNameBn.value.toString(), mlMotherNameEn.value.toString(), mlMotherNameBn.value.toString(), mlOccupation.value.toString(), mlEducation.value.toString(), mlReligion.value.toString(), mlLiveIn.value.toString(),
                mlParaEnPre.value.toString(), mlParaBnPre.value.toString(), mlRoadEnPre.value.toString(), mlRoadBnPre.value.toString(), mlWardEnPre.value.toString(), mlWardBnPre.value.toString(), mlPostOfficeEnPre.value.toString(), mlPostOfficeBnPre.value.toString(),
                mlDivisionEnPre.value.toString(), mlDivisionBnPre.value.toString(), mlDistrictEnPre.value.toString(), mlDistrictBnPre.value.toString(), mlSubDistrictEnPre.value.toString(), mlSubDistrictBnPre.value.toString(),
                mlParaEnPer.value.toString(), mlParaBnPer.value.toString(), mlRoadEnPer.value.toString(), mlRoadBnPer.value.toString(), mlWardEnPer.value.toString(), mlWardBnPer.value.toString(), mlPostOfficeEnPer.value.toString(), mlPostOfficeBnPer.value.toString(),
                mlDivisionEnPer.value.toString(), mlDivisionBnPer.value.toString(), mlDistrictEnPer.value.toString(), mlDistrictBnPer.value.toString(), mlSubDistrictEnPer.value.toString(), mlSubDistrictBnPer.value.toString(),
                mlPhoneNumber.value.toString(), mlEmail.value.toString(), mlAttachmentEn.value.toString(), mlAttachmentBn.value.toString(), mlHusbandNameEn.value.toString(), mlHusbandNameBn.value.toString()
            ).let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _isRegistered.value = response
                        }
                    }
                    is Resource.Failure->{
                        _isRegistered.value = errorResponse(it)
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
            isLoading.value = false
        }
    }

    fun clearUserInfo()
    {
        mlIsSameAddress.value = ""
        mlIdentityType.value = ""
        mlHoldingNo.value = ""
        mlHoldingType.value = ""
        mlNidNo.value = ""
        mlBirthRegNo.value = ""
        mlPassportNo.value = ""
        mlDob.value = ""
        mlNameEn.value = ""
        mlNameBn.value = ""
        mlGender.value = ""
        mlMaritalStatus.value = ""
        mlFatherNameEn.value = ""
        mlFatherNameBn.value = ""
        mlMotherNameEn.value = ""
        mlMotherNameBn.value = ""
        mlOccupation.value = ""
        mlEducation.value = ""
        mlReligion.value = ""
        //mlLiveIn.value = ""
        mlParaEnPre.value = ""
        mlParaBnPre.value = ""
        mlRoadEnPre.value = ""
        mlRoadBnPre.value = ""
        mlWardEnPre.value = ""
        mlWardBnPre.value = ""
        mlPostOfficeEnPre.value = ""
        mlPostOfficeBnPre.value = ""
        mlParaEnPer.value = ""
        mlParaBnPer.value = ""
        mlRoadEnPer.value = ""
        mlRoadBnPer.value = ""
        mlWardEnPer.value = ""
        mlWardBnPer.value = ""
        mlPostOfficeEnPer.value = ""
        mlPostOfficeBnPer.value = ""
        mlDivisionEnPre.value = ""
        mlDivisionBnPre.value = ""
        mlDistrictEnPre.value = ""
        mlDistrictBnPre.value = ""
        mlSubDistrictEnPre.value = ""
        mlSubDistrictBnPre.value = ""
        mlDivisionEnPer.value = ""
        mlDivisionBnPer.value = ""
        mlDistrictEnPer.value = ""
        mlDistrictBnPer.value = ""
        mlSubDistrictEnPer.value = ""
        mlSubDistrictBnPer.value = ""
        mlPhoneNumber.value = ""
        mlEmail.value = ""
        mlAttachmentEn.value = ""
        mlAttachmentBn.value = ""
        mlHusbandNameEn.value = ""
        mlHusbandNameBn.value = ""
        suggestionList.clear()
    }

    fun suggestionData()
    {
        storeSuggestion(PARA_SUGGESTION, mlParaEnPer.value.toString())
        storeSuggestion(PARA_SUGGESTION, mlParaEnPre.value.toString())
        storeSuggestion(PARA_SUGGESTION, mlParaBnPre.value.toString())
        storeSuggestion(PARA_SUGGESTION, mlParaBnPer.value.toString())

        storeSuggestion(POST_OFFICE_SUGGESTION, mlPostOfficeEnPre.value.toString())
        storeSuggestion(POST_OFFICE_SUGGESTION, mlPostOfficeEnPer.value.toString())
        storeSuggestion(POST_OFFICE_SUGGESTION, mlPostOfficeBnPre.value.toString())
        storeSuggestion(POST_OFFICE_SUGGESTION, mlPostOfficeBnPer.value.toString())
    }

    private fun storeSuggestion(type : Int, suggestion : String)
    {
        val suggestionEntity = SuggestionEntity(0,suggestion,type)
        job = viewModelScope.launch {
            suggestionRepo.suggestionEntry(suggestionEntity)
        }
    }

    private fun getSuggestion()
    {
        job = viewModelScope.launch {
            suggestionList.clear()
            for(item in suggestionRepo.getSuggestion())
            {
                suggestionList.add(item.suggestion)
            }
        }
    }

    fun checkPhoneNumberUseLimit()
    {
        job = viewModelScope.launch {
            repository.checkPhoneNumberUseLimit(mlPhoneNumber.value.toString()).let{
                when(it)
                {
                    is Resource.Success-> {
                        it.value.let { response->
                            _isLimitOver.value = response
                        }
                    }
                    is Resource.Failure->{
                        _isLimitOver.value = errorResponse(it)
                        isNetworkError.value = it.isNetworkError
                    }
                }
            }
        }
    }
}