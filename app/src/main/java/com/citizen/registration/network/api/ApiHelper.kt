package com.citizen.registration.network.api

import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.data.response.LoginResponse


/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/1/2021 at 11:01 AM
 */
interface ApiHelper
{
    suspend fun login(userName : String, password : String): LoginResponse

    suspend fun getDashboardReport(): DefaultResponse

    suspend fun getHoldingType(): DefaultResponse

    suspend fun checkDuplicateIdentity(identityNo : String): DefaultResponse

    suspend fun getPlaceInfo(): DefaultResponse

    suspend fun citizenRegistration(
        trackingNumber : String, userId : String,subUserId : String,userName : String,sameAsAddress : String,
        identityType : String, picture : String,holdingNo : String,nationalId : String,birthReg : String,
        passport : String,dateOfBirth : String,englishName : String,banglaName : String,gender : String,
        maritalStatus : String, enFatherName : String,bnFatherName : String,enMotherName : String,
        bnMotherName : String, occupation : String,education : String,religion : String,liveIn : String,
        pEnAddress : String,pBnAddress : String,pEnBlock : String,pBnBlock : String,pEnWord : String,
        pBnWord : String, pEnPost : String, pBnPost : String, pEnDivision : String, pBnDivision : String,
        pEnDistrict : String, pBnDistrict : String,pEnSubDistrict : String,pBnSubDistrict : String,
        perEnAddress : String,perBnAddress : String,perEnBlock : String,
        perBnBlock : String, perEnWord : String,perBnWord : String, perEnPost : String,
        perBnPost : String,perEnDivision : String, perBnDivision : String,perEnDistrict : String,
        perBnDistrict : String, perEnSubDistrict : String,perBnSubDistrict : String,
        mobile : String, mail : String,enAttachment : String, bnAttachment : String, husbandNameEn : String, husbandNameBn : String
    ): DefaultResponse
}