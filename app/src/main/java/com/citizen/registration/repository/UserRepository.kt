package com.citizen.registration.repository

import com.citizen.registration.interfaces.SafeApiCall
import com.citizen.registration.network.api.ApiHelper
import javax.inject.Inject

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 11/18/2021 at 12:59 PM
 */
class UserRepository @Inject constructor(private val apiHelper : ApiHelper) : SafeApiCall
{
    suspend fun login(userName : String, password : String) = safeApiCall {
        apiHelper.login(userName, password)
    }

    suspend fun getDashboardReport() = safeApiCall {
        apiHelper.getDashboardReport()
    }

    suspend fun getHoldingType() = safeApiCall {
        apiHelper.getHoldingType()
    }

    suspend fun checkDuplicateIdentity(identityNo : String) = safeApiCall {
        apiHelper.checkDuplicateIdentity(identityNo)
    }

    suspend fun getPlaceInfo() = safeApiCall {
        apiHelper.getPlaceInfo()
    }

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
                                      mobile : String, mail : String,enAttachment : String, bnAttachment : String) = safeApiCall{
        apiHelper.citizenRegistration(trackingNumber , userId ,subUserId ,userName ,sameAsAddress ,
            identityType, picture ,holdingNo ,nationalId ,birthReg ,
            passport ,dateOfBirth ,englishName ,banglaName ,gender ,
            maritalStatus, enFatherName ,bnFatherName ,enMotherName ,
            bnMotherName , occupation ,education ,religion ,liveIn ,
            pEnAddress ,pBnAddress ,pEnBlock ,pBnBlock ,pEnWord ,
            pBnWord , pEnPost , pBnPost , pEnDivision , pBnDivision ,
            pEnDistrict , pBnDistrict ,pEnSubDistrict ,pBnSubDistrict ,
            perEnAddress ,perBnAddress ,perEnBlock ,
            perBnBlock , perEnWord ,perBnWord , perEnPost ,
            perBnPost ,perEnDivision , perBnDivision ,perEnDistrict ,
            perBnDistrict , perEnSubDistrict ,perBnSubDistrict ,
            mobile , mail ,enAttachment , bnAttachment )
    }
}