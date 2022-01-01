package com.citizen.registration.network.api

import com.citizen.registration.data.response.DefaultResponse
import com.citizen.registration.data.response.LoginResponse
import javax.inject.Inject

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 12/1/2021 at 11:02 AM
 */
class ApiHelperImpl @Inject constructor(private val apiServices : ApiServices) :  ApiHelper
{
    override suspend fun login(userName: String, password: String): LoginResponse = apiServices.login(userName, password)

    override suspend fun getDashboardReport(): DefaultResponse = apiServices.getDashboardReport()

    override suspend fun getHoldingType(): DefaultResponse = apiServices.getHoldingType()

    override suspend fun checkDuplicateIdentity(identityNo: String): DefaultResponse = apiServices.checkDuplicateIdentity(identityNo)

    override suspend fun getPlaceInfo(): DefaultResponse = apiServices.getPlaceInfo()

    override suspend fun citizenRegistration(
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
    ): DefaultResponse = apiServices.citizenRegistration(
        trackingNumber , userId ,subUserId ,userName ,sameAsAddress ,
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
        mobile , mail ,enAttachment , bnAttachment, husbandNameEn , husbandNameBn
    )
}